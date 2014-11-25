<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="hero-unit white">
<div id="headLine">
	<h4>การจัดสรรงบประมาณเพิ่มเติม</h4> 
</div>

<div class="row">
	<div class="span11">
		
		<div id="detailModal" class="modal wideModal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<span style="font-weight: bold;"></span>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" id="cancelBtn">กลับหน้าหลัก</a> 
				
			</div>
		</div>
		
		<div id="modal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<span style="font-weight: bold;"></span>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" id="cancelBtn">Close</a> 
				<a href="#"	class="btn btn-primary" id="saveBtn">Save changes</a>
			</div>
		</div>
		
		<div id="targetValueModal" class="modal hide fade">
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

<script id="mainTblTemplate" type="text/x-handler-template">
<div style="margin-bottom:20px;">
	ทำการจัดสรรเพิ่มเติมรอบใหม่ <button id="processBtn" class="btn btn-primary"> ตกลง </button>
</div>
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<td style="width: 100px;">รอบการจัดสรรเพิ่มเติม</td>
			<td style="width: 200px;">วันที่ทำการจัดสรร</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
</script>

<script id="mainTblTbodyTemplate" type="text/x-handler-template">
{{#each this}}
<tr data-id={{id}}>
	<td style="text-align: center;">{{math round "+" 1}}</td>
	{{#if round}} 
		<td style="text-align: left;">{{formatTimeDetail createdDate}}</td>
	{{else}}
		<td style="text-align: left;">จัดสรรปรกติ (ครั้งแรก)</td>
	{{/if}}
</tr>
{{/each}}
</script>

<script src="<c:url value='/resources/js/pages/m72f01.js'/>"></script>

<script type="text/javascript">
var fiscalYear = parseInt("${fiscalYear}");

$(document).ready(function() {
	
	mainCtrView = new MainCtrView();
	mainCtrView.render();
	
	
});
</script>