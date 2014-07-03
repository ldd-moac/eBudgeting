<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="hero-unit white">
<div id="headLine">
	<h4>เพิ่มข้อมูลปีงบประมาณ</h4> 
</div>
<div class="row">
	<div class="span11">
		<c:if test="${rootPage == false}">
		    <ul class="breadcrumb" id="headNav">
		    	<c:forEach items="${breadcrumb}" var="link" varStatus="status">
		    		<c:choose>
						<c:when test="${status.last}">
							<li class="active">${link.value}</li>
						</c:when>
						<c:otherwise>
							<li><a href="<c:url value='${link.url}'></c:url>">${link.value}</a> <span class="divider">/</span></li>
						</c:otherwise>
					</c:choose>
		    	</c:forEach>
		    </ul>
	    </c:if>
	
		<div id="modal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<span style="font-weight: bold;"></span>
			</div>
			<div class="modal-body">
				
			</div>
		</div>
	
		<div id="mainCtr">
		<c:choose>
		<c:when test="${rootPage}">
			<table class="table table-bordered" id="mainTbl">
				<thead>
					<tr>
						<td>ปีงบประมาณที่มีข้อมูลแล้ว</td>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>			
		</c:when>
		</c:choose>
		
		
		<form>
		    <fieldset>
			    <legend>ตั้งค่าปีงบประมาณใหม่</legend>
			    <label>ระบุปีงบประมาณ</label>
			    <input type="text" placeholder="…" id="fiscalYearTxt">
			    <span class="help-block">เพิ่มค่าตั้งต้นของปีงบประมาณใหม่</span>
			    <button class="btn" id="initFiscalYearBtn">ตกลง</button>
		    </fieldset>
    	</form>
    	</div>
	</div>
</div>

</div>
<script id="tbodyTemplate" type="text/x-handlebars-template">
{{#each this}}
<tr data-id="{{id}}">
	<td>{{fiscalYear}}</td>
</tr>
{{/each}}
</script>
<script id="rowTemplate" type="text/x-handelbars-template">
<td>{{fiscalYear}}</td>
</script>
<script type="text/javascript">
function initNewFiscalyear() {
	return false;
}
var fiscalYearView;
var rootObjective = new ObjectiveCollection();
$(document).ready(function() {
	
	var FiscalYearView = Backbone.View.extend({
		initialize: function() {
			rootObjective.bind('reset', this.render, this);
		},
		el: "#mainCtr",
		events: {
			"click #initFiscalYearBtn" : "initFiscalYear"
		},
		rowTemplate: Handlebars.compile($("#rowTemplate").html()),
		tbodyTemplate: Handlebars.compile($("#tbodyTemplate").html()),
		
		initFiscalYear: function() {
			// check fist if the input ficalYear has already been init
			var fyTxt = +($('#fiscalYearTxt').val());
			if(rootObjective.findWhere({fiscalYear: fyTxt}) != null) {
				// already has one
				alert("ปีงบประมาณนี้มีข้อมูลอยู่แล้ว กรุณาระบุปีงบประมาณใหม่");
			} else {
				if(isNaN(fyTxt)) {
					alert("กรุณาระบุปีงบประมาณเป็นตัวเลข");
				} else {
					$('#modal').find('.modal-header span').html("เพิ่มข้อมูลปีงบประมาณ " + fyTxt);
					$('#modal').find('.modal-body').html('<img src=' + appUrl('/resources/graphics/loading.gif') + '/> กรุณารอการประมวลผล ... ');
					
					$('#modal').modal({show: true, backdrop: 'static', keyboard: false});
					
					$.ajax({
						type: 'POST',
						url: appUrl('/Objective/initFiscalYear'),
						data: {
							fiscalYear: $('#fiscalYearTxt').val()
						},
						success: _.bind(function() {
							
							$('#modal').modal('hide');
							
							var newRoot = new Objective();
							newRoot.set('fiscalYear', fyTxt);
							
							rootObjective.add(newRoot);
							
							this.render();
						},this)
					});	
					
				}
			
			}
			
			return false;
		},
		
		render: function() {
			
			var json= rootObjective.toJSON();
			var html= this.tbodyTemplate(json);
				
			this.$el.find('tbody').html(html);
			
			return this;
		},
		
		renderObjective: function(objective) {

			var objectiveEl = this.$el.find('tr[data-id='+ objective.get('id') +']');
			
			var json = objective.toJSON();
			var html = this.rowTemplate(json);
			
			objectiveEl.html(html);
			
		}
		
		
	});
	
	fiscalYearView = new FiscalYearView();
	rootObjective.fetch({
		url: appUrl('/Objective/root'),
		success: function() {
			fiscalYearView.render();
			
		}
	});
	
});
</script>