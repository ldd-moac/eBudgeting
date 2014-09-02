<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="hero-unit white">
<div id="headLine">
	<h4>ทะเบียนรายการกลาง</h4> 
</div>

<div class="row">
	<div class="span11">
		
		<div id="modal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<span style="font-weight: bold;"></span>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<a href="#"	class="btn btn-primary" id="saveBtn">บันทึกข้อมูล</a>
				<a href="#" class="btn" id="cancelBtn">ยกเลิก</a> 
			</div>
		</div>
	
		<div class="control-group" id="mainCtr">
			
			<c:choose>
			<c:when test="${rootPage}">
				<table class="table table-bordered" id="mainTbl">
					<thead>
						<tr>
							<td>เลือกปีงบประมาณ</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${fiscalYears}" var="fiscalYear">
						<tr>
								<td> <a href="./${fiscalYear.fiscalYear}/" class="nextChildrenLnk">${fiscalYear.fiscalYear}<i class="icon icon-chevron-right nextChildrenLnk"></i> </a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>			
			</c:when>
			</c:choose>	
		</div>

		
		
	</div>
</div>
</div>

<script id="modalTemplate" type="text/x-handler-template">
<form>
	<label>ชื่อรายการกลาง</label>
	<input type="text" id="nameTxt" value="{{name}}">
</form>
</script>

<script id="mainCtrTemplate" type="text/x-handler-template">
<div>
	<form class="form-search" style="margin-bottom:10px;" id="objectiveSearchFrm">
		<div class="input-append">
    		<input type="text" class="span2 search-query" id="queryTxt" value="{{queryTxt}}">
    			<button class="btn" type="submit" id="SearchBtn">ค้นหาทะเบียน</button>
    	</div>
    </form>
</div>

<div class="controls" style="margin-bottom: 15px;">
	<a href="#" class="btn btn-info menuNew"><i class="icon icon-file icon-white"></i> เพิ่มชื่อทะเบียน</a>
	<a href="#" class="btn btn-primary menuEdit"><i class="icon icon-edit icon-white"></i> แก้ไขทะเบียน</a>
	<a href="#" class="btn btn-danger menuDelete"><i class="icon icon-trash icon-white"></i> ลบ</a>

	{{#if pageParams}}
	{{#with pageParams}}
    <div class="pagination pagination-small">
        <span style="border: 1px;">พบทั้งสิ้น {{totalElements}} รายการ </span> <b>หน้า : </b> <ul>
		{{#each page}}
	    <li {{#if isActive}}class="active"{{/if}}><a href="#" class="pageLink" data-id="{{pageNumber}}">
				{{#if isPrev}}&laquo;{{/if}} 
				{{#if isNext}}&raquo;{{/if}}
				{{#if showPageNumber}} {{pageNumber}} {{/if}}

			</a>
		</li>
	    {{/each}}
    </div>
	{{/with}}
	{{/if}}

</div>
<table class="table table-bordered table-striped" id="mainTbl">
	<thead>
		<tr>
			<td style="width:20px;"></td>
			<td>ชื่อ</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
</script>

<script id="newRowTemplate" type="text/x-handlebars-template">
	<td>  </td>
	<td>
		 <form class="form-inline">
			<div class="control-group">
				<label class="control-label" for="nameTxt"> <b>ชื่อรายการกลาง: </b> </label>
				<div class="controls">
					<input id="nameTxt" type='text' placeholder='...' class='span7' value="{{name}}" ></input> <br/>
				</div>
			</div>
		</form>

		<button class='btn btn-mini btn-info lineSave'>บันทึกข้อมูล</button>
		<button class='btn btn-mini btn-danger cancelLineSave'>ยกเลิก</button>
	</td>

</script>
<script id="rowTemplate" type="text/x-handler-template">
{{#each this}}
<tr data-id={{id}}>
	<td><input type="radio" name="rowRdo" id="rdo_{{id}}" value="{{id}}"/></td>
	<td>{{name}}</td>
</tr>
{{/each}}
</script>
	
<script type="text/javascript">

var fiscalYear = "${fiscalYear}";
var bctPage = new BudgetCommonTypePagableCollection(fiscalYear, 1);

$(document).ready(function() {
	
	var ModalView = Backbone.View.extend({
		initialize: function(params){
			this.parent = params.parent;
			
			this.currentBudgetCommonType = null;
			
		},
		el: "#modal",
		
		modalTemplate: Handlebars.compile($("#modalTemplate").html()),
		
		events: {
			"click #cancelBtn" : "close",
			"click #saveBtn" : "save"
		},
		
		render: function() {
			if(this.currentBudgetCommonType != null) {
				
				this.$el.find('.modal-header span').html("เพิ่มรายการ");
				
				var html = this.modalTemplate(this.currentBudgetCommonType.toJSON());
				this.$el.find('.modal-body').html(html);
	
				
				this.$el.modal({show: true, backdrop: 'static', keyboard: false});
				return this;
			}	
		},
		
		renderWith: function(budgetCommonType) {
			this.currentBudgetCommonType = budgetCommonType;
			this.render();
		},
		
		save: function(e) {
			// ok
			var newModel=false;
			if(this.currentBudgetCommonType.get('id') == null) {
				newModel = true;
				this.currentBudgetCommonType.set('fiscalYear', fiscalYear);
			}
			this.currentBudgetCommonType.save({
				name: this.$el.find('input[id=nameTxt]').val()
				
			},{
				success : _.bind(function(model) {
					
					this.$el.modal('hide');
					this.parent.refresh();
				},this)
			});
		},
		
		close: function() {
			this.$el.modal('hide');
		}
		
	});
	
	var MainCtrView = Backbone.View.extend({
		initialize: function(options) {
			this.collection = options.collection;
			this.collection.bind('reset', this.render, this);
			this.queryTxt = "";
			
			this.modal = new ModalView({parent: this});
		},
		events: {
			"click .menuNew" : "newBudgetCommonType",
			"click .menuEdit" : "editBudgetCommonType",
			"click .menuDelete" : "deleteBudgetCommonType",
			"click .lineSave" : "saveLine",
			"click .cancelLineSave" : "cancelSaveLine",
			
			"click a.pageLink" : "gotoPage",
			"click #SearchBtn" : "searchBtn"
		},
		mainCtrTpl: Handlebars.compile($("#mainCtrTemplate").html()),
		newRowTpl : Handlebars.compile($('#newRowTemplate').html()),
		rowTpl : Handlebars.compile($("#rowTemplate").html()),
		el: '#mainCtr',
		
		gotoPage: function(e) {
			var pageNumber = $(e.target).attr('data-id');
			this.renderPage(pageNumber);
		},
		
		searchBtn: function(e) {
			this.queryTxt = this.$el.find('#queryTxt').val();
			this.renderPage(1);
		},
		refresh: function() {
			this.renderPage(this.collection.targetPage);
		},
		renderPage: function(pageNumber) {
			this.collection.setTargetPage(pageNumber);
			bctPage.fetch({
				reset: true,
				type: 'POST',
				url: appUrl('/BudgetCommonType/fiscalYear/' + fiscalYear + '/page/'+pageNumber),
				data: {query: this.queryTxt},
				success: function() {
					bctPage.trigger('reset');
				}
			});
		},
		
		render: function() {
			var json = {};
			json.pageParams = this.collection.toPageParamsJSON();
			json.queryTxt = this.queryTxt;
			
			this.$el.html(this.mainCtrTpl(json));
			
			
			json=this.collection.toJSON();
			this.$el.find('tbody').html(this.rowTpl(json));
		},
		
		newBudgetCommonType: function(e) {
			if(! $(e.currentTarget).hasClass('disabled') ) {
				
				//var html = this.newRowTpl({name:null});
				
				//$('#mainCtr tbody').append('<tr>'+html+'</tr>');
				
				
				//this.$el.find('a.btn').toggleClass('disabled');
				
				//this.currentBudgetCommonType = new BudgetCommonType();
				this.modal.renderWith(new BudgetCommonType());
			}
		},
		
		saveLine: function(e) {
			// ok
			
			var newModel=false;
			var url = "";
			if(this.currentBudgetCommonType.get('id') == null) {
				newModel = true;
				url = this.currentBudgetCommonType.urlRoot;
			} else {
				url = this.currentBudgetCommonType.urlRoot + 
					this.currentBudgetCommonType.get('id');
			}
			
			this.currentBudgetCommonType.save({
				name: this.$el.find('input[id=nameTxt]').val(),
				fiscalYear: fiscalYear
			},{
				url : url,
				success : _.bind(function(model) {
					this.refresh();
				},this)
			});
		},
		
		cancelSaveLine: function(e) {
			this.collection.trigger('reset');
		},
		
		editBudgetCommonType: function(e) {
			var tuId = this.$el.find('input[name=rowRdo]:checked').val();
			var budgetCommonType = BudgetCommonType.findOrCreate(tuId);
			
			this.currentBudgetCommonType=budgetCommonType;
			this.currentBudgetCommonType.url=appUrl('/BudgetCommonType/'+this.currentBudgetCommonType.get('id'));
			this.modal.renderWith(this.currentBudgetCommonType);
			
			//var html = this.newRowTpl(budgetCommonType.toJSON());
			
			//this.$el.find('tr[data-id='+ tuId + ']').html(html);
		},
		
		deleteBudgetCommonType: function(e) {
			var tuId = this.$el.find('input[name=rowRdo]:checked').val();
			var budgetCommonType = BudgetCommonType.findOrCreate(tuId);
			
			if(confirm('คุณต้องการลบรายการกลาง \"'+ budgetCommonType.get('name') + '\"')==true) {
				budgetCommonType.destroy({
					url: budgetCommonType.urlRoot + budgetCommonType.id,
					success: _.bind(function() {
						this.refresh()
					},this)
				});
			}
			
		}
	});
	
	mainCtrView = new MainCtrView({collection: bctPage});
	bctPage.setTargetPage(1);
	mainCtrView.renderPage(1);
	
	
});
</script>