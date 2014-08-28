<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="hero-unit white">
<div id="headLine">
	<h4>การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)</h4> 
</div>

<div class="row">
	<div class="span11" id="mainCtr">
		
		<div id="modal" class="modal wideModal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<span style="font-weight: bold;"></span>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<a href="#" class="btn" id="cancelBtn">กลับหน้าหลัก</a> 
			</div>
		</div>

		<div id="signOffTbl" style="margin-bottom: 45px;">
			
		</div>


		<h4>ประวัติการ sign off</h4>
		<div >
			<table class="table table-bordered"  style="margin-bottom:10px; width:875px; table-layout:fixed;">
				<thead>
					<tr>
						<td width="80">วันที่/เวลา</td>
						<td width="150">ผู้ดำเนินการ</td>
						<td>การดำเนินการ</td>
				</thead>
				<tbody id="signOffLog"></tbody>
			</table>
		</div>
	</div>
</div>
</div>

<script id="loadingTemplate" type="text/x-handler-template">
	<div>Loading <img src="/eBudgeting/resources/graphics/spinner_bar.gif"/></div>
</script>


<script id="signOffLogTbodyTemplate" type="text/x-handler-template">
{{#each this}}
<tr>
	<td>{{formatTimeDetail timestamp}}</td>
	<td>{{person.firstName}} {{person.lastName}}</td>
	<td>{{toStatus}}</td>
</tr>
{{/each}}
</script>

<script id="mainTblTemplate" type="text/x-handler-template">
<table class="table table-bordered"  style="margin-bottom:10px; width:875px; table-layout:fixed;">
	<thead>
		<tr>
			<td>เลือกทั้งหมด</td>
			<td><button class="btn topRow" id="lock1">สิทธิ Sign off (ปิด) รอการเสนอ</button><br/>
					Sign Off รอเสนอ (Lock)</td>
			<td> <button class="btn topRow" id="unLock1">สิทธิ Release (เปิด) รอการเสนอ</button><br/>
					Release ถอนรอเสนอ (Lock)</td>
			<td><button class="btn topRow" id="lock2">สิทธิ Sign off (ปิด) นำส่งข้อมูล</button><br/>
					Sign Off นำส่งข้อมูล (Lock)</td>
			<td><button class="btn topRow" id="unLock2">สิทธิ Release (เปิด) นำส่งข้อมูล</button><br/>
					 Release นำส่งข้อมูล (Lock)</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td> {{headline}} 
					<ul>
						<li>เงินกิจกรรม : {{formatNumber sumTotalObjectiveBudgetProposal}} บาท</li>
						<li>เงินรายการ : {{formatNumber sumTotalBudgetProposal}} บาท</li>
					</ul>
			</td>
			<td>{{lock1Person.firstName}}  {{lock1Person.lastName}} <br/>{{formatTimeDetail lock1TimeStamp}}</td>
			<td>{{unLock1Person.firstName}}  {{unLock1Person.lastName}} <br/>{{formatTimeDetail unLock1TimeStamp}}</td>
			<td>{{lock2Person.firstName}}  {{lock2Person.lastName}}  <br/>{{formatTimeDetail lock2TimeStamp}}</td>
			<td>{{unLock2Person.firstName}}  {{unLock2Person.lastName}} <br/>{{formatTimeDetail unLock2TimeStamp}}</td>
		</tr>
	</tbody>
</table>
</script>

<script src="<c:url value='/resources/js/pages/budgetSignOff.js'/>"></script>

<script type="text/javascript">
	var fiscalYear = parseInt("${fiscalYear}");
	var round = parseInt("${round}");
	
	var mainCtrView = null;
	
	var sumObjectiveProposal = null;
	var sumProposal = null;
	var signOfflogs = new BudgetSignOffLogCollection();
	
	mainCtrView = new MainCtrView();
	
	$(document).ready(function() {
		budgetSignOff = new BudgetSignOff();
		budgetSignOff.url = appUrl('/BudgetSignOff/' + fiscalYear +'/Round/'+round);
		budgetSignOff.fetch({
			success: function() {
				$.get(appUrl('/BudgetProposal/sumTotalOfOwner/' + fiscalYear+'/Round/'+round),
					function(response) {
						sumObjectiveProposal = response[1];
						sumProposal = response[0];
						budgetSignOff.set('sumTotalBudgetProposal', response[0]);
						budgetSignOff.set('sumTotalObjectiveBudgetProposal', response[1]);
						mainCtrView.renderWith(budgetSignOff);		
				});
			}
		});
		
	});
</script>
