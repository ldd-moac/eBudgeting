package biz.thaicom.eBudgeting.controllers.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

import biz.thaicom.eBudgeting.controllers.error.RESTError;
import biz.thaicom.eBudgeting.models.bgt.ActualBudget;
import biz.thaicom.eBudgeting.models.bgt.AdditionalBudgetAllocation;
import biz.thaicom.eBudgeting.models.bgt.AllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.BudgetProposal;
import biz.thaicom.eBudgeting.models.bgt.BudgetSignOff;
import biz.thaicom.eBudgeting.models.bgt.BudgetSignOffLog;
import biz.thaicom.eBudgeting.models.bgt.ObjectiveAllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.ObjectiveBudgetProposal;
import biz.thaicom.eBudgeting.models.bgt.OrganizationAllocationRound;
import biz.thaicom.eBudgeting.models.bgt.ProposalStrategy;
import biz.thaicom.eBudgeting.services.EntityService;
import biz.thaicom.security.models.Activeuser;
import biz.thaicom.security.models.ThaicomUserDetail;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Throwables;

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
	
	
	@RequestMapping(value="/BudgetProposal/{budgetProposalId}/additionalAllocation", method=RequestMethod.POST)
	public @ResponseBody AdditionalBudgetAllocation additionalAllocation(
			@PathVariable Long budgetProposalId,
			@RequestParam Long amount) {
		
		
		AdditionalBudgetAllocation aba = entityService.additionalAllocationToProposal(budgetProposalId, amount); 
		
		logger.debug("New additionalBudgetAllocation Id: " + aba.getId());
		
		return aba;
		
		
	};
	
	@RequestMapping(value="/BudgetProposal/find/{fiscalYear}/{objectiveId}/{budgetTypeId}", method=RequestMethod.GET)
	public @ResponseBody List<BudgetProposal> findBudgetProposal(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			@PathVariable Long budgetTypeId){
		return entityService.findBudgetProposalByObjectiveIdAndBudgetTypeId(objectiveId, budgetTypeId);
	}
	
	
	@RequestMapping(value="/BudgetProposal/find/{fiscalYear}/{objectiveId}", method=RequestMethod.GET)
	public @ResponseBody List<BudgetProposal> findBudgetProposal(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId){
		return entityService.findBudgetProposalByObjectiveId(objectiveId);
	}
	
	@RequestMapping(value="/ObjectiveBudgetProposal/{fiscalYear}/{objectiveId}", method=RequestMethod.GET) 
	public @ResponseBody List<ObjectiveBudgetProposal> findObjecitveProposal(
			@PathVariable Integer fiscalYear,
			@PathVariable Long objectiveId,
			@Activeuser ThaicomUserDetail currentUser) {
		return entityService.findObjectiveBudgetproposalByObjectiveIdAndOwnerId(objectiveId, currentUser.getWorkAt().getId());
	}
	
	@RequestMapping(value="/ObjectiveBudgetProposal/{id}", method=RequestMethod.PUT)
	public @ResponseBody ObjectiveBudgetProposal updateObjectiveBudgetProposal(
			@PathVariable Long id,
			@RequestBody JsonNode node,
			@Activeuser ThaicomUserDetail currentUser) {
		return entityService.saveObjectiveBudgetProposal(currentUser.getWorkAt(), node);
	}
	
	@RequestMapping(value="/ObjectiveBudgetProposal", method=RequestMethod.POST)
	public @ResponseBody ObjectiveBudgetProposal saveObjectiveBudgetProposal(
			@RequestBody JsonNode node,
			@Activeuser ThaicomUserDetail currentUser) {
		return entityService.saveObjectiveBudgetProposal(currentUser.getWorkAt(), node);
	}
	
	@RequestMapping(value="/ObjectiveBudgetProposal/{id}", method=RequestMethod.DELETE)
	public @ResponseBody ObjectiveBudgetProposal deleteObjectiveBudgetProposal(
			@PathVariable Long id,
			@Activeuser ThaicomUserDetail currentUser) {
		return entityService.deleteObjectiveBudgetProposal(id);
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
	
	@RequestMapping(value="/ProposalStrategy/{id}/updateTotalCalculatedAllocatedAmount", method=RequestMethod.POST)
	public @ResponseBody String proposalStrategyUpageAllocatedAmount(
			@PathVariable Long id,
			@RequestParam Long totalCalculatedAllocatedAmount){
		
		entityService.updateProposalStrategyTotalCalculatedAllocatedAmount(id, totalCalculatedAllocatedAmount);
		
		return "success";
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
		
	@RequestMapping(value="/AllocationRecordStrategy/{id}", method=RequestMethod.PUT) 
	public @ResponseBody ProposalStrategy updateAllocationRecordStrategy(
			@PathVariable Long id,
			@RequestBody JsonNode data) throws JsonParseException, JsonMappingException, IOException{
		
		// we just pass this to entityJPA
		return entityService.updateAllocationRecordStrategy(id, data);		
		
	}

	
	
	@RequestMapping(value="/OrganizationAllocationRecord/LotsUpdate", method=RequestMethod.PUT)
	public @ResponseBody String updateLotsOrganizationAllocationRecord(
			@RequestBody JsonNode node,
			@Activeuser ThaicomUserDetail currentUser) throws Exception {
		entityService.saveLotsOrganizationAllocationRecord(node);
		return "OK";
	}
	
	@RequestMapping(value="/BudgetProposal/LotsUpdate", method=RequestMethod.PUT)
	public @ResponseBody String updateLotsBudgetProposal(
			@RequestBody JsonNode node,
			@Activeuser ThaicomUserDetail currentUser) throws Exception {
		entityService.saveLotsBudgetProposal(node);
		return "OK";
	}
	
	@RequestMapping(value="/BudgetProposal", method=RequestMethod.POST)
	public @ResponseBody BudgetProposal saveBudgetProposal (
			@RequestBody JsonNode proposal,
			@Activeuser ThaicomUserDetail currentUser){
		
		return entityService.saveBudgetProposal(proposal, currentUser);
		
	}
	

//	@RequestMapping(value="/BudgetProposal1", method=RequestMethod.POST)
//	public @ResponseBody BudgetProposal saveBudgetProposal1 (
//			@RequestBody BudgetProposal proposal,
//			@Activeuser ThaicomUserDetail currentUser){
//		
//		proposal.setOwner(currentUser.getWorkAt());
//		
//		//return entityService.saveBudgetProposal(proposal);
//		
//	}
	
	
	@RequestMapping(value="/ObjectiveAllocationRecord/{id}", method=RequestMethod.GET)
	public @ResponseBody ObjectiveAllocationRecord findObjectiveAllocationRecord(
			@PathVariable Long id) {
		return entityService.findObjectiveAllocationRecordById(id);
	}
	@RequestMapping(value="/ObjectiveAllocationRecord/{id}", method=RequestMethod.PUT)
	public @ResponseBody String updateObjectiveAllocationRecord(
			@PathVariable Long id,
			@RequestBody JsonNode data,
			@Activeuser ThaicomUserDetail currentUser){
		
		entityService.updateObjectiveAllocationRecord(id, data);
		return "ok";
	}
	
	@RequestMapping(value="/AllocationRecord/{id}", method=RequestMethod.GET)
	public @ResponseBody AllocationRecord findAllocationRecord(
			@PathVariable Long id) {
		return entityService.findAllocationRecordById(id);
	}
	
	@RequestMapping(value="/AllocationRecord/{fiscalYear}/R{round}", method=RequestMethod.GET)
	public @ResponseBody String initAllocationRecord(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser){
		
		
		logger.debug("init alloc record of "+ fiscalYear + " round " + round);
		return entityService.initAllocationRecord(fiscalYear, round);
		
	}
	
	@RequestMapping(value="/OrganizationAllocationRecord/{fiscalYear}/newRound", method=RequestMethod.GET)
	public @ResponseBody String organizationAllocationRecordNewRound(
			@PathVariable Integer fiscalYear,
			@Activeuser ThaicomUserDetail currentUser){
		
		
		
		return entityService.organizationAllocationRecordNewRound(fiscalYear);
		
	}
	
	@RequestMapping(value="/ReservedBudget/{fiscalYear}/initialize", method=RequestMethod.GET)
	public @ResponseBody String initBudgetReserved(
			@PathVariable Integer fiscalYear,
			@Activeuser ThaicomUserDetail currentUser){
		
		return entityService.initReservedBudget(fiscalYear);
		
	}
	
	@RequestMapping(value="/ReservedBudget/{id}/amountReserved/{amountReserved}", method=RequestMethod.PUT)
	public @ResponseBody String updateReservedBudgetAmount(
			@PathVariable Long id,
			@PathVariable Long amountReserved) {
		
		entityService.updateReservedBudget(id, amountReserved);
		
		return "ok";
	}
	
	@RequestMapping(value="/ReservedBudget/{id}", method=RequestMethod.PUT)
	public @ResponseBody String updateReservedBudget(
			@PathVariable Long id,
			@RequestBody JsonNode data,
			@Activeuser ThaicomUserDetail currentUser){
		
		entityService.updateReservedBudget(id, data.get("amountReserved").asLong());
		return "ok";
	}
	
	@RequestMapping(value="/ActualBudget/{id}", method=RequestMethod.PUT)
	public @ResponseBody String updateActualBudget(
			@PathVariable Long id,
			@RequestBody JsonNode data,
			@Activeuser ThaicomUserDetail currentUser){
		
		entityService.updateActualBudget(id, data.get("amountAllocated").asLong());
		return "ok";
	}
	
	@RequestMapping(value="/ActualBudget/", method=RequestMethod.POST)
	public @ResponseBody String saveActualBudget(
			@PathVariable Long id,
			@RequestBody JsonNode data,
			@Activeuser ThaicomUserDetail currentUser){
		
		ActualBudget ab = entityService.saveActualBudget(data);
		return ab.getId().toString();
	}
	
	
	@RequestMapping(value="/AllocationRecord/{id}", method=RequestMethod.PUT)
	public @ResponseBody String updateAllocationRecord(
			@PathVariable Long id,
			@RequestBody JsonNode data,
			@Activeuser ThaicomUserDetail currentUser){
		
		entityService.updateAllocationRecord(id, data);
		return "ok";
	}
	
	@RequestMapping(value="/BudgetProposalsAndReservedBudget/", method=RequestMethod.PUT)
	public @ResponseBody String updateBudgetProposalAndReservedBudget(
			@RequestBody JsonNode data){
		

		Boolean result = entityService.updateBudgetProposalAndReservedBudget(data);
		
		return "success";
	}
	
	@RequestMapping(value="/BudgetSignOff/{fiscalYear}/R{round}/updateCommand/{command}", method=RequestMethod.GET)
	public @ResponseBody BudgetSignOffLog updateBudgetSignOff(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser,
			@PathVariable String command){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		BudgetSignOffLog budgetSignOffLog = entityService.updateBudgetSignOff(fiscalYear, currentUser, round, command);
		
		return budgetSignOffLog;
		
	}
	
	@RequestMapping(value="/BudgetProposal/sumTotalOfOwner/{fiscalYear}/Round/{round}") 
	public @ResponseBody List<Long> findSumTotalProposalOfOwner(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser) {
		
		List<Long> returnList = new ArrayList<Long>();
		
		if(round == 0) {
			returnList.add(entityService.findSumTotalBudgetProposalOfOwner(fiscalYear, currentUser.getWorkAt()));
			returnList.add(entityService.findSumTotalObjectiveBudgetProposalOfOwner(fiscalYear, currentUser.getWorkAt()));
		} else {
			returnList.add(entityService.findSumTotalAllocationRound(fiscalYear, round-1));
			returnList.add(entityService.findSumTotalObjectiveAllocationRound(fiscalYear, round-1));
		}
		
		
		return returnList;
	}
	
	
	@RequestMapping(value="/BudgetProposal/sumTotalOfOwnerAll/{fiscalYear}/Round/{round}") 
	public @ResponseBody SortedMap<Long, List<Object>> findSumTotalProposalOfOwnerAll(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser) {
		
		SortedMap<Long, List<Object>> returnMap = new TreeMap<Long, List<Object>>(); 
		
		if(round == 0) {
			returnMap = entityService.findSumTotalOfOwnerAll(fiscalYear);
		} else {
			// we need to get allocation round!
		}
		
		
		return returnMap;
	}
	
	@RequestMapping(value="/BudgetProposal/{fiscalYear}/copyFromProposalToObjectiveProposal")
	public @ResponseBody String copyFromProposalToObjectiveProposal(
			@PathVariable Integer fiscalYear,
			@Activeuser ThaicomUserDetail currentUser) {
		entityService.copyFromProposalToObjectiveProposal(fiscalYear, currentUser.getWorkAt());
		return "success";
	}
	
	@RequestMapping(value="/BudgetProposal/{fiscalYear}/copyFromAllocationToObjectiveAllocation/R{roundNum}")
	public @ResponseBody String copyFromAllocationToObjectiveAllocation(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer roundNum,
			@Activeuser ThaicomUserDetail currentUser) {
		entityService.copyFromAllocationToObjectiveAllocation(fiscalYear, roundNum);
		return "success";
	}
 	
	@RequestMapping(value="/BudgetSignOff/{fiscalYear}/Round/{round}")
	public @ResponseBody BudgetSignOff findBudgetSignOffByFiscalYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser) {
		
		BudgetSignOff so = entityService.findBudgetSignOffByFiscalYearAndOrganizationAndRound(fiscalYear, currentUser.getWorkAt(),round);
		
		if(so.getLock1TimeStamp()!=null) {
			logger.debug("so.getLock1: " + so.getLock1TimeStamp().toString());
		} 
		if(so.getUnLock1TimeStamp()!=null) {
			logger.debug("so.getUnLock1: " + so.getUnLock1TimeStamp().toString());
		} 
		
		
		if(so.getLock2TimeStamp()!=null) {
			logger.debug("so.getLock2: " + so.getLock2TimeStamp().toString());
		}
		if(so.getUnLock2TimeStamp()!=null) {
			logger.debug("so.getUnLock2: " + so.getUnLock2TimeStamp().toString());
		}
		return so;
		
	}
	
	@RequestMapping(value="/BudgetSignOffLog/{fiscalYear}/Round/{round}")
	public @ResponseBody List<BudgetSignOffLog> findBudgetSignOffLogByFiscalYear(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer round,
			@Activeuser ThaicomUserDetail currentUser) {
		List<BudgetSignOffLog> logs = entityService.findAllBudgetSignOffLog(fiscalYear, round, currentUser);
		return logs;
	}
	
	@RequestMapping(value="/OrganizationAllocationRound/{fiscalYear}/maxRound")
	public @ResponseBody OrganizationAllocationRound findOrganizationAllocationRoundByFiscalYearAndMaxRound(
			@PathVariable Integer fiscalYear,
			@Activeuser ThaicomUserDetail currentUser) {
		OrganizationAllocationRound round = entityService.findMaxOrgAllocRound(fiscalYear);
		return round;
	}
	
	@RequestMapping(value="/OrganizationAllocationRound/{fiscalYear}")
	public @ResponseBody List<OrganizationAllocationRound> findOrganizationAllocationRoundByFiscalYear(
			@PathVariable Integer fiscalYear,
			@Activeuser ThaicomUserDetail currentUser) {
		List<OrganizationAllocationRound> rounds = entityService.findAllOrganizationAllocationRoundByFiscalYear(fiscalYear);
		return rounds;
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
