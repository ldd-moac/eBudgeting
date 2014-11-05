package biz.thaicom.eBudgeting.exception;

import java.util.List;

import biz.thaicom.eBudgeting.models.bgt.BudgetProposal;
import biz.thaicom.eBudgeting.models.pln.Objective;

public class ObjectiveHasBudgetProposalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8891435932418377739L;
	
	private List<BudgetProposal> proposals;
	private Objective objective;
	
	public ObjectiveHasBudgetProposalException(Objective objective, List<BudgetProposal> proposals) {
		this.objective = objective;
		this.proposals = proposals;
	}

	public List<BudgetProposal> getProposals() {
		return proposals;
	}

	public void setProposals(List<BudgetProposal> proposals) {
		this.proposals = proposals;
	}
	
	public String toString() {
		return "มีการตั้งงบประมาณสำหรับกิจกรรมนี้แล้ว";
	}

	@Override
	public String getMessage() {
		String str = "(" + this.objective.getCode() + ")" + this.objective.getName();
		String idList = "[";
		for(BudgetProposal p : proposals) {
			idList = idList + "id#" + p.getId() +", ";
		}
		idList = idList + "]";
		
		return "กิจกรรม " + str + " มีรายการตั้งงบประมาณในฐานข้อมูล : " + idList;
	}

	
	
	
}
