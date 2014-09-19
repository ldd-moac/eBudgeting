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
@Table(name="BGT_ALLOCATIONRECORD")
@SequenceGenerator(name="BGT_ALLOCATIONRECORD_SEQ", sequenceName="BGT_ALLOCATIONRECORD_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AllocationRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8389152899753513205L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_ALLOCATIONRECORD_SEQ")
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
	
	@OneToMany(mappedBy="allocationRecord", cascade=CascadeType.ALL)
	private List<AllocationRecordStrategy> allocationRecordStrategies;
	
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

	public void adjustAmountAllocated(Long adjustedAmount) {
		this.amountAllocated = this.amountAllocated - adjustedAmount;
		
	}

	public List<AllocationRecordStrategy> getAllocationRecordStrategies() {
		return allocationRecordStrategies;
	}

	public void setAllocationRecordStrategies(
			List<AllocationRecordStrategy> allocationRecordStrategies) {
		this.allocationRecordStrategies = allocationRecordStrategies;
	}

	public Long getAmountAllocatedNext1Year() {
		return amountAllocatedNext1Year;
	}

	public void setAmountAllocatedNext1Year(Long amountAllocatedNext1Year) {
		this.amountAllocatedNext1Year = amountAllocatedNext1Year;
	}

	public Long getAmountAllocatedNext2Year() {
		return amountAllocatedNext2Year;
	}

	public void setAmountAllocatedNext2Year(Long amountAllocatedNext2Year) {
		this.amountAllocatedNext2Year = amountAllocatedNext2Year;
	}

	public Long getAmountAllocatedNext3Year() {
		return amountAllocatedNext3Year;
	}

	public void setAmountAllocatedNext3Year(Long amountAllocatedNext3Year) {
		this.amountAllocatedNext3Year = amountAllocatedNext3Year;
	}
	
}
