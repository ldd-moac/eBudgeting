package biz.thaicom.eBudgeting.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import biz.thaicom.eBudgeting.models.bgt.BudgetSignOff;
import biz.thaicom.eBudgeting.models.bgt.OrganizationAllocationRound;
import biz.thaicom.eBudgeting.models.pln.Objective;
import biz.thaicom.eBudgeting.models.pln.ObjectiveTypeId;
import biz.thaicom.eBudgeting.models.pln.TargetUnit;
import biz.thaicom.eBudgeting.models.webui.Breadcrumb;
import biz.thaicom.eBudgeting.services.EntityService;
import biz.thaicom.security.models.Activeuser;
import biz.thaicom.security.models.ThaicomUserDetail;

@Controller
public class GenericViewController {

	public static Logger logger = LoggerFactory
			.getLogger(GenericViewController.class);

	private static final String webAppcontext = "eBudgeting";

	@Autowired
	private EntityService entityService;

	@RequestMapping("/jsp/{jspName}")
	public String renderJsp(@PathVariable String jspName) {

		return jspName;
	}

	// --------------------------------------------------------------m1f05:
	// เพิ่มข้อมูลเริ่มต้นปีงบประมาณ
	@RequestMapping("/page/m1f05/")
	public String render_m1f05(Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m1f05";
	}

	// --------------------------------------------------------------m1f06:
	// ข้อมูลหน่วยงาน

	// --------------------------------------------------------------m1f07:
	// ข้อมูลผู้ใช้งาน
	@RequestMapping("/page/m1f07")
	public String render_m61f07(Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m1f07";
	}

	



	@RequestMapping("/page/m4f01/")
	public String render_m4f01(Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m4f01";
	}

	@RequestMapping("/page/m4f02/")
	public String render_m4f02(Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m4f02";
	}

	@RequestMapping("/page/m4f02/{fiscalYear}/{objectiveId}")
	public String render_m4f02OfYear(@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId, Model model,
			HttpServletRequest request) {

		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear,
				objectiveId);

		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if (objective != null) {
			logger.debug("Objective found!");

			model.addAttribute("objective", objective);
			// now construct breadcrumb?

			List<Breadcrumb> breadcrumb = entityService
					.createBreadCrumbObjective("/page/m4f02", fiscalYear,
							objective);

			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);

		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m4f02/";
		}

		return "m4f02";
	}

	@RequestMapping("/page/m2f14/")
	public String render_m2f14(Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m2f14";
	}

	@RequestMapping("/page/m2f14/{fiscalYear}/")
	public String render_m2f14WithFiscalYear(@PathVariable Integer fiscalYear,
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);

		return "m2f14";
	}

	private Integer setFiscalYearFromSession(Model model, HttpSession session) {
		if (session.getAttribute("currentRootFY") != null) {
			Objective rootFy = (Objective) session
					.getAttribute("currentRootFY");
			model.addAttribute("fiscalYear", rootFy.getFiscalYear());
			return rootFy.getFiscalYear();
		}
		return null;
	}

	private Integer getCurrentFiscalYearFromSession(HttpSession session) {
		Objective rootFy = (Objective) session.getAttribute("currentRootFY");
		return rootFy.getFiscalYear();
	}

	// --------------------------------------------------------------m51f01:
	// ทะเบียนยุทธศาสตร์การจัดสรร
	@RequestMapping("/page/m51f01/")
	public String render_m51f01(Model model, HttpServletRequest request,
			HttpSession session) {
		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);

		model.addAttribute("typeId", 109);

		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f02:
	// ทะเบียนประเด็นยุทธศาสตร์

	@RequestMapping("/page/m51f02/")
	public String render_m51f02(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);

		setFiscalYearFromSession(model, session);

		model.addAttribute("typeId", 121);

		String relatedTypeString = ""
				+ ObjectiveTypeId.ยุทธศาสตร์การจัดสรรงบประมาณ.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = ""
				+ ObjectiveTypeId.ยุทธศาสตร์การจัดสรรงบประมาณ.getName() + "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasUnit", true);

		model.addAttribute("hasParent", "");
		model.addAttribute("parentTypeName", "");
		model.addAttribute("parentTypeId", "");

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f03:
	// ทะเบียนเป้าหมายเชิงยุทธศาสตร์
	@RequestMapping("/page/m51f03/")
	public String render_m51f03(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 110);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName",
				ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getName());
		model.addAttribute("parentTypeId",
				ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getValue());

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f04:
	// ทะเบียนเป้าหมายบริการกระทรวง
	@RequestMapping("/page/m51f04/")
	public String render_m51f04(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);

		model.addAttribute("typeId", 111);

		model.addAttribute("hasUnit", true);

		model.addAttribute("hasParent", "");
		model.addAttribute("parentTypeName", "");
		model.addAttribute("parentTypeId", "");

		String relatedTypeString = ""
				+ ObjectiveTypeId.เป้าประสงค์เชิงนโยบาย.getValue();

		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = ""
				+ ObjectiveTypeId.เป้าประสงค์เชิงนโยบาย.getName();
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f05:
	// ทะเบียนเป้าหมายบริการหน่วยงาน
	@RequestMapping("/page/m51f05/")
	public String render_m51f05(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);

		model.addAttribute("typeId", 112);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName",
				ObjectiveTypeId.เป้าหมายบริการกระทรวง.getName());
		model.addAttribute("parentTypeId",
				ObjectiveTypeId.เป้าหมายบริการกระทรวง.getValue());

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f06:
	// ทะเบียนแผนงาน
	@RequestMapping("/page/m51f06/")
	public String render_m51f06(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 101);

		String relatedTypeString = ""
				+ ObjectiveTypeId.ประเด็นยุทธศาสตร์.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = ""
				+ ObjectiveTypeId.ประเด็นยุทธศาสตร์.getName() + "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f07:
	// ทะเบียนผลผลิต/โครงการ
	@RequestMapping("/page/m51f07/")
	public String render_m51f07(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 102);

		model.addAttribute("hasUnit", true);

		String relatedTypeString = ""
				+ ObjectiveTypeId.เป้าหมายบริการหน่วยงาน.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = ""
				+ ObjectiveTypeId.เป้าหมายบริการหน่วยงาน.getName() + "";

		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.แผนงาน.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.แผนงาน.getValue());

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f08:
	// ทะเบียนกิจกรรมหลัก
	@RequestMapping("/page/m51f08/")
	public String render_m51f08(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 103);

		String relatedTypeString = ""
				+ ObjectiveTypeId.กลยุทธ์หน่วยงาน.getValue() + " "
				+ ObjectiveTypeId.กลยุทธ์วิธีการหน่วยงาน.getValue() + " "
				+ ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = ""
				+ ObjectiveTypeId.กลยุทธ์หน่วยงาน.getName() + " "
				+ ObjectiveTypeId.กลยุทธ์วิธีการหน่วยงาน.getName() + " "
				+ ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getName() + "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName",
				ObjectiveTypeId.ผลผลิตโครงการ.getName());
		model.addAttribute("parentTypeId",
				ObjectiveTypeId.ผลผลิตโครงการ.getValue());

		model.addAttribute("hasUnit", true);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f09:
	// ทะเบียนกิจกรรมรอง
	@RequestMapping("/page/m51f09/")
	public String render_m51f09(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 104);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		return "objectiveNameRegister";
	}

	// --------------------------------------------------------------m51f10:
	// ทะเบียนกิจกรรมย่อย
	@RequestMapping("/page/m51f10/")
	public String render_m51f10(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 105);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		return "objectiveNameRegister";
	}

	// --------------------------------------------------------------m51f11:
	// ทะเบียนกิจกรรมเสริม
	@RequestMapping("/page/m51f11/")
	public String render_m51f11(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);

		model.addAttribute("typeId", 106);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");
		return "objectiveNameRegister";
	}

	// --------------------------------------------------------------m51f12:
	// ทะเบียนกิจกรรมสนับสนุน
	@RequestMapping("/page/m51f12/")
	public String render_m51f12(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 107);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		return "objectiveNameRegister";
	}

	// --------------------------------------------------------------m51f13:
	// ทะเบียนกิจกรรมรายละเอียด
	@RequestMapping("/page/m51f13/")
	public String render_m51f13(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 108);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");
		return "objectiveNameRegister";
	}

	// --------------------------------------------------------------m51f14:
	// ทะเบียนรายการและระดับรายการ
	@RequestMapping("/page/m51f14/")
	public String render_m51f14(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		return "m51f14";
	}

	// --------------------------------------------------------------m51f15:
	// ทะเบียนรายการกลาง
	@RequestMapping("/page/m51f15/")
	public String render_m51f15_fiscal(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);

		return "m51f15";
	}

	// -------------------------------------------------------------- m51f16:
	// ทะเบียนรายการหลักสำหรับบันทึกงบประมาณกิจกรรม
	@RequestMapping("/page/m51f16/")
	public String render_m51f16_fiscal(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		return "m51f16";
	}

	// -------------------------------------------------------------- m51f17:
	// การเชื่อมโยงกิจกรรม
	@RequestMapping("/page/m51f17/")
	public String render_m51f17_fiscal(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		return "m51f17";
	}

	// --------------------------------------------------- m51f18:
	// ข้อมูลหน่วยนับเป้าหมาย
	@RequestMapping("/page/m51f18/")
	public String render_m51f18(Model model, HttpServletRequest request,
			HttpSession session) {
		List<TargetUnit> targetUnits = entityService.findAllTargetUnits();
		model.addAttribute("rootPage", true);
		model.addAttribute("targetUnits", targetUnits);
		return "m51f18";
	}

	// --------------------------------------------------------------m52f01:
	// ทะเบียนเป้าประสงค์เชิงนโยบนาย
	@RequestMapping("/page/m52f01/")
	public String render_m52f01(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 113);

		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m53f01:
	// ทะเบียนยุทธศาสตร์กระทรวง
	@RequestMapping("/page/m53f01/")
	public String render_m53f01(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 114);

		String relatedTypeString = ""
				+ ObjectiveTypeId.เป้าประสงค์เชิงนโยบาย.getValue();
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = ""
				+ ObjectiveTypeId.เป้าประสงค์เชิงนโยบาย.getName();
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m53f02:
	// ทะเบียนยุทธศาสตร์หน่วยงาน
	@RequestMapping("/page/m53f02/")
	public String render_m53f02(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 115);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName",
				ObjectiveTypeId.ยุทธศาสตร์กระทรวง.getName());
		model.addAttribute("parentTypeId",
				ObjectiveTypeId.ยุทธศาสตร์กระทรวง.getValue());

		return "objectiveRegister";

	}

	// --------------------------------------------------------------m53f03:
	// ทะเบียนกลยุทธ์/วิธีการหน่วยงาน
	@RequestMapping("/page/m53f03/")
	public String render_m53f03(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 116);

		model.addAttribute("hasUnit", true);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName",
				ObjectiveTypeId.กลยุทธ์หน่วยงาน.getName());
		model.addAttribute("parentTypeId",
				ObjectiveTypeId.กลยุทธ์หน่วยงาน.getValue());

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m54f01:
	// ทะเบียนแนวทางการจัดสรรงบประมาณ
	@RequestMapping("/page/m54f01/")
	public String render_m54f01(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 118);

		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m55f01:
	// ทะเบียนวิสัยทัศน์หน่วยงาน
	@RequestMapping("/page/m55f01/")
	public String render_m55f01(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 119);

		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", false);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m55f02:
	// ทะเบียนพันธกิจหน่วยงาน
	@RequestMapping("/page/m55f02/")
	public String render_m55f02(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		model.addAttribute("typeId", 120);

		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);

		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);

		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", false);

		return "objectiveRegister";
	}

	// --------------------------------------------------------------m61f03:
	// การบันทึกงบประมาณ ระดับกิจกรรมหลัก
	@RequestMapping("/page/m61f03_1/")
	public String render_m61f03(Model model, HttpServletRequest request,
			HttpSession session, @Activeuser ThaicomUserDetail currentUser) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();
		Integer fy = setFiscalYearFromSession(model, session);
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYears", fiscalYears);

		// check the budgetSignOff
		BudgetSignOff bso = entityService
				.findBudgetSignOffByFiscalYearAndOrganizationAndRound(fy,
						currentUser.getWorkAt(), 0);

		if (bso.getLock1Person() != null) {
			// should not be able to edit!
			model.addAttribute("readOnly", true);
		}

		return "m61f03_1";
	}

	// --------------------------------------------------------------m61f04:
	// การบันทึกงบประมาณ ระดับรายการ
	// @RequestMapping("/page/m61f04/")
	// public String render_m61f04(
	// Model model, HttpServletRequest request, HttpSession session,
	// @Activeuser ThaicomUserDetail currentUser) {
	//
	// model.addAttribute("rootPage", false);
	//
	// setFiscalYearFromSession(model, session);
	//
	// Integer fy = getCurrentFiscalYearFromSession(session);
	// Objective rootObjective =
	// entityService.findOneRootObjectiveByFiscalyear(fy);
	//
	// model.addAttribute("objectiveId", rootObjective.getId());
	//
	// //check the budgetSignOff
	// BudgetSignOff bso =
	// entityService.findBudgetSignOffByFiscalYearAndOrganization(
	// fy, currentUser.getWorkAt());
	//
	// if(bso.getLock1Person() != null) {
	// // should not be able to edit!
	// model.addAttribute("readOnly", true);
	// }
	//
	// return "m61f04";
	// }

	// --------------------------------------------------------------m62f02:
	// การบันทึก SignOff/Release
	@RequestMapping("/page/m62f02/")
	public String render_m61f05(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);

		setFiscalYearFromSession(model, session);

		Integer fy = getCurrentFiscalYearFromSession(session);
		Objective rootObjective = entityService
				.findOneRootObjectiveByFiscalyear(fy);

		model.addAttribute("objectiveId", rootObjective.getId());
		model.addAttribute("round", 0);

		return "budgetSignOff";
	}
	
	// --------------------------------------------------------------m63f03:
	// การบันทึก SignOff/Release
	@RequestMapping("/page/m63f03/")
	public String render_m63f03(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);

		setFiscalYearFromSession(model, session);

		Integer fy = getCurrentFiscalYearFromSession(session);
		Objective rootObjective = entityService
				.findOneRootObjectiveByFiscalyear(fy);

		model.addAttribute("objectiveId", rootObjective.getId());
		model.addAttribute("round", 1);

		return "budgetSignOff";
	}
	
	// --------------------------------------------------------------m64f03:
	// การบันทึก SignOff/Release
	@RequestMapping("/page/m64f03/")
	public String render_m64f03(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);

		setFiscalYearFromSession(model, session);

		Integer fy = getCurrentFiscalYearFromSession(session);
		Objective rootObjective = entityService
				.findOneRootObjectiveByFiscalyear(fy);

		model.addAttribute("objectiveId", rootObjective.getId());
		model.addAttribute("round", 2);

		return "budgetSignOff";
	}
	
	// --------------------------------------------------------------m65f03:
	// การบันทึก SignOff/Release
	@RequestMapping("/page/m65f03/")
	public String render_m65f03(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);

		setFiscalYearFromSession(model, session);

		Integer fy = getCurrentFiscalYearFromSession(session);
		Objective rootObjective = entityService
				.findOneRootObjectiveByFiscalyear(fy);

		model.addAttribute("objectiveId", rootObjective.getId());
		model.addAttribute("round", 3);

		return "budgetSignOff";
	}	

	// --------------------------------------------------------------m61f04:
	// การบันทึกงบประมาณ ระดับรายการ
	@RequestMapping("/page/m61f04_1/")
	public String render_m61f04_1(Model model, HttpServletRequest request,
			HttpSession session, @Activeuser ThaicomUserDetail currentUser) {

		model.addAttribute("rootPage", false);

		setFiscalYearFromSession(model, session);

		Integer fy = getCurrentFiscalYearFromSession(session);
		Objective rootObjective = entityService
				.findOneRootObjectiveByFiscalyear(fy);

		model.addAttribute("objectiveId", rootObjective.getId());

		// check the budgetSignOff
		BudgetSignOff bso = entityService
				.findBudgetSignOffByFiscalYearAndOrganizationAndRound(fy,
						currentUser.getWorkAt(), 0);

		if (bso.getLock1Person() != null) {
			// should not be able to edit!
			model.addAttribute("readOnly", true);
		}

		return "m61f04_1";
	}
	
	// ==============================================================m62:
	// การกระทบยอดเงินงบประมาณ (m62)

	// --------------------------------------------------------------m62f01:
	// การประมวลผลการกระทบยอดเงินงบประมาณจากระดับรายการมาที่ระดับกิจกรรม
	@RequestMapping("/page/m62f01/")
	public String render_m62f01(Model model, HttpServletRequest request,
			HttpSession session,
			@Activeuser ThaicomUserDetail currentUser) {
		model.addAttribute("rootPage", true);
		model.addAttribute("user", currentUser);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		model.addAttribute("rootObjective", entityService.findOneRootObjectiveByFiscalyear(fiscalYear));
		return "m62f01";
	}

	@RequestMapping("/page/m62f01/{fiscalYear}/{objectiveId}")
	public String render_m62f01OfYear(@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId, Model model,
			HttpServletRequest request,
			@Activeuser ThaicomUserDetail currentUser) {

		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear,
				objectiveId);

		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if (objective != null) {
			logger.debug("Objective found!");

			model.addAttribute("objective", objective);
			// now construct breadcrumb?

			List<Breadcrumb> breadcrumb = entityService
					.createBreadCrumbObjective("/page/m61f03", fiscalYear,
							objective);

			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);

		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m61f03/";
		}

		return "m62f01";
	}


	// ==============================================================m63:
	// การพิจารณากรอบวงเงินเพื่อตั้งคำของบประมาณ (เข้าระบบ e-Budgeting) (m63)
	
	// --------------------------------------------------------------m63f01:
	// การปรับลดงบประมาณระดับกิจกรรมรอบที่ 1
	@RequestMapping("/page/m63f01_orig/")
	public String render_m63f01Orig(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 1);
		setFiscalYearFromSession(model, session);

		return "m63f01";
	}

	@RequestMapping("/page/m63f01/")
	public String render_m63f01(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 1);
		setFiscalYearFromSession(model, session);

		return "m65f01";
	}
	
	// --------------------------------------------------------------m63f02:
	// การปรับลดงบประมาณระดับรายการรอบที่ 1
//	@RequestMapping("/page/m63f02_orig/")
//	public String render_m63f02_orig(Model model, HttpSession session) {
//		model.addAttribute("rootPage", true);
//		model.addAttribute("round", 1);
//		setFiscalYearFromSession(model, session);
//		
//		return "m63f02";
//	}
	
	@RequestMapping("/page/m63f02/")
	public String render_m63f02(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 1);
		setFiscalYearFromSession(model, session);
		
		return "m65f02";
	}
	
	// --------------------------------------------------------------m63f04:
	// การประมวลผลก่อนการปรับลดครั้งที่ 1  (m63f04)
	@RequestMapping("/page/m63f04/")
	public String render_m63f04(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		model.addAttribute("rootObjective", entityService.findOneRootObjectiveByFiscalyear(fiscalYear));
		return "m63f04";
	}
	
	// --------------------------------------------------------------m63f05:
	// ทะเบียนรายการและระดับรายการ
	@RequestMapping("/page/m63f05/")
	public String render_m63f05(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		return "m63f05";
	}

	// --------------------------------------------------------------m63f06:
	// การประมวลผลการกระทบยอดเงินงบประมาณจากระดับรายการมาที่ระดับกิจกรรม
	@RequestMapping("/page/m63f06/")
	public String render_m63f06(Model model, HttpServletRequest request,
			HttpSession session,
			@Activeuser ThaicomUserDetail currentUser) {
		model.addAttribute("rootPage", true);
		model.addAttribute("user", currentUser);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		Objective rootObjective = entityService.findOneRootObjectiveByFiscalyear(fiscalYear);
		model.addAttribute("rootObjective", rootObjective);
		logger.debug("rootObjective.id" + rootObjective.getId());
		
		return "m63f06";
	}

	
	// ==============================================================m64:
	// การพิจารณาตามชั้นกรรมาธิการ (วาระที่ 1 - 3) (m64)
	
	// --------------------------------------------------------------m64f01:
	// การปรับลดงบประมาณระดับกิจกรรมรอบที่ 2
	@RequestMapping("/page/m64f01/")
	public String render_m64f01(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 2);
		setFiscalYearFromSession(model, session);

		return "m65f01";
	}


	// --------------------------------------------------------------m64f02:
	// การปรับลดงบประมาณระดับรายการรอบที่ 2
	@RequestMapping("/page/m64f02/")
	public String render_m64f02(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 2);
		setFiscalYearFromSession(model, session);
		
		return "m65f02";
	}

	//	@RequestMapping("/page/m64f02_orig/")
//	public String render_m64f02_orig(Model model, HttpSession session) {
//		model.addAttribute("rootPage", true);
//		model.addAttribute("round", 2);
//		setFiscalYearFromSession(model, session);
//		
//		return "m64f02";
//	}

	// --------------------------------------------------------------m64f04:
	//  การประมวลผลก่อนการปรับลดรอบที่ 2
	@RequestMapping("/page/m64f04/")
	public String render_m64f04(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("rootPage", true);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		model.addAttribute("rootObjective", entityService.findOneRootObjectiveByFiscalyear(fiscalYear));
		return "m64f04";
	}

	// --------------------------------------------------------------m64f05:
	// การปรับลดทะเบียนรายการรอบที่ 2
	@RequestMapping("/page/m64f05/")
	public String render_m64f05(Model model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("rootPage", false);
		setFiscalYearFromSession(model, session);
		return "m64f05";
	}

	// --------------------------------------------------------------m64f06:
	// การประมวลผลการกระทบยอดเงินงบประมาณจากระดับรายการมาที่ระดับกิจกรรม
	@RequestMapping("/page/m64f06/")
	public String render_m64f06(Model model, HttpServletRequest request,
			HttpSession session,
			@Activeuser ThaicomUserDetail currentUser) {
		model.addAttribute("rootPage", true);
		model.addAttribute("user", currentUser);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		Objective rootObjective = entityService.findOneRootObjectiveByFiscalyear(fiscalYear);
		model.addAttribute("rootObjective", rootObjective);
		logger.debug("rootObjective.id" + rootObjective.getId());
		
		return "m64f06";
	}
	
	// ==============================================================m65:
	// การอนุมัติงบประมาณ ตาม พ.ร.บ. (m65)
	
	// --------------------------------------------------------------m65f01:
	// การปรับลดงบประมาณระดับกิจกรรมรอบที่ 2
	@RequestMapping("/page/m65f01/")
	public String render_m65f01(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 3);
		setFiscalYearFromSession(model, session);

		return "m65f01";
	}
	
	// --------------------------------------------------------------m65f04:
	//  การประมวลผลก่อนการปรับลดรอบที่ 2
	@RequestMapping("/page/m65f04/")
	public String render_m65f04(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 3);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		model.addAttribute("rootObjective", entityService.findOneRootObjectiveByFiscalyear(fiscalYear));
		return "m65f04";
	}

	
	@RequestMapping("/page/m65f02/")
	public String render_m65f02(Model model, HttpSession session) {
		model.addAttribute("rootPage", true);
		model.addAttribute("round", 3);
		setFiscalYearFromSession(model, session);
		return "m65f02";
	}

	
	// --------------------------------------------------------------m65f06:
	// การประมวลผลการกระทบยอดเงินงบประมาณจากระดับรายการมาที่ระดับกิจกรรม
	@RequestMapping("/page/m65f06/")
	public String render_m65f06(Model model, HttpServletRequest request,
			HttpSession session,
			@Activeuser ThaicomUserDetail currentUser) {
		model.addAttribute("rootPage", true);
		model.addAttribute("user", currentUser);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		Objective rootObjective = entityService.findOneRootObjectiveByFiscalyear(fiscalYear);
		model.addAttribute("rootObjective", rootObjective);
		logger.debug("rootObjective.id" + rootObjective.getId());
		
		return "m65f06";
	}

	// ==============================================================m71:
	// --------------------------------------------------------------m71f04:
		//  การประมวลผลก่อนการปรับลดรอบที่ 2
		@RequestMapping("/page/m71f04/")
		public String render_m71f04(Model model, HttpServletRequest request, HttpSession session) {
			model.addAttribute("rootPage", true);
			Integer fiscalYear = setFiscalYearFromSession(model, session);
			model.addAttribute("rootObjective", entityService.findOneRootObjectiveByFiscalyear(fiscalYear));
			return "m71f04";
		}
	
	
	// --------------------------------------------------------------m71f02:
		// การจัดสรรงบประมาณ (m71)
	@RequestMapping("/page/m71f02/")
	public String render_m71f02(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("rootPage", true);
		setFiscalYearFromSession(model, session);
		return "m71f02";
	}
	
	// --------------------------------------------------------------m71f03:
	// การจัดสรรงบประมาณ (m71)
	@RequestMapping("/page/m71f03/")
	public String render_m71f03(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("rootPage", true);
		setFiscalYearFromSession(model, session);
		return "m71f03";
	}
	
	// --------------------------------------------------------------m72f01:
			// การจัดสรรงบประมาณเพิ่มเติม (m72)
		@RequestMapping("/page/m72f01/")
		public String render_m72f01(Model model, HttpServletRequest request, HttpSession session) {
			model.addAttribute("rootPage", true);
			setFiscalYearFromSession(model, session);
			return "m72f01";
		}
		
		// --------------------------------------------------------------m72f02:
		// การจัดสรรงบประมาณเพิ่มเติม (m72)
	@RequestMapping("/page/m72f02/")
	public String render_m72f02(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("rootPage", true);
		Integer fiscalYear = setFiscalYearFromSession(model, session);
		
		OrganizationAllocationRound round = entityService.findMaxOrgAllocRound(fiscalYear);
		model.addAttribute("round", round);
		
		return "m72f02";
	}
	
	

}
