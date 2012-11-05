package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_PROPOSALSTRATEGY")
@SequenceGenerator(
		name="BGT_PROPOSALSTRATEGY_SEQ", 
		sequenceName="BGT_PROPOSALSTRATEGY_SEQ", allocationSize=1)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProposalStrategy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8803018798530957948L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_PROPOSALSTRATEGY_SEQ")
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name="BUDGETPROPOSAL_ID")
	private BudgetProposal proposal;
	
	@Basic
	private Long totalCalculatedAmount;
	
	@Basic 
	private Long totalCalculatedAllocatedAmount;
	
	@Basic
	private String name;
	@Basic
	private Long amountRequestNext1Year;
	
	@Basic
	private Long amountRequestNext2Year;
	
	@Basic
	private Long amountRequestNext3Year;
	
	@ManyToOne
	@JoinColumn(name="FORMULASTRATEGY_ID")
	private FormulaStrategy formulaStrategy;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="proposalStrategy",
			cascade=CascadeType.REMOVE)
	private List<RequestColumn> requestColumns;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FormulaStrategy getFormulaStrategy() {
		return formulaStrategy;
	}

	public void setFormulaStrategy(FormulaStrategy formulaStrategy) {
		this.formulaStrategy = formulaStrategy;
	}

	public List<RequestColumn> getRequestColumns() {
		return requestColumns;
	}

	public void setRequestColumns(List<RequestColumn> requestColumns) {
		this.requestColumns = requestColumns;
	}

	public BudgetProposal getProposal() {
		return proposal;
	}

	public void setProposal(BudgetProposal proposal) {
		this.proposal = proposal;
	}

	public Long getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}

	public void setTotalCalculatedAmount(Long totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getAmountRequestNext1Year() {
		return amountRequestNext1Year;
	}

	public void setAmountRequestNext1Year(Long amountRequestNext1Year) {
		this.amountRequestNext1Year = amountRequestNext1Year;
	}

	public Long getAmountRequestNext2Year() {
		return amountRequestNext2Year;
	}

	public void setAmountRequestNext2Year(Long amountRequestNext2Year) {
		this.amountRequestNext2Year = amountRequestNext2Year;
	}

	public Long getAmountRequestNext3Year() {
		return amountRequestNext3Year;
	}

	public void setAmountRequestNext3Year(Long amountRequestNext3Year) {
		this.amountRequestNext3Year = amountRequestNext3Year;
	}

	public void adjustTotalCalculatedAmount(Long adjustedAmount) {
		this.totalCalculatedAmount = this.totalCalculatedAmount - adjustedAmount;
	}

	public void adjustAmountRequestNext1Year(Long adjustedAmountRequestNext1Year) {
		if(this.amountRequestNext1Year == null) this.amountRequestNext1Year = 0L;
		this.amountRequestNext1Year = this.amountRequestNext1Year - adjustedAmountRequestNext1Year;
		
	}

	public void adjustAmountRequestNext2Year(Long adjustedAmountRequestNext2Year) {
		if(this.amountRequestNext2Year == null) this.amountRequestNext2Year = 0L;
		this.amountRequestNext2Year = this.amountRequestNext2Year - adjustedAmountRequestNext2Year;
		
	}

	public void adjustAmountRequestNext3Year(Long adjustedAmountRequestNext3Year) {
		if(this.amountRequestNext3Year == null) this.amountRequestNext3Year = 0L;
		this.amountRequestNext3Year = this.amountRequestNext3Year - adjustedAmountRequestNext3Year;
		
	}

	public Long getTotalCalculatedAllocatedAmount() {
		return totalCalculatedAllocatedAmount;
	}

	public void setTotalCalculatedAllocatedAmount(
			Long totalCalculatedAllocatedAmount) {
		this.totalCalculatedAllocatedAmount = totalCalculatedAllocatedAmount;
	}
	
	

}