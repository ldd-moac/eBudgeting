package biz.thaicom.eBudgeting.controllers.rest;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Throwables;

import biz.thaicom.eBudgeting.controllers.error.RESTError;
import biz.thaicom.eBudgeting.models.pln.ObjectiveName;
import biz.thaicom.eBudgeting.models.webui.PageUI;
import biz.thaicom.eBudgeting.services.EntityService;
import biz.thaicom.security.models.User;

@Controller
public class UserRestController {
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	
	@Autowired
	private EntityService entityService;
	
	@RequestMapping(value="/User/page/{pageNumber}") 
	public @ResponseBody Page<User> getPagedObjectiveByFiscalYearAndType(
			@PathVariable Integer pageNumber,
			@RequestParam (required=false) String query) {
		PageRequest pageRequest =
	            new PageRequest(pageNumber - 1, PageUI.PAGE_SIZE, Sort.Direction.ASC, "username");
		
		if(query == null || query.length() ==0 ) {
			query = "%";
		} else {
			query = "%"  + query + "%";
 		}
		
		return entityService.findUser(query, pageRequest);
	}
	
	
	@RequestMapping(value="/User/{id}", method=RequestMethod.GET)
	public @ResponseBody User findOneUser(
			@PathVariable Long id) {
		return entityService.findOneUser(id);
	}
	
	@RequestMapping(value="/User/{id}", method=RequestMethod.PUT)
	public @ResponseBody User updateUser(
			@PathVariable Long id,
			@RequestBody JsonNode node) {
		return entityService.updateUser(node);
	}
	
	@RequestMapping(value="/User/", method=RequestMethod.POST)
	public @ResponseBody User saveUser(
			@RequestBody JsonNode node) {
		return entityService.saveUser(node);
	}
	
	@RequestMapping(value="/User/{id}", method=RequestMethod.DELETE)
	public @ResponseBody User deleteUser(
			@PathVariable Long id) {
		return entityService.deleteUser(id);
	}
	
	@ExceptionHandler(value=Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody RESTError handleException(final Exception e, final HttpServletRequest request) {
		e.printStackTrace();
    	RESTError error = new RESTError();
    	error.setMessage(e.getMessage());
    	
    	String trace = Throwables.getStackTraceAsString(e);
        error.setStackTrace(trace);
        
        error.setDate(new Date());
        
        return error;
	}
	
}
