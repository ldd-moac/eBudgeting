package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
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

import biz.thaicom.eBudgeting.models.hrx.Organization;
import biz.thaicom.eBudgeting.models.pln.Objective;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_BUDGETPROPOSAL")
@SequenceGenerator(name="BGT_BUDGETPROPOSAL_SEQ", sequenceName="BGT_BUDGETPROPOSAL_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class BudgetProposal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6691460847982541290L;

	
	// Field
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_BUDGETPROPOSAL_SEQ")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BUDGETTYPE_BGT_BUDGETTYPE_ID")
	private BudgetType budgetType;
	
	@Basic
	private String name;
	

	
	@Basic
	private Long amountRequest;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORGANIZATION_ID")
	private Organization owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OBJECTIVE_ID")
	private Objective forObjective;
	
	@OneToMany(mappedBy="proposal", fetch=FetchType.LAZY)
	private List<AllocationRecord> allocationRecords;	

	@OneToMany(mappedBy="proposal", fetch=FetchType.LAZY)
	private List<RequestColumn> requestColumns;
	
}