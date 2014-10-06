package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import biz.thaicom.eBudgeting.models.pln.Objective;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_OBJALLOCATIONRECORD")
@SequenceGenerator(name="BGT_OBJALLOCATIONRECORD_SEQ", sequenceName="BGT_OBJALLOCATIONRECORD_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ObjectiveAllocationRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8389152899753513205L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_OBJALLOCATIONRECORD_SEQ")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OBJECTIVE_PLN_OBJECTIVE_ID")
	private Objective forObjective;
	
	@Basic
	private Long amountAllocated;
	
	@Basic
	private Long amountAllocatedNext1Year;
	@Basic
	private Long amountAllocatedNext2Year;
	@Basic
	private Long amountAllocatedNext3Year;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BUDGETTYPE_BGT_ID")
	private BudgetType budgetType;
	
	@Basic
	@Column(name="IDX")
	private Integer index;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Objective getForObjective() {
		return forObjective;
	}

	public void setForObjective(Objective forObjective) {
		this.forObjective = forObjective;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getAmountAllocated() {
		return amountAllocated;
	}

	public void setAmountAllocated(Long amountAllocated) {
		this.amountAllocated = amountAllocated;
	}

	public BudgetType getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}

	public void adjustAmountAllocated(Long adjustedAmount, Long adjustedAmountNext1Year, Long adjustedAmountNext2Year, Long adjustedAmountNext3Year) {
		this.amountAllocated = this.amountAllocated - adjustedAmount;
		this.amountAllocatedNext1Year = this.getAmountAllocatedNext1Year() - adjustedAmountNext1Year;
		this.amountAllocatedNext2Year = this.getAmountAllocatedNext2Year() - adjustedAmountNext2Year;
		this.amountAllocatedNext3Year = this.getAmountAllocatedNext3Year() - adjustedAmountNext3Year;
		
	}
	public Long getAmountAllocatedNext1Year() {
		if(this.amountAllocatedNext1Year == null)  amountAllocatedNext1Year = 0L;
		return amountAllocatedNext1Year;
	}

	public void setAmountAllocatedNext1Year(Long amountAllocatedNext1Year) {
		this.amountAllocatedNext1Year = amountAllocatedNext1Year;
	}

	public Long getAmountAllocatedNext2Year() {
		if(this.amountAllocatedNext2Year == null)  amountAllocatedNext2Year = 0L;
		return amountAllocatedNext2Year;
	}

	public void setAmountAllocatedNext2Year(Long amountAllocatedNext2Year) {
		this.amountAllocatedNext2Year = amountAllocatedNext2Year;
	}

	public Long getAmountAllocatedNext3Year() {
		if(this.amountAllocatedNext3Year == null)  amountAllocatedNext3Year = 0L;
		return amountAllocatedNext3Year;
	}

	public void setAmountAllocatedNext3Year(Long amountAllocatedNext3Year) {
		this.amountAllocatedNext3Year = amountAllocatedNext3Year;
	}

}
