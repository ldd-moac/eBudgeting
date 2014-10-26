package biz.thaicom.eBudgeting.controllers.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Throwables;

import biz.thaicom.eBudgeting.controllers.error.RESTError;
import biz.thaicom.eBudgeting.models.pln.ObjectiveType;
import biz.thaicom.eBudgeting.services.EntityService;

@Controller
public class ObjectiveTypeRestController {

	private static final Logger logger = LoggerFactory.getLogger(ObjectiveTypeRestController.class);
	
	@Autowired
	private EntityService entityService;
	
	@RequestMapping("/ObjectiveType/root")
	public @ResponseBody List<Integer> getRootFiscalYear() {
		return entityService.findObjectiveTypeRootFiscalYear();

	}
	
	@RequestMapping("/ObjectiveType/root/{fiscalYear}")
	public @ResponseBody List<ObjectiveType> getRootByFiscalYear(
			@PathVariable Integer fiscalYear) {
		return entityService.findObjectiveTypeByFiscalYearEager(fiscalYear, null);
		
	}
	
	@RequestMapping(value="/ObjectiveType/{id}", method=RequestMethod.GET)
	public @ResponseBody ObjectiveType getObjectiveTypeById(@PathVariable Long id) {
		return entityService.findObjectiveTypeById(id);
	}
	
	@RequestMapping(value="/ObjectiveType/{id}/childrenName", method=RequestMethod.GET)
	public @ResponseBody String getObjectiveTypeChildrenName(@PathVariable Long id) {
		return entityService.findObjectiveTypeChildrenNameOf(id);
	}
	
	
	
	@ExceptionHandler(value=Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody RESTError handleException(final Exception e, final HttpServletRequest request) {
    	RESTError error = new RESTError();
    	error.setMessage(e.getMessage());
    	
    	String trace = Throwables.getStackTraceAsString(e);
        error.setStackTrace(trace);
        
        error.setDate(new Date());
        
        return error;
	}
	
}
