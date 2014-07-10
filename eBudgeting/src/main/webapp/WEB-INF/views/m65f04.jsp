 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="hero-unit white">
<div id="headLine">
	<h4>ประมวลผลก่อนการปรับลดงบประมาณครั้งที่ 3</h4> 
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
								<li><a href="<c:url value='${link.url}'></c:url>">${link.value}</a>
									<span class="divider">/</span></li>
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
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn" id="cancelBtn">Close</a> <a href="#"
						class="btn btn-primary" id="saveBtn">Save changes</a>
				</div>
			</div>

			<div id="mainCtr">
				
			</div>
		</div>
	</div>
</div>


<script id="processingTemplate" type="text/x-handler-template">
	<div><img src="/eBudgeting/resources/graphics/loading-small.gif"/> Processing... </div>
</script>

<script id="loadingTemplate" type="text/x-handler-template">
	<div>Loading <img src="/eBudgeting/resources/graphics/spinner_bar.gif"/></div>
</script>

<script id="mainTblTemplate" type="text/x-handler-template">
<div style="margin-bottom:20px;">
	ยืนยันการประมวลผล <button id="processBtn" class="btn btn-primary"> ตกลง </button>
</div>
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<td style="width: 400px;">กิจกรรม</td>
			<td style="width: 100px;">ปรับลดครั้งที่2 ระดับกิจกรรม</td>
			<td style="width: 100px;">ปรับลดครั้งที่2 ระดับรายการ</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
</script>

<script id="mainTblTbodyTemplate" type="text/x-handler-template">
{{#each this}}
<tr data-id={{id}}>
	<td style="padding-left:{{paddingLeft}}px;"> 
		<span class="label label-info">{{type.name}}</span><br/>
		{{#if isLeaf}}
			<i id=caret-{{id}} class="icon-circle"></i></icon>
		{{else}} 
			<a id="link-{{id}}" class="drillDown" href="#"><i id="caret-{{id}}" class="icon-chevron-right"></i></icon></a> 
		{{/if}}
		{{name}} </td>
	<td style="text-align: right; padding-right: 15px;"> <br/>{{sumAllocationR2 this}}</td>
	<td style="text-align: right; padding-right: 15px;"> <br/>{{sumObjectiveAllocationR2 this}}</td>
</tr>
{{/each}}
</script>

<script src="<c:url value='/resources/js/pages/m65f04.js'/>"></script>

<script type="text/javascript">

Handlebars.registerHelper('sumAllocationR2', function(obj) {
	var sum=0;
	_.forEach(obj.allocationRecordsR2, function(r) {
		sum += r.amountAllocated;
	});
	return addCommas(sum);
});

Handlebars.registerHelper('sumObjectiveAllocationR2', function(obj) {
	var sum=0;
	_.forEach(obj.objectiveAllocationRecordsR2, function(r) {
		sum += r.amountAllocated;
	});
	return addCommas(sum);
});

	var fiscalYear = "${fiscalYear}";
	var rootObjectiveId = "${rootObjective.id}";
	
	$('#mainCtr').html('<div>Loading <img src="/eBudgeting/resources/graphics/spinner_bar.gif"/></div>');
	
	var mainTblView = new MainTblView({
		rootObjectiveId : rootObjectiveId
	});
	mainTblView.render();
	
</script>