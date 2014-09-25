package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_ALLOCRECORDSTRATEGY")
@SequenceGenerator(name="BGT_ALLOCRECORDSTRATEGY_SEQ", sequenceName="BGT_ALLOCRECORDSTRATEGY_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AllocationRecordStrategy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3051546495641121782L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_ALLOCRECORDSTRATEGY_SEQ")
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name="ALLOCATIONRECORD_ID")
	private AllocationRecord allocationRecord;
	
	@Basic
	private Long totalCalculatedAmount;
	
	@Basic
	private Long amountAllocatedNext1Year;
	@Basic
	private Long amountAllocatedNext2Year;
	@Basic
	private Long amountAllocatedNext3Year;
	
	@OneToMany
	@JoinTable(name="BGT_ALLOCREC_PROPOSALSTRGY")
	private List<ProposalStrategy> proposalStrategies;
	
	@ManyToOne
	private FormulaStrategy strategy;
	
	@OneToMany(mappedBy="allocationRecordStrategy", cascade=CascadeType.ALL, orphanRemoval=true) 
	private List<RequestColumn> requestColumns;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AllocationRecord getAllocationRecord() {
		return allocationRecord;
	}

	public void setAllocationRecord(AllocationRecord allocationRecord) {
		this.allocationRecord = allocationRecord;
	}

	public Long getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}

	public void setTotalCalculatedAmount(Long totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}

	public List<ProposalStrategy> getProposalStrategies() {
		return proposalStrategies;
	}

	public void setProposalStrategies(List<ProposalStrategy> proposalStrategies) {
		this.proposalStrategies = proposalStrategies;
	}

	public FormulaStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(FormulaStrategy strategy) {
		this.strategy = strategy;
	}

	public List<RequestColumn> getRequestColumns() {
		return requestColumns;
	}

	public void setRequestColumns(List<RequestColumn> requestColumns) {
		this.requestColumns = requestColumns;
	}

	public void adjustTotalCalculatedAmount(Long adjustedAmount) {
		if(this.totalCalculatedAmount != null) {
			this.totalCalculatedAmount = this.totalCalculatedAmount - adjustedAmount;
		}
		
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
