package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import biz.thaicom.eBudgeting.models.hrx.Organization;
import biz.thaicom.eBudgeting.models.pln.Objective;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_ACTUALBUDGET")
@SequenceGenerator(name="BGT_ACTUALBUDGET_SEQ", sequenceName="BGT_ACTUALBUDGET_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope=ActualBudget.class)
public class ActualBudget implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3582315154615939329L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_ACTUALBUDGET_SEQ")
	private Long id;
	
	@Basic
	@Column(name="AMOUNTALLOCATED")
	private Long amountAllocated;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OBJECTIVE_ID")
	private Objective forObjective;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BUDGETTYPE_BGT_ID")
	private BudgetType budgetType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ROUND_ID")
	private OrganizationAllocationRound round;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAmountAllocated() {
		return amountAllocated;
	}

	public void setAmountAllocated(Long amountAllocated) {
		this.amountAllocated = amountAllocated;
	}

	public Objective getForObjective() {
		return forObjective;
	}

	public void setForObjective(Objective forObjective) {
		this.forObjective = forObjective;
	}

	public BudgetType getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}

	public OrganizationAllocationRound getRound() {
		return round;
	}

	public void setRound(OrganizationAllocationRound round) {
		this.round = round;
	}

	public void adjustAmountAllocated(Long adjustedAmountAllocated) {
		this.amountAllocated = this.amountAllocated - adjustedAmountAllocated;
		
	}
	
	
}
