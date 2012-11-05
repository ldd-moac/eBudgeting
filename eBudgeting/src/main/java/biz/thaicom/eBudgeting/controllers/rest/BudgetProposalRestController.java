package biz.thaicom.eBudgeting.controllers.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.type.TypeFactory;

import biz.thaicom.eBudgeting.models.bgt.AllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.BudgetProposal;
import biz.thaicom.eBudgeting.models.bgt.ProposalStrategy;
import biz.thaicom.eBudgeting.services.EntityService;
import biz.thaicom.security.models.Activeuser;
import biz.thaicom.security.models.ThaicomUserDetail;

@Controller
public class BudgetProposalRestController {
	private static final Logger logger = LoggerFactory.getLogger(BudgetProposalRestController.class);
	
	@Autowired
	private EntityService entityService;
	
	
	@RequestMapping("/BudgetProposal/{budgetProposalId}/")
	public @ResponseBody BudgetProposal findBudgetProposalById(
			@PathVariable Long budgetProposalId, 
			@Activeuser ThaicomUserDetail activeUser) {
		
		 

		return entityService.findBudgetProposalById(budgetProposalId);
	}
	
	
	@RequestMapping(value="/BudgetProposal/find/{fiscalYear}/{objectiveId}/{budgetTypeId}", method=RequestMethod.GET)
	public @ResponseBody List<BudgetProposal> findBudgetProposal(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			@PathVariable Long budgetTypeId){
		return entityService.findBudgetProposalByObjectiveIdAndBudgetTypeId(objectiveId, budgetTypeId);
	}
	
	@RequestMapping(value="/ProposalStrategy/find/{fiscalYear}/{objectiveId}", method=RequestMethod.GET)
	public @ResponseBody List<ProposalStrategy> findProposalStrategyByFiscalyearAndObjective(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			@Activeuser ThaicomUserDetail currentUser){
	
		return entityService.findProposalStrategyByFiscalyearAndObjective(fiscalYear, currentUser.getWorkAt().getId(), objectiveId);
		
	}
	
	@RequestMapping(value="/ProposalStrategy/findAll/{fiscalYear}/{objectiveId}", method=RequestMethod.GET)
	public @ResponseBody List<ProposalStrategy> findAllProposalStrategyByFiscalyearAndObjective(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			@Activeuser ThaicomUserDetail currentUser){
	
		return entityService.findAllProposalStrategyByFiscalyearAndObjective(fiscalYear, objectiveId);
		
	}
	
	
	@RequestMapping(value="/ProposalStrategy/BudgetProposal/{budgetProposalId}", method=RequestMethod.POST)
	public @ResponseBody List<ProposalStrategy> findProposalStrategyByBudgetProposal(
			@PathVariable Long budgetProposalId){
		
		return entityService.findProposalStrategyByBudgetProposal(budgetProposalId);
		
	}
	
	@RequestMapping(value="/ProposalStrategy/{id}", method=RequestMethod.DELETE)
	public @ResponseBody ProposalStrategy deleteProposalStrategy(
			@PathVariable Long id){
		return entityService.deleteProposalStrategy(id);
	}
	
	@RequestMapping(value="/ProposalStrategy/{id}", method=RequestMethod.PUT) 
	public @ResponseBody ProposalStrategy updateProposalStrategy(
			@PathVariable Long id,
			@RequestBody JsonNode data) throws JsonParseException, JsonMappingException, IOException{
		
		// we just pass this to entityJPA
		return entityService.updateProposalStrategy(id, data);		
		
	}
	
	@RequestMapping(value="/ProposalStrategy/{budgetProposalId}/{formulaStrategyId}", method=RequestMethod.POST)
	public @ResponseBody ProposalStrategy saveProposalStrategy (
			@PathVariable Long budgetProposalId,
			@PathVariable Long formulaStrategyId,
			@RequestBody ProposalStrategy strategy){
		
		
		return entityService.saveProposalStrategy(strategy, budgetProposalId, formulaStrategyId);
		
	}
	

	@RequestMapping(value="/BudgetProposal", method=RequestMethod.POST)
	public @ResponseBody BudgetProposal saveBudgetProposal (
			@RequestBody BudgetProposal proposal,
			@Activeuser ThaicomUserDetail currentUser){
		
		proposal.setOwner(currentUser.getWorkAt());
		
		return entityService.saveBudgetProposal(proposal);
		
	}
	
	@RequestMapping(value="/AllocationRecord/{fiscalYear}/R{round}", method=RequestMethod.GET)
	public @ResponseBody String initAllocationRecord(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser){
		
		
		
		return entityService.initAllocationRecord(fiscalYear, round);
		
	}
	
	@RequestMapping(value="/ReservedBudget/{fiscalYear}/initialize", method=RequestMethod.GET)
	public @ResponseBody String initBudgetReserved(
			@PathVariable Integer fiscalYear,
			@Activeuser ThaicomUserDetail currentUser){
		
		
		
		return entityService.initReservedBudget(fiscalYear);
		
	}
	
	@RequestMapping(value="/AllocationRecord/{id}", method=RequestMethod.PUT)
	public @ResponseBody AllocationRecord updateAllocationRecord(
			@PathVariable Long id,
			@RequestBody JsonNode data,
			@Activeuser ThaicomUserDetail currentUser){
		
		return entityService.updateAllocationRecord(id, data);
		
	}
	
	@RequestMapping(value="/BudgetProposalsAndReservedBudget/", method=RequestMethod.PUT)
	public @ResponseBody String updateBudgetProposalAndReservedBudget(
			@RequestBody JsonNode data){
		

		Boolean result = entityService.updateBudgetProposalAndReservedBudget(data);
		
		return "success";
	}
	
	
	@ExceptionHandler(value=EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Boolean handleEntityNotFoundExeption(final EntityNotFoundException e, 
			final HttpServletRequest request) {
		logger.error(e.toString());
		Boolean success = false;
		return success;
	}
	
	@ExceptionHandler(value=Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody String handleException(final Exception e, final HttpServletRequest request) {
		logger.error(e.toString());
		e.printStackTrace();
		return "failed: " + e.toString();
		
	}

}