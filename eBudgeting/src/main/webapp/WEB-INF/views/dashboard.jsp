<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="hero-unit white">

<div class="row">
	<div class="span3 mainMenu" id="main1" data-id="1">
		
	</div>
	
	<div class="span3 mainMenu" id="main2" data-id="2">
		
	</div>
	
	<div class="span4 mainMenu" id="main3" data-id="3">
		
	</div>
</div>
</div>

<script id="menuTemplate" type="text/x-handlebars-template">
{{#each this}}
<div style="margin-bottom: 25px;">
	<h3 style="margin-bottom: 0px;">{{name}}</h3>
	<ul style="padding-top:0px;">
		{{#each menus}}
			<li>{{name}}
				<ul>
					{{#each menus}}
					<li>
					{{#if disabled}} 
						{{name}}
					{{else}}
						<a href="{{link}}" >{{name}}</a>
					{{/if}}
					</li>
					{{/each}}
				</ul>
			</li>
		{{/each}}
	</ul>
</div>
{{/each}}
</script>

<script id="subMenuTemplate" type="text/x-handlebars-template">
{{#each this}}
<ul style="padding-top:0px;">
		<li>
					{{#if disabled}} 
						{{name}}
					{{else}}
						<a data-type="{{type}}" {{#if link}}href="{{link}}"{{else}}href="#"{{/if}}>{{name}}</a>
					{{/if}}

			</li>
		
	</ul>	
{{/each}}
</script>

<sec:authorize access="hasRole('ROLE_USER_PLAN')">
<script type="text/javascript">
var ROLE_USER_PLAN = true;
</script>
</sec:authorize>


<script type="text/javascript">
var menuCode='${menuCode}';
var menuLevel='${menuLevel}';
var fiscalYear = parseInt("${currentRootFY.fiscalYear}");

var userGroups = '${userGroups}'.split(",");

var menuJson = [{
	name: "ข้อมูลพื้นฐานหน่วยงาน  - admin (m1)",
	code: "m1",
	menus: [{
		name: "ข้อมูลพื้นฐานงบประมาณ(m11)", code: "m11", menus: [{
			name: "m1f05: เพิ่มข้อมูลเริ่มต้นปีงบประมาณ", code:"m1f05", link: "page/m1f05/", group: "BGT_BGT_ADMIN"
		},{
			name: "m1f06: ข้อมูลหน่วยงาน", code:"m1f06", link: "page/m1f06/", group: "BGT_ADMIN"
		},{
			name: "m1f07: ข้อมูลผู้ใช้งาน", code:"m1f07", link: "page/m1f07/", group: "BGT_ADMIN"
		}]
	}]
},{
	name: "ระบบทะเบียน (m5)", code: "m5",
	menus: [{
		name: "ทะเบียนสายยุทธศาสตร์การจัดสรรงบประมาณ (m51)", code: "m51", menus:[{
			name: "m51f01:(1) ทะเบียนยุทธศาสตร์การจัดสรร", code: "m51f01", link: "page/m51f01/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f02: (2) ทะเบียนประเด็นยุทธศาสตร์", code: "m51f02", link: "page/m51f02/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f03: ทะเบียนเป้าหมายเชิงยุทธศาสตร์", code: "m51f03", link: "page/m51f03/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f04: ทะเบียนเป้าหมายบริการกระทรวง", code: "m51f04", link: "page/m51f04/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f06: (3) ทะเบียนแผนงาน", code: "m51f05", link: "page/m51f06/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f05: ทะเบียนเป้าหมายบริการหน่วยงาน", code: "m51f06", link: "page/m51f05/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f07: (4) ทะเบียนผลผลิต/โครงการ", code: "m51f07", link: "page/m51f07/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f08: (5) ทะเบียนกิจกรรมหลัก", code: "m51f08", link: "page/m51f08/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f09: (6) ทะเบียนกิจกรรมรอง", code: "m51f09", link: "page/m51f09/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f10: (7) ทะเบียนกิจกรรมย่อย", code: "m51f10", link: "page/m51f10/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f11: (8) ทะเบียนกิจกรรมเสริม", code: "m51f11", link: "page/m51f11/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f12: (9) ทะเบียนกิจกรรมสนับสนุน", code: "m51f12", link: "page/m51f12/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f13: (10) ทะเบียนกิจกรรมรายละเอียด", code: "m51f13", link: "page/m51f13/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f14: ทะเบียนรายการและรายการย่อย", code: "m51f14", link: "page/m51f14/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f15: ทะเบียนรายการกลาง", code: "m51f15", link: "page/m51f15/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f16: ทะเบียนรายการหลักสำหรับบันทึกงบประมาณกิจกรรม", code: "m51f16", link: "page/m51f16/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f17: การเชื่อมโยงกิจกรรม", code: "m51f17", link: "page/m51f17/", group: "BGT_BGT_ADMIN"
		},{
			name: "m51f18: ทะเบียนหน่วยนับ", code: "m51f18", link: "page/m51f18/", group: "BGT_BGT_ADMIN"
		}]
	}, {
		name: "ทะเบียนตามแผนปฏิบัติราชการ (m52)", code: "m52",  menus:[{
			name: "m52f01: ทะเบียนเป้าประสงค์ (เป้าหมาย) เชิงนโยบนาย", code: "m52f01",  link: "page/m52f01/", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "ทะเบียนสายยุทธศาสตร์กระทรวง-หน่วยงาน (m53)", code: "m53",  menus: [{
			name: "m53f01: ทะเบียนยุทธศาสตร์กระทรวง", code: "m53f01",  link: "page/m53f01/", group: "BGT_BGT_ADMIN"
		},{
			name: "m53f02: ทะเบียนกลยุทธ์หน่วยงาน",code: "m53f02",  link: "page/m53f02/", group: "BGT_BGT_ADMIN"
		},{
			name: "m53f03: ทะเบียนกลยุทธ์/วิธีการกรมฯ", code: "m53f03",  link: "page/m53f03/", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "ทะเบียนสายกลยุทธ์หลัก (m54)", code: "m54",  menus:[{
			name: "m54f01: ทะเบียนแนวทางการจัดสรรงบประมาณ (กลยุทธ์หลัก)", code: "m54f01",  link: "page/m54f01/", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "ทะเบียนวิสัยทัศน์-พันธกิจ (m55)",code: "m55",  menus:[{
			name: "m55f01: ทะเบียนวิสัยทัศน์หน่วยงาน", code: "m55f01", link: "page/m55f01/", group: "BGT_BGT_ADMIN"
		},{
			name: "m55f02: ทะเบียนพันธกิจหน่วยงาน", code: "m55f02", link: "page/m55f02/", group: "BGT_BGT_ADMIN"
		}]
	}]
},{
	name: "ระบบการบันทึกเงินคำของบประมาณ (m6)",code: "m6", 
	menus: [{
		name: "การจัดทำคำของบประมาณ  (m61)",code: "m61",  menus:[{
			name: "m61f01: การโยกข้อมูลงบประมาณและ MTEF จากปีงบประมาณก่อนหน้า (ระดับกิจกรรม)", code: "m61f01", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m61f02: การโยกข้อมูลงบประมาณและ MTEF จากปีงบประมาณก่อนหน้า (ระดับรายการ)",code: "m61f02",  link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m61f03: การบันทึกงบประมาณ ระดับกิจกรรม",code: "m61f03",  link: "page/m61f03_1/", group: "BGT_USER"
		},{
			name: "m61f04: การบันทึกงบประมาณ ระดับรายการ", code: "m61f04", link: "page/m61f04_1/", group: "BGT_USER"
		},{
			name: "m61f05: การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)",code: "m61f05",  link: "page/m61f05/", group: "BGT_BGT_ADMIN" 
		}]
	}, {
		name: "การกระทบยอดเงินงบประมาณ (m62)", code: "m62", menus: [{
			name: "m62f01: การประมวลผลการกระทบยอดเงินงบประมาณจากระดับรายการมาที่ระดับกิจกรรม", code: "m62f01", link: "page/m62f01/", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "การพิจารณากรอบวงเงินเพื่อตั้งคำของบประมาณ (เข้าระบบ e-Budgeting) (m63)",code: "m63",  menus:[{
			name: "m63f04: การประมวลผลก่อนการปรับลดรอบที่ 1", code: "m63f04", link: "page/m63f04/", group: "BGT_BGT_ADMIN"
		},{
			name: "m63f01: การปรับลดงบประมาณระดับกิจกรรมรอบที่ 1",code: "m63f01",  link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m63f05: การปรับลดทะเบียนรายการ",code: "m63f05",  link: "page/m63f05/", group: "BGT_USER"
		},{
			name: "m63f02: การปรับลดงบประมาณระดับรายการรอบที่ 1", code: "m63f02", link: "page/m63f02/", group: "BGT_BGT_ADMIN"
		},{
			name: "m63f03: การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)", code: "m63f03", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	}, {
		name: "การพิจารณาตามชั้นกรรมาธิการ (วาระที่ 1 - 3) (m64)", code: "m64", menus:[{
			name: "m64f04: การประมวลผลก่อนการปรับลดรอบที่ 2", code: "m64f04", link: "page/m64f04/", group: "BGT_BGT_ADMIN"
		}, {
			name: "m64f01: การปรับลดงบประมาณระดับกิจกรรมรอบที่ 2", code: "m64f01", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}, {
			name: "m64f02: การปรับลดงบประมาณระดับรายการรอบที่ 2", code: "m64f02", link: "page/m64f02/", group: "BGT_BGT_ADMIN"
		}, {
			name: "m64f03: การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)", code: "m64f03", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "การอนุมัติงบประมาณ ตาม พ.ร.บ. (m65)",code: "m65",  menus: [{
			name: "m65f04: การประมวลผลก่อนการปรับลดรอบที่ 3", code: "m65f04", link: "page/m65f04/", group: "BGT_BGT_ADMIN"
		},{
			name: "m65f01: การปรับลดงบประมาณระดับกิจกรรมรอบที่ 3", code: "m65f01", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m65f02: การปรับลดงบประมาณระดับรายการรอบที่ 3", code: "m65f02", link: "page/m65f02/", group: "BGT_BGT_ADMIN"
		},{
			name: "m65f03: การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)", code: "m65f03", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	}]
},{
	name: "ระบบการจัดสรรงบประมาณ (m7)", code: "m7", 
	menus: [{
		name: "การจัดสรรงบประมาณ (m71)",code: "m71",  menus: [{
			name: "m3f04: การประมวลผลก่อนการจัดสรรงบประมาณ", code: "m3f04", link: "page/m4f01/", group: "BGT_BGT_ADMIN"
		},{
			name: "m71f01: การจัดสรรงบประมาณระดับกิจกรรม", code: "m71f01", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m71f02: การจัดสรรงบประมาณระดับรายการ", code: "m71f02", link: "page/m71f02/", group: "BGT_BGT_ADMIN"
		},{
			name: "m71f03: การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)", code: "m71f03", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	}, {
		name: "การจัดสรรเพิมเติม (m72)",code: "m72",  menus:[{
			name: "m72f01: การบจัดสรรงบประมาณเพิ่มเติมระดับกิจกรรม",code: "m72f01",  link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}, {
			name: "m72f02: การจัดสรรงบประมาณเพิ่มเติมระดับรายการ", code: "m72f02", link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}, {
			name: "m72f03: การนำส่งคำของบประมาณ (Sign off) / ถอนนำส่ง (Release)",code: "m72f03",  link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	}]
},{
	name: "ระบบรายงาน (r)", code: "m8", 
	menus: [{
		name: "รายงานทะเบียน (m51r)", code: "m81", menus:[{
			name: "m51r01: ทะเบียนยุทธศาสตร์จัดสรร", code: 'm51r01', link: "m51r01.xls/"+fiscalYear+"/file/m51r01.xls",  type: "download", type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r02: ทะเบียนประเด็นยุทธศาสตร์", code: 'm51r01', link: "m51r02.xls/"+fiscalYear+"/file/m51r02.xls",  type: "download", type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r03: ทะเบียนเป้าหมายเชิงยุทธศาสตร์", code: 'm51r01', link: "m51r03.xls/"+fiscalYear+"/file/m51r03.xls",  type: "download", type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r04: ทะเบียนเป้าหมายบริการกระทรวง",  code: 'm51r01',link: "m51r04.xls/"+fiscalYear+"/file/m51r04.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r05: ทะเบียนเป้าหมายบริการหน่วยงาน",  code: 'm51r01',link: "m51r05.xls/"+fiscalYear+"/file/m51r05.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r06: ทะเบียนแผนงาน",  code: 'm51r01',link: "m51r06.xls/"+fiscalYear+"/file/m51r06.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r07: ทะเบียนผลผลิต-โครงการ และรายละเอียดประกอบ",  code: 'm51r01',link: "m51r07.xls/"+fiscalYear+"/file/m51r07.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r08: ทะเบียนกิจกรรมหลัก", code: 'm51r01', link: "m51r08.xls/"+fiscalYear+"/file/m51r08.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r09: ทะเบียนกิจกรรมรอง",  code: 'm51r01',link: "m51r09.xls/"+fiscalYear+"/file/m51r09.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r10: ทะเบียนกิจกรรมย่อย",  code: 'm51r01',link: "m51r10.xls/"+fiscalYear+"/file/m51r10.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r11: ทะเบียนกิจกรรมเสริม", code: 'm51r01', link: "m51r11.xls/"+fiscalYear+"/file/m51r11.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r12: ทะเบียนกิจกรรมสนับสนุน", code: 'm51r01', link: "m51r12.xls/"+fiscalYear+"/file/m51r12.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r13: ทะเบียนกิจกรรมรายละเอียด", code: 'm51r01', link: "m51r13.xls/"+fiscalYear+"/file/m51r13.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r15: ทะเบียนประเภทรายการกลาง", code: 'm51r01', link: "m51r15.xls/"+fiscalYear+"/file/m51r15.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r16: ตรวจสอบสายการเชื่อมโยงข้อมูล", code: 'm51r01', link: "m51r16.xls/"+fiscalYear+"/file/m51r16.xls",  type: "download", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r17: ตรวจสอบสายการเชื่อมโยงข้อมูล (สายยุทธศาสตร์กระทรวงฯ-กลยุทธ์หน่วยงาน)", code: 'm51r01', link: "m51r17.xls/"+fiscalYear+"/file/m51r17.xls",  type: "download",disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m51r17: ทะเบียนหน่วยนับ", code: 'm51r01', link: "m51r18.xls/"+fiscalYear+"/file/m51r18.xls",  type: "download", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "รายงานการตรวจสอบ (m52r)", menus:[{
			name: "m52r01: การบันทึกงบประมาณระดับกิจกรรม", code: 'm51r01', link: "m52r01_1.xls/"+fiscalYear+"/file/m52r01_1.xls",  type: "download", group: "BGT_USER"
		},{
			name: "m52r02: การบันทึกงบประมาณระดับรายการ", code: 'm51r01', link: "m52r02_1.xls/"+fiscalYear+"/file/m52r02_1.xls",  type: "download", group: "BGT_USER"
		},{
			name: "m52r07: แบบสรุปงบประมาณรายจ่ายประจำปีงบประมาณ(วิสัยทัศน์ - พันธกิจ)", code: 'm51r01', link: "m52r07.xls/"+fiscalYear+"/file/m52r07.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m52r08: ความเชื่อมโยงกับแผนบริหารรราชการแผ่นดิน", code: 'm51r01', link: "m52r08.xls/"+fiscalYear+"/file/m52r08.xls",  type: "download", group: "BGT_BGT_ADMIN"
		},{
			name: "m52r11: ความเชื่อมโยงระหว่างยุทธศาสตร์กระทรวงกับกลยุทธ์หน่วยงานและแนวทางการจัดสรรงบประมาณ", code: 'm51r01', link: "m52r11.xls/"+fiscalYear+"/file/m52r11.xls",  type: "download", group: "BGT_BGT_ADMIN"
		}]
	}, {
		name: "รายงานตามแบบแผนปฏิบัติราชการ] (m83)", code: "m83",  menus:[{
			name: "m82f01: ตรวจสอบสายการเชื่อมโยงข้อมูล", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f02: ตรวจสอบการบันทึกเงินระดับกิจกรรม", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f03: ตรวจสอบการบันทึกเงินระดับรายการ", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "รายงานตามแบบคำของบประมาณ (m84)", code: "m84", menus:[{
			name: "m82f01: ตรวจสอบสายการเชื่อมโยงข้อมูล", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f02: ตรวจสอบการบันทึกเงินระดับกิจกรรม", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f03: ตรวจสอบการบันทึกเงินระดับรายการ", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "รายงานตามแบบตารางขวาง] (m85)", code: "m85", menus:[{
			name: "m82f01: ตรวจสอบสายการเชื่อมโยงข้อมูล", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f02: ตรวจสอบการบันทึกเงินระดับกิจกรรม", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f03: ตรวจสอบการบันทึกเงินระดับรายการ", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	},{
		name: "รายงานตามแผนปฏิบัติการและงบประมาณ (m86)", code: "m86", menus:[{
			name: "m82f01: ตรวจสอบสายการเชื่อมโยงข้อมูล", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f02: ตรวจสอบการบันทึกเงินระดับกิจกรรม", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		},{
			name: "m82f03: ตรวจสอบการบันทึกเงินระดับรายการ", code: 'm51r01', link: "page/m2f14/", disabled: "disabled", group: "BGT_BGT_ADMIN"
		}]
	}]
}];

	var menuTemplate = Handlebars.compile($("#menuTemplate").html());

	var mainview;
	var e1;
	

	//	find only the last menu of our right!
	var l2Menu = _.flatten(_.flatten(menuJson, 'menus'), 'menus').filter(function(menu) {
		return  userGroups.indexOf(menu.group) >= 0;
	});
	
	var l2MenuCode = _.pluck(l2Menu,'code');


	$(document).ready(function() {
	
		// now prune the menu
		for(var i=0; i<menuJson.length; i++) {
			var m1 = menuJson[i];
			for(var j=0; j<m1.menus.length; j++) {
				var m2 = m1.menus[j];
				
				m2.menus = _.filter(m2.menus, function(menu) {
					
					return l2MenuCode.lastIndexOf(menu.code) >= 0;
				});
			}
			
			m1.menus = _.filter(m1.menus, function(menu) {
				return menu.menus.length > 0;
			});
		}
		
		menuJson =_.filter(menuJson, function(menu) {
			return menu.menus.length > 0;
		});
		
		var MainView = Backbone.View.extend({
			initialize : function(options) {
	
			},
			subMenuTemplate : Handlebars.compile($("#subMenuTemplate").html()),
			el : "body",
			render : function() {
				$("#main1").html(this.subMenuTemplate(this.menu));
			},
	
			renderWith : function(m) {
				this.menu = m;
				this.render();
			},
			events : {
				"click a" : "menuClick"
			},
	
			menuClick : function(e) {
				if ( $(e.target).attr('data-type') !=  "download" ) {
					e1 = e;
		
					var parentDiv = $(e.target).parents('div.mainMenu');
					var li = $(e.target).parent();
		
					//e1=parentDiv;
					var level = parentDiv.attr('data-id');
		
					if (level == 1) {
						// get this index
						var i = parentDiv.find('li').index(li);
		
						this.currentFirstLevelIndex = i;
						$("#main3").empty();
		
						$("#main2").html(this.subMenuTemplate(this.menu[i].menus));
		
						$("#navbarBreadcrumb").empty();
						$("#navbarBreadcrumb").html(
								"<li><a href='#'>" + 
								$(e.target).html() +
								"</a></li>");
		
						$.ajax({
							type : 'POST',
							data : {
								level : 0,
								value : this.menu[i].name,
								code : this.menu[i].code
							},
							url : appUrl('/Session/updateNavbarBreadcrumb')
						});
		
					} else if (level == 2) {
						// get this index
						var i = parentDiv.find('li').index(
								li);
		
						$("#main3").html(this.subMenuTemplate(this.menu[this.currentFirstLevelIndex].menus[i].menus));
		
						$("#navbarBreadcrumb").empty();
						$("#navbarBreadcrumb").html(
								"<li><a href='#'>" + 
								this.menu[this.currentFirstLevelIndex].name + 
								"</a> <span class='divider'>/</span></li>");
						$("#navbarBreadcrumb").append(
								"<li><a href='#'>" + 
								$(e.target).html() +
								"</a></li>");
		
						$.ajax({
							type : 'POST',
							data : {
								level : 1,
								value : this.menu[this.currentFirstLevelIndex].menus[i].name,
								code : this.menu[this.currentFirstLevelIndex].menus[i].code
							},
							url : appUrl('/Session/updateNavbarBreadcrumb')
						});
		
					} else if (level == 3) {
		
					}
				} else {
					loadReport($(e.target).attr('href'));
					return false;
				}
			}
		});
	
		
		mainView = new MainView();
	
		mainView.renderWith(menuJson);
	 	
	 	if(menuLevel == '0') {
	 		var firstMenu = _.where(menuJson, {code: menuCode})[0];
	 		$("#main2").html(mainView.subMenuTemplate(firstMenu.menus));
	 		mainView.currentFirstLevelIndex = _.indexOf(menuJson, firstMenu);
	 	} else if(menuLevel == '1') {
	 		var firstMenu = _.find(menuJson, function(menu) { return _.where(menu.menus, {code: menuCode}).length > 0; });
			mainView.currentFirstLevelIndex = _.indexOf(menuJson, firstMenu);
			$("#main2").html(mainView.subMenuTemplate(firstMenu.menus));
			
			var secondMenu = _.where(firstMenu.menus, {code: menuCode})[0];
			$("#main3").html(mainView.subMenuTemplate(secondMenu.menus));
	 	}
		

});
</script>
