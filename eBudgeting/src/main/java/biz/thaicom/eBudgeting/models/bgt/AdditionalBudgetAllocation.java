package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_ADDITIONALBGTALLOCATION")
@SequenceGenerator(name="BGT_ADDITIONALBGTALLOC_SEQ", sequenceName="BGT_ADDITIONALBGTALLOC_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AdditionalBudgetAllocation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6689897606672159502L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_ADDITIONALBGTALLOC_SEQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="PROPOSAL_ID")
	private BudgetProposal proposal;
	
	@Basic
	private Long amount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date allocationTimeStamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BudgetProposal getProposal() {
		return proposal;
	}

	public void setProposal(BudgetProposal proposal) {
		this.proposal = proposal;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getAllocationTimeStamp() {
		return allocationTimeStamp;
	}

	public void setAllocationTimeStamp(Date allocationTimeStamp) {
		this.allocationTimeStamp = allocationTimeStamp;
	}
	
	
	
}
