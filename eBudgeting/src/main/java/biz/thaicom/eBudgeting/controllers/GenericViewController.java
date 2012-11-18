package biz.thaicom.eBudgeting.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;

import biz.thaicom.eBudgeting.models.bgt.BudgetType;
import biz.thaicom.eBudgeting.models.pln.Objective;
import biz.thaicom.eBudgeting.models.pln.ObjectiveTarget;
import biz.thaicom.eBudgeting.models.pln.ObjectiveTypeId;
import biz.thaicom.eBudgeting.models.pln.TargetUnit;
import biz.thaicom.eBudgeting.models.webui.Breadcrumb;
import biz.thaicom.eBudgeting.services.EntityService;
import biz.thaicom.security.models.Activeuser;
import biz.thaicom.security.models.ThaicomUserDetail;

@Controller
public class GenericViewController {

	public static Logger logger = LoggerFactory.getLogger(GenericViewController.class);
	
	private static final String webAppcontext = "eBudgeting";
	
	@Autowired
	private EntityService entityService;
	
	@RequestMapping("/jsp/{jspName}")
	public String renderJsp(@PathVariable String jspName) {
		
		return jspName;
	}
	
	@RequestMapping("/page/m2f13/")
	public String render_m2f13_fiscalYear(Model model) {
		prepareRootPage(model);
		
		return "m2f13";
	}
	
	private void prepareRootPage(Model model) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		
	}

	@RequestMapping("/page/m2f13/{fiscalYear}/{budgetTypeId}")
	public String render_m2f13(Model model, HttpServletRequest request,
			@PathVariable Integer fiscalYear,
			@PathVariable Long budgetTypeId) { 
		
		logger.debug("fiscalYear = {}, budgetTypeId = {}", fiscalYear, budgetTypeId);
		
		if(budgetTypeId == null) 
			budgetTypeId = 0L;
		
		// now we just get the hold of this budgetType
		BudgetType budgetType = entityService.findBudgetTypeById(budgetTypeId);
		
		if(budgetType != null) {
			logger.debug("BudgetType found!");
			
			model.addAttribute("budgetType", budgetType);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbBudgetType("/page/m2f13", fiscalYear, budgetType); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("budgetType", budgetType);
			
		} else {
			logger.debug("BudgetType NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m2f13/";
		}
		
		return "m2f13";
	}
	

	
//	@RequestMapping("/page/m2f06/**")
//	public String render_m2f06(Model model, HttpServletRequest request) {
//		String pattern = (String)
//		        request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); 
//		
//		String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern, 
//		        request.getServletPath());
//
//		String url = webAppcontext +  "/page/m2f06/";
//		List<Map<String,String>> breadcrumb = new ArrayList<Map<String,String>>();
//		
//		logger.debug(searchTerm);
//		if(searchTerm == null || searchTerm.length()==0) {
//			model.addAttribute("url", "/eBudgeting/Objective/root");
//			model.addAttribute("ROOT", true);
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("url", url);
//			map.put("value", "ROOT");
//			breadcrumb.add(map);
//			model.addAttribute("breadcrumb", breadcrumb);
//		} else {
//			// now tokenized the string
//			StringTokenizer token = new StringTokenizer(searchTerm,"/");
//			List<String> items = new ArrayList<String>();
//			while(token.hasMoreTokens()){
//				items.add(token.nextToken());
//			}
//			
//			if(items.size() == 1) {
//				// first part is year
//				model.addAttribute("url", "/eBudgeting/Objective/root/"+items.get(0));
//			} else {
//				model.addAttribute("url", "/eBudgeting/Objective/"+items.get(items.size()-1)+"/children");
//				model.addAttribute("lastObjectiveId", items.get(items.size()-1));
//				// now we need all parents of this object
//				
//			}
//			
//			Objective objective = null;
//			
//			// here we recontruct the breadcrumb
//			for(int i=0; i<items.size(); i++) {
//				
//				HashMap<String, String> map = new HashMap<String, String>();
//				
//				if(i > 0) {
//					url = url  + items.get(i) + "/";
//					map.put("url", url);
//					int index = objective.getIndex()+1;
//					map.put("value", objective.getType().getName() + "ที่  " + index);
//					breadcrumb.add(map);
//					
//				} else {
//					map.put("url", url);
//					map.put("value", "ROOT");
//					breadcrumb.add(map);
//					
//					map = new HashMap<String, String>();
//					url = url + items.get(i) + "/";
//					map.put("url", url);
//					map.put("value", items.get(i));
//					breadcrumb.add(map);
//
//				}
//				
//				if(i+1 < items.size()) {
//					// do this if it's not the last one
//					Long nextId = null;
//					try {
//						nextId = Long.parseLong(items.get(i+1));
//					} catch (NumberFormatException e) {
//						// we should just failed here! 
//					}
//					
//					objective = entityService.findOjectiveById(nextId);
//				}
//				
//				
//			}
//			model.addAttribute("breadcrumb", breadcrumb);
//		}
//		
//		model.addAttribute("currentPath", url);
//		return "m2f06";
//	}
	
	
	@RequestMapping("/page/m2f11/")
	public String runder_m2f11(
			Model model, HttpServletRequest request) {
		
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		
		return "m2f11";
	}
	
	@RequestMapping("/page/m2f11/{fiscalYear}/{objectiveId}")
	public String runder_m2f11OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m2f11", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m2f11/";
		}
		
		return "m2f11";

	}
	
	@RequestMapping("/page/m2f12/")
	public String runder_m2f12(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m2f12";
	}
	
	@RequestMapping("/page/m2f12/{fiscalYear}/{objectiveId}")
	public String render_m2f12OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request,
			@Activeuser ThaicomUserDetail currentUser) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m2f12", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m2f12/";
		}
		
		return "m2f12";
	}
	
	
	@RequestMapping("/page/m3f01/")
	public String runder_m3f01(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m3f01";
	}
	
	@RequestMapping("/page/m3f01/{fiscalYear}/{objectiveId}")
	public String render_m3f01OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m2f06", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m3f01/";
		}
		
		return "m3f01";
	}
	
	@RequestMapping("/page/m3f02/")
	public String render_m3f02(
			Model model, HttpServletRequest request) {
		
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		logger.debug("htt");
		return "m3f02";
	}
	
	@RequestMapping("/page/m3f02/{fiscalYear}/{objectiveId}")
	public String render_m3f02OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m3f02", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m3f02/";
		}
		
		return "m3f02";
	}
	
	@RequestMapping("/page/m3f03/")
	public String render_m3f03(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m3f03";
	}
	
	@RequestMapping("/page/m3f03/{fiscalYear}/{objectiveId}")
	public String render_m3f03OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m3f03", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m3f01/";
		}
		
		return "m3f03";
	}
	
	
	@RequestMapping("/page/m2f06/")
	public String runder_m2f06(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m2f06";
	}
	
	@RequestMapping("/page/m2f06/{fiscalYear}/{objectiveId}")
	public String render_m2f06OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m2f06", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m2f06/";
		}
		
		return "m2f06";
	}
	
	@RequestMapping("/page/m1f05/")
	public String runder_m1f05(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m1f05";
	}

	@RequestMapping("/page/m3f04/")
	public String runder_m3f04(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m3f04";
	}
	
	@RequestMapping("/page/m3f05/")
	public String runder_m3f05(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m3f05";
	}
	
	@RequestMapping("/page/m3f06/")
	public String runder_m3f06(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m3f06";
	}
	
	@RequestMapping("/page/m4f01/")
	public String runder_m4f01(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m4f01";
	}
	
	@RequestMapping("/page/m4f02/")
	public String runder_m4f02(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m4f02";
	}
	
	@RequestMapping("/page/m4f02/{fiscalYear}/{objectiveId}")
	public String render_m4f02OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m4f02", fiscalYear, objective); 
			
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
	
	@RequestMapping("/page/m1f03/")
	public String render_m1f03(
			Model model, HttpServletRequest request) {
		List<TargetUnit> targetUnits = entityService.findAllTargetUnits();		
		model.addAttribute("rootPage", true);
		model.addAttribute("targetUnits", targetUnits);
		return "m1f03";
	}
	
	@RequestMapping("/page/m2f14/")
	public String render_m2f14(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m2f14";
	}
	
	@RequestMapping("/page/m2f14/{fiscalYear}/")
	public String render_m2f14WithFiscalYear(
			@PathVariable Integer fiscalYear,
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		
		return "m2f14";
	}
	
	




	// --------------------------------------------------------------m51f01: ทะเบียนยุทธศาสตร์การจัดสรร
	@RequestMapping("/page/m51f01/")
	public String render_m51f01(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 109);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f01/{fiscalYear}")
	public String render_m51f01(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 109);
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);
		
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m51f02: ทะเบียนประเด็นยุทธศาสตร์
	@RequestMapping("/page/m51f02/")
	public String render_m51f02(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 121);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f02/{fiscalYear}")
	public String render_m51f02(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 121);
		
		String relatedTypeString = "" + ObjectiveTypeId.ยุทธศาสตร์การจัดสรรงบประมาณ.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "" +  ObjectiveTypeId.ยุทธศาสตร์การจัดสรรงบประมาณ.getName() + "" ;
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasUnit", true);
		
		model.addAttribute("hasParent", "");
		model.addAttribute("parentTypeName", "");
		model.addAttribute("parentTypeId", "");
		
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m51f03: ทะเบียนเป้าหมายเชิงยุทธศาสตร์
	@RequestMapping("/page/m51f03/")
	public String render_m51f03(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 110);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f03/{fiscalYear}")
	public String render_m51f03(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 110);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.ประเด็นยุทธศาสตร์.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.ประเด็นยุทธศาสตร์.getValue());
		
		return "objectiveRegister";
	}
	
	
	// --------------------------------------------------------------m51f04: ทะเบียนเป้าหมายบริการกระทรวง
	@RequestMapping("/page/m51f04/")
	public String render_m51f04(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 111);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f04/{fiscalYear}")
	public String render_m51f04(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 111);
		
		model.addAttribute("hasUnit", true);
		
		model.addAttribute("hasParent", "");
		model.addAttribute("parentTypeName", "");
		model.addAttribute("parentTypeId", "");
		
		String relatedTypeString = "" + ObjectiveTypeId.เป้าหมายเชิงยุทธศาสตร์.getValue() + " " + 
				ObjectiveTypeId.เป้าประสงค์เชิงนโยบาย.getValue();
				
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString ="" + ObjectiveTypeId.เป้าหมายเชิงยุทธศาสตร์.getName() + " " + 
				ObjectiveTypeId.เป้าประสงค์เชิงนโยบาย.getName();
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m51f05: ทะเบียนเป้าหมายบริการหน่วยงาน
	@RequestMapping("/page/m51f05/")
	public String render_m51f05(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 112);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f05/{fiscalYear}")
	public String render_m51f05(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 112);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.เป้าหมายบริการกระทรวง.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.เป้าหมายบริการกระทรวง.getValue());
		
		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f06: ทะเบียนแผนงาน
	@RequestMapping("/page/m51f06/")
	public String render_m51f06(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 101);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f06/{fiscalYear}")
	public String render_m51f06(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 101);
		
		String relatedTypeString = "" + ObjectiveTypeId.ประเด็นยุทธศาสตร์.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "" + ObjectiveTypeId.ประเด็นยุทธศาสตร์.getName() + "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);
		
		return "objectiveRegister";
	}
	
	
	// --------------------------------------------------------------m51f07: ทะเบียนผลผลิต/โครงการ
	@RequestMapping("/page/m51f07/")
	public String render_m51f07(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 102);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f07/{fiscalYear}")
	public String render_m51f07(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 102);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.แผนงาน.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.แผนงาน.getValue());
		
		return "objectiveRegister";
	}

	// --------------------------------------------------------------m51f08: ทะเบียนกิจกรรมหลัก
	@RequestMapping("/page/m51f08/")
	public String runder_m51f08(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 103);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f08/{fiscalYear}")
	public String render_m51f08(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

		List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m51f08", fiscalYear, null); 
		
		model.addAttribute("breadcrumb", breadcrumb.listIterator());
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 103);
		
		String relatedTypeString = "" + ObjectiveTypeId.ยุทธศาสตร์หน่วยงาน.getValue() + " " +
				ObjectiveTypeId.กลยุทธ์วิธีการหน่วยงาน.getValue() + " " +
				ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getValue() + "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "" + ObjectiveTypeId.ยุทธศาสตร์หน่วยงาน.getName() + " " +
				ObjectiveTypeId.กลยุทธ์วิธีการหน่วยงาน.getName() + " " +
				ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getName() + "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.ผลผลิตโครงการ.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.ผลผลิตโครงการ.getValue());
		
		model.addAttribute("hasUnit", true);
		
		
		return "objectiveRegister";
	}

	
	// --------------------------------------------------------------m51f09: ทะเบียนกิจกรรมรอง
	@RequestMapping("/page/m51f09/")
	public String render_m51f09(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 104);
		
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f09/{fiscalYear}")
	public String render_m51f09(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 104);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.กิจกรรมหลัก.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.กิจกรรมหลัก.getValue());
		
		return "objectiveRegister";
	}
	
	
	// --------------------------------------------------------------m51f10: ทะเบียนกิจกรรมย่อย
	@RequestMapping("/page/m51f10/")
	public String render_m51f10(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 105);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f10/{fiscalYear}")
	public String render_m51f10(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 105);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.กิจกรรมรอง.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.กิจกรรมรอง.getValue());
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m51f11: ทะเบียนกิจกรรมเสริม
	@RequestMapping("/page/m51f11/")
	public String render_m51f11(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 106);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f11/{fiscalYear}")
	public String render_m51f11(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 106);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.กิจกรรมย่อย.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.กิจกรรมย่อย.getValue());
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m51f12: ทะเบียนกิจกรรมสนับสนุน
	@RequestMapping("/page/m51f12/")
	public String render_m51f12(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 107);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f12/{fiscalYear}")
	public String render_m51f12(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 107);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.กิจกรรมเสริม.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.กิจกรรมเสริม.getValue());
		
		return "objectiveRegister";
	}
	
	
	// --------------------------------------------------------------m51f13: ทะเบียนกิจกรรมรายละเอียด
	@RequestMapping("/page/m51f13/")
	public String render_m51f13(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 108);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m51f13/{fiscalYear}")
	public String render_m51f13(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 108);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.กิจกรรมสนับสนุน.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.กิจกรรมสนับสนุน.getValue());
		return "objectiveRegister";
	}
	
	
	// --------------------------------------------------------------m51f14: ทะเบียนรายการและระดับรายการ
	@RequestMapping("/page/m51f14/")
	public String render_m51f14(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m51f14";
	}
	
	@RequestMapping("/page/m51f14/{fiscalYear}")
	public String render_m51f14(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		return "m51f14";
	}
	
	// --------------------------------------------------------------m51f15: ทะเบียนรายการกลาง
	@RequestMapping("/page/m51f15/")
	public String render_m51f15(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m51f15";
	}
	
	
	
	@RequestMapping("/page/m51f15/{fiscalYear}")
	public String render_m51f15(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		return "m51f15";
	}
	
	
	// --------------------------------------------------------------m52f01: ทะเบียนเป้าประสงค์เชิงนโยบนาย
	@RequestMapping("/page/m52f01/")
	public String render_m52f01(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 113);
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);
		
		return "objectiveRegister";
	}
	
	
	
	@RequestMapping("/page/m52f01/{fiscalYear}")
	public String render_m52f01(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 113);
		
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);
		
		return "objectiveRegister";
	}
	
	
	// --------------------------------------------------------------m53f01: ทะเบียนยุทธศาสตร์กระทรวง
	@RequestMapping("/page/m53f01/")
	public String render_m53f01(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 114);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m53f01/{fiscalYear}")
	public String render_m53f01(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 114);
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m53f02: ทะเบียนยุทธศาสตร์หน่วยงาน
	@RequestMapping("/page/m53f02/")
	public String render_m53f02(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 115);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m53f02/{fiscalYear}")
	public String render_m53f02(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 115);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.ยุทธศาสตร์กระทรวง.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.ยุทธศาสตร์กระทรวง.getValue());
		
		
		return "objectiveRegister";
		
	}
	
	
	// --------------------------------------------------------------m53f03: ทะเบียนกลยุทธ์/วิธีการหน่วยงาน
	@RequestMapping("/page/m53f03/")
	public String render_m53f03(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 116);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m53f03/{fiscalYear}")
	public String render_m53f03(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 116);
		
		model.addAttribute("hasUnit", true);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", true);
		model.addAttribute("parentTypeName", ObjectiveTypeId.ยุทธศาสตร์กระทรวง.getName());
		model.addAttribute("parentTypeId", ObjectiveTypeId.ยุทธศาสตร์กระทรวง.getValue());
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m54f01: ทะเบียนแนวทางการจัดสรรงบประมาณ
	@RequestMapping("/page/m54f01/")
	public String render_m54f01(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 118);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m54f01/{fiscalYear}")
	public String render_m54f01(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 118);
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", true);
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m55f01: ทะเบียนวิสัยทัศน์หน่วยงาน
	@RequestMapping("/page/m55f01/")
	public String render_m55f01(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 119);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m55f01/{fiscalYear}")
	public String render_m55f01(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 119);
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", "");
		
		return "objectiveRegister";
	}
	
	// --------------------------------------------------------------m55f02: ทะเบียนพันธกิจหน่วยงาน
	@RequestMapping("/page/m55f02/")
	public String render_m55f02(
			Model model, HttpServletRequest request) {
		model.addAttribute("rootPage", true);
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		model.addAttribute("typeId", 120);
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m55f02/{fiscalYear}")
	public String render_m55f02(
			Model model, @PathVariable Integer fiscalYear,
			HttpServletRequest request) {

				
		model.addAttribute("rootPage", false);
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("typeId", 120);
		
		String relatedTypeString = "";
		model.addAttribute("relatedTypeString", relatedTypeString);
		
		String relatedTypeNameString = "";
		model.addAttribute("relatedTypeNameString", relatedTypeNameString);
		
		model.addAttribute("hasParent", "");

		model.addAttribute("hasUnit", false);
		
		return "objectiveRegister";
	}
	
	@RequestMapping("/page/m61f03/")
	public String render_m63f03(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m61f03";
	}
	
	@RequestMapping("/page/m61f03/{fiscalYear}/{objectiveId}")
	public String render_m63f03OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request,
			@Activeuser ThaicomUserDetail currentUser) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m61f03", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m61f03/";
		}
		
		return "m61f03";
	}
	
	@RequestMapping("/page/m63f02/")
	public String runder_m63f02(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m63f02";
	}
	
	@RequestMapping("/page/m63f02/{fiscalYear}/{objectiveId}")
	public String render_m63f02OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m63f02", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m63f02/";
		}
		
		return "m63f02";
	}
	
	@RequestMapping("/page/m64f02/")
	public String render_m64f02(
			Model model, HttpServletRequest request) {
		
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		logger.debug("htt");
		return "m64f02";
	}
	
	@RequestMapping("/page/m64f02/{fiscalYear}/{objectiveId}")
	public String render_m64f02OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m64f02", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m64f02/";
		}
		
		return "m64f02";
	}
	
	@RequestMapping("/page/m65f02/")
	public String render_m65f02(
			Model model, HttpServletRequest request) {
		List<Objective> fiscalYears = entityService.findRootFiscalYear();		
		model.addAttribute("rootPage", true);
		model.addAttribute("fiscalYears", fiscalYears);
		return "m65f02";
	}
	
	@RequestMapping("/page/m65f02/{fiscalYear}/{objectiveId}")
	public String render_m65f02OfYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			Model model, HttpServletRequest request) {
		
		logger.debug("fiscalYear = {}, objectiveId = {}", fiscalYear, objectiveId);
		
		// now find the one we're looking for
		Objective objective = entityService.findOjectiveById(objectiveId);
		if(objective != null ) {
			logger.debug("Objective found!");
			
			model.addAttribute("objective", objective);
			// now construct breadcrumb?
			
			List<Breadcrumb> breadcrumb = entityService.createBreadCrumbObjective("/page/m65f02", fiscalYear, objective); 
			
			model.addAttribute("breadcrumb", breadcrumb.listIterator());
			model.addAttribute("rootPage", false);
			model.addAttribute("objective", objective);
			
		} else {
			logger.debug("Objective NOT found! redirect to fiscal year selection");
			// go to the root one!
			return "redirect:/page/m65f02/";
		}
		
		return "m65f01";
	}
	

}


