package biz.thaicom.eBudgeting.models.pln;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.thaicom.eBudgeting.models.bgt.AllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.BudgetProposal;
import biz.thaicom.eBudgeting.models.bgt.BudgetType;
import biz.thaicom.eBudgeting.models.bgt.ObjectiveAllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.ObjectiveBudgetProposal;
import biz.thaicom.eBudgeting.models.bgt.ProposalStrategy;
import biz.thaicom.eBudgeting.models.bgt.RequestColumn;
import biz.thaicom.eBudgeting.models.bgt.ReservedBudget;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="PLN_OBJECTIVE")
@SequenceGenerator(name="PLN_OBJECTIVE_SEQ", sequenceName="PLN_OBJECTIVE_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Objective implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(Objective.class);
	
	/**
	 * SerialUID 
	 */
	private static final long serialVersionUID = 6280652136722537800L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLN_OBJECTIVE_SEQ")
	private Long id;
	
	@Basic
	private String name;
	
	@Basic
	private String code;
	
	@Basic
	private Integer fiscalYear;
	
	@Basic
	private String parentPath;
	
	@Basic
	private Boolean isLeaf;
	
	@Basic
	private Integer parentLevel;
	
	@Basic
	private Integer lineNumber;
	
	@Basic
	@Column(name="IDX")
	private Integer index;

	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name="NAME_PLN_OBJECTIVENAME_ID", nullable=false)
	@Fetch(FetchMode.JOIN)
	private ObjectiveName objectiveName;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TYPE_PLN_OBJECTIVETYPE_ID", nullable=false)
	private ObjectiveType type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENT_PLN_OBJECTIVE_ID")
	private Objective parent;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private Set<ObjectiveDetail> detail;
	
	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
	@OrderBy("id")
	private List<Objective> children;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private List<BudgetProposal> proposals;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private List<ObjectiveBudgetProposal> objectiveProposals;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private List<AllocationRecord> allocationRecords;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private List<ObjectiveAllocationRecord> objectiveAllocationRecords;
	
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private List<TargetValueAllocationRecord> targetValueAllocationRecords;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY)
	private List<ReservedBudget> reservedBudgets;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="BGT_OBJECTIVE_BUDGETTYPE")
	private List<BudgetType> budgetTypes;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PLN_JOIN_OBJECTIVE_TARGET")
	private  List<ObjectiveTarget> targets;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PLN_JOIN_OBJECTIVE_UNIT")
	private  List<TargetUnit> units;
	
	@OneToMany(mappedBy="forObjective", fetch=FetchType.LAZY) 
	private List<TargetValue> targetValues;
	
	@Transient
	private List<BudgetProposal> filterProposals;	
	
	@Transient
	private List<ObjectiveBudgetProposal> filterObjectiveBudgetProposals;
	
	@Transient
	private List<AllocationRecord> allocationRecordsR1;

	@Transient
	private List<AllocationRecord> allocationRecordsR2;
	
	@Transient
	private List<AllocationRecord> allocationRecordsR3;
	
	@Transient
	private List<ObjectiveAllocationRecord> objectiveAllocationRecordsR1;

	@Transient
	private List<ObjectiveAllocationRecord> objectiveAllocationRecordsR2;
	
	@Transient
	private List<ObjectiveAllocationRecord> objectiveAllocationRecordsR3;
	
	@Transient
	private List<TargetValue> filterTargetValues;
	
	@Transient
	private List<BudgetProposal> sumBudgetTypeProposals;

	@Transient
	private List<ObjectiveBudgetProposal> sumBudgetTypeObjectiveProposals;
	
	
	//Normal Getter/Setter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public ObjectiveType getType() {
		return type;
	}
	public void setType(ObjectiveType type) {
		this.type = type;
	}
	public List<Objective> getChildren() {
		return children;
	}
	public void setChildren(List<Objective> children) {
		this.children = children;
	}
	public Objective getParent() {
		return parent;
	}
	public void setParent(Objective parent) {
		this.parent = parent;
	}
	
	public Integer getParentLevel() {
		return parentLevel;
	}
	public void setParentLevel(Integer parentLevel) {
		this.parentLevel = parentLevel;
	}
	public List<BudgetType> getBudgetTypes() {
		return budgetTypes;
	}
	public void setBudgetTypes(List<BudgetType> budgetTypes) {
		this.budgetTypes = budgetTypes;
	}
	public List<BudgetProposal> getProposals() {
		return proposals;
	}
	public void setProposals(List<BudgetProposal> proposals) {
		this.proposals = proposals;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public List<AllocationRecord> getAllocationRecords() {
		return allocationRecords;
	}
	public void setAllocationRecords(List<AllocationRecord> allocationRecords) {
		this.allocationRecords = allocationRecords;
	}
	public Integer getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	public ObjectiveName getObjectiveName() {
		return objectiveName;
	}
	public void setObjectiveName(ObjectiveName objectiveName) {
		this.objectiveName = objectiveName;
	}
	public List<TargetUnit> getUnits() {
		return units;
	}
	public void setUnits(List<TargetUnit> units) {
		this.units = units;
	}
	public void addUnit(TargetUnit unit) {
		if(this.units == null) {
			this.units = new ArrayList<TargetUnit>();
		}
		this.units.add(unit);
	}
	
	public List<ObjectiveAllocationRecord> getObjectiveAllocationRecordsR1() {
		if(objectiveAllocationRecordsR1 == null) {
			objectiveAllocationRecordsR1 = new ArrayList<ObjectiveAllocationRecord>();
		}
		return objectiveAllocationRecordsR1;
	}
	public void setObjectiveAllocationRecordsR1(List<ObjectiveAllocationRecord> objectiveAllocationRecordsR1) {
		this.objectiveAllocationRecordsR1 = objectiveAllocationRecordsR1;
	}
	public List<ObjectiveAllocationRecord> getObjectiveAllocationRecordsR2() {
		if(objectiveAllocationRecordsR2 == null) {
			objectiveAllocationRecordsR2 = new ArrayList<ObjectiveAllocationRecord>();
		}
		return objectiveAllocationRecordsR2;
	}
	public void setObjectiveAllocationRecordsR2(List<ObjectiveAllocationRecord> objectiveAllocationRecordsR2) {
		this.objectiveAllocationRecordsR2 = objectiveAllocationRecordsR2;
	}
	public List<ObjectiveAllocationRecord> getObjectiveAllocationRecordsR3() {
		if(objectiveAllocationRecordsR3 == null) {
			objectiveAllocationRecordsR3 = new ArrayList<ObjectiveAllocationRecord>();
		}
		return objectiveAllocationRecordsR3;
	}
	public void setObjectiveAllocationRecordsR3(List<ObjectiveAllocationRecord> objectiveAllocationRecordsR3) {
		this.objectiveAllocationRecordsR3 = objectiveAllocationRecordsR3;
	}
	
	public List<AllocationRecord> getAllocationRecordsR1() {
		if(allocationRecordsR1 == null) {
			allocationRecordsR1 = new ArrayList<AllocationRecord>();
		}
		return allocationRecordsR1;
	}
	public void setAllocationRecordsR1(List<AllocationRecord> allocationRecordsR1) {
		this.allocationRecordsR1 = allocationRecordsR1;
	}
	public List<AllocationRecord> getAllocationRecordsR2() {
		if(allocationRecordsR2 == null) {
			allocationRecordsR2 = new ArrayList<AllocationRecord>();
		}
		return allocationRecordsR2;
	}
	public void setAllocationRecordsR2(List<AllocationRecord> allocationRecordsR2) {
		this.allocationRecordsR2 = allocationRecordsR2;
	}
	public List<AllocationRecord> getAllocationRecordsR3() {
		if(allocationRecordsR3 == null) {
			allocationRecordsR3 = new ArrayList<AllocationRecord>();
		}
		return allocationRecordsR3;
	}
	public void setAllocationRecordsR3(List<AllocationRecord> allocationRecordsR3) {
		this.allocationRecordsR3 = allocationRecordsR3;
	}
	
	// loading barebone information about the entity
	public void doBasicLazyLoad() {
		//now we get one parent and its type
		if(this.getParent() != null) {
			this.getParent().getId();
		} 
//		if(this.getBudgetType() != null) {
//			if(this.getBudgetType().getParent() !=null) {
//				this.getBudgetType().getParent().getId();
//			}
//			this.getBudgetType().getId();
//		}
		if(this.getType() != null) {
			this.getType().getId();
			if(this.getType().getParent() != null) {
				this.getType().getParent().getId();
			}
			if(this.getType().getChildren() != null) {
				this.getType().getChildren().size();
			}
		}
		
		if(this.getTargets() != null) {
			this.getTargets().size();
		}
		
		if(this.getChildren() != null) {
			this.getChildren().size();
			logger.debug("children size: " + this.getChildren().size());
		}
		
	}
	public void doEagerLoad() {
		this.getType().getId();
//		if(this.getBudgetType() != null) {
//			this.getBudgetType().getId();
//		}
		if(this.getChildren() != null && this.getChildren().size() > 0) {
			// now load all the children
			for(Objective obj : this.children) {
				obj.doEagerLoad();
			}
		}
	}
	
	public void doEagerLoadWithBudgetProposal(Boolean isChildrenTraversal) {
		this.getType().getId();
//		if(this.getBudgetType() != null) {
//			this.getBudgetType().getId();
//		}
		
		if(this.getProposals() != null && this.getProposals().size() > 0) {
			for(BudgetProposal proposal : this.getProposals()) {
				if(proposal.getProposalStrategies() != null && proposal.getProposalStrategies().size() >0 ) {
					for(ProposalStrategy proposeStrategy: proposal.getProposalStrategies()) {
						proposeStrategy.getFormulaStrategy().getFormulaColumns().size();
						proposeStrategy.getRequestColumns().size();
					}
				}
			}
		}
		
		if(isChildrenTraversal) {
		
			if(this.getChildren() != null && this.getChildren().size() > 0) {
				// now load all the children
				for(Objective obj : this.children) {
					obj.doEagerLoadWithBudgetProposal(true);
				}
			}
		}
	}
	
	
	
	public List<BudgetProposal> getFilterProposals() {
		return filterProposals;
	}
	
	public void setFilterProposals(List<BudgetProposal> proposals){
		this.filterProposals = proposals;
	}

	public void addfilterProposal(BudgetProposal proposal) {
		if(this.filterProposals == null) {
			this.filterProposals = new ArrayList<BudgetProposal>();
		}
		
		this.filterProposals.add(proposal);
		
	}
	public List<BudgetProposal> getSumBudgetTypeProposals() {
		return sumBudgetTypeProposals;
	}
	public void setSumBudgetTypeProposals(
			List<BudgetProposal> sumBudgetTypeProposals) {
		this.sumBudgetTypeProposals = sumBudgetTypeProposals;
	}
	
	public void addToSumBudgetTypeProposalsOnlyAmount(BudgetProposal proposal) {
		this.addToSumBudgetTypeProposals(proposal, false);
	}
	
	public void addToSumBudgetTypeObjectiveProposalsOnlyAmount(
			ObjectiveBudgetProposal proposal) {
		if(this.sumBudgetTypeObjectiveProposals == null) {
			this.sumBudgetTypeObjectiveProposals = new ArrayList<ObjectiveBudgetProposal>();
			
		}
		
		for(ObjectiveBudgetProposal p : sumBudgetTypeObjectiveProposals) {
			if(p.getBudgetType().getId().equals(proposal.getBudgetType().getId())) {
				p.setAmountRequest(p.getAmountRequest() + proposal.getAmountRequest());
				p.setAmountRequestNext1Year(p.getAmountRequestNext1Year() + proposal.getAmountRequestNext1Year());
				p.setAmountRequestNext2Year(p.getAmountRequestNext2Year() + proposal.getAmountRequestNext2Year());
				p.setAmountRequestNext3Year(p.getAmountRequestNext3Year() + proposal.getAmountRequestNext3Year());
				
				return;
			}
		}
		
		ObjectiveBudgetProposal p = new ObjectiveBudgetProposal();
		p.setAmountRequest(proposal.getAmountRequest());
		p.setAmountRequestNext1Year(proposal.getAmountRequestNext1Year());
		p.setAmountRequestNext2Year(proposal.getAmountRequestNext2Year());
		p.setAmountRequestNext3Year(proposal.getAmountRequestNext3Year());
		p.setBudgetType(proposal.getBudgetType());
		p.setForObjective(this);
		
		this.sumBudgetTypeObjectiveProposals.add(p);
		return;
		
	}
	
	public void addToSumBudgetTypeProposals(BudgetProposal proposal, Boolean addRequestColumn){
		//logger.debug("++++++++++++++++++++++objective.id: {} and proposal.budgetType.id: {}", this.id, proposal.getBudgetType()==null?"null":proposal.getBudgetType().getId());
		
		// find if there is one 
		if(this.sumBudgetTypeProposals == null) {
			this.sumBudgetTypeProposals = new ArrayList<BudgetProposal>();
		}
		
		for(BudgetProposal p : sumBudgetTypeProposals) {
//			logger.debug("p.getBudgetType().getId(): {}", p.getBudgetType().getId() );
//			logger.debug("proposal.getBudgetType().getId(): {}", proposal.getBudgetType().getId() );
			if(p.getBudgetType() == null) {
				p.setAmountRequest(p.getAmountRequest() + proposal.getAmountRequest());
				
				if(proposal.getAmountAllocated() != null && p.getAmountAllocated() != null) {
					p.setAmountAllocated(p.getAmountAllocated() + proposal.getAmountAllocated());
				}
				
				p.setAmountRequestNext1Year(p.getAmountRequestNext1Year() + proposal.getAmountRequestNext1Year());
				p.setAmountRequestNext2Year(p.getAmountRequestNext2Year() + proposal.getAmountRequestNext2Year());
				p.setAmountRequestNext3Year(p.getAmountRequestNext3Year() + proposal.getAmountRequestNext3Year());
				return;
			} else{
				// logger.debug(">>>>>>>>>>>>>>>>>p.budgetType.Id: {}", p.getBudgetType().getId());
				if(p.getBudgetType().getId() == proposal.getBudgetType().getId()) {
			
					
				// so we can add here and then return
				

				
					p.setAmountRequest(p.getAmountRequest() + proposal.getAmountRequest());
					
					if(proposal.getAmountAllocated() != null && p.getAmountAllocated() != null) {
						p.setAmountAllocated(p.getAmountAllocated() + proposal.getAmountAllocated());
					}
					
					p.setAmountRequestNext1Year(p.getAmountRequestNext1Year() + proposal.getAmountRequestNext1Year());
					p.setAmountRequestNext2Year(p.getAmountRequestNext2Year() + proposal.getAmountRequestNext2Year());
					p.setAmountRequestNext3Year(p.getAmountRequestNext3Year() + proposal.getAmountRequestNext3Year());
					
					if(proposal.getProposalStrategies() != null) {
						if(p.getProposalStrategies() == null) {
							p.setProposalStrategies(new ArrayList<ProposalStrategy>());
						}
						
						for(ProposalStrategy ps : proposal.getProposalStrategies()) {
							Boolean found = false;
							
							for(ProposalStrategy proposalStrategy: p.getProposalStrategies()) {
								//logger.debug("proposal id : " + ps.getId() );
								if(proposalStrategy.getFormulaStrategy() != null && 
										ps.getFormulaStrategy() != null &&
										ps.getFormulaStrategy().getId() == proposalStrategy.getFormulaStrategy().getId()) {
									found = true;
									
									// update TotalCalculated
									proposalStrategy.setTotalCalculatedAmount(proposalStrategy.getTotalCalculatedAmount() + ps.getTotalCalculatedAmount());
									proposalStrategy.setAmountRequestNext1Year(proposalStrategy.getAmountRequestNext1Year() + ps.getAmountRequestNext1Year());
									proposalStrategy.setAmountRequestNext2Year(proposalStrategy.getAmountRequestNext2Year() + ps.getAmountRequestNext2Year());
									proposalStrategy.setAmountRequestNext3Year(proposalStrategy.getAmountRequestNext3Year() + ps.getAmountRequestNext3Year());
									
									
									if(addRequestColumn == true) {
									
										// now we begin update RequestColumn
										for(RequestColumn rc : ps.getRequestColumns()) {
											//loop thourgh our own rc for matching columns!
											if(proposalStrategy.getRequestColumns()!=null) {
												for(RequestColumn requestColumn : proposalStrategy.getRequestColumns()) {
													//logger.debug("xxxx rc: "  + rc.getId());
													if(rc.getColumn().getId() == requestColumn.getColumn().getId()) {
														requestColumn.setAmount(requestColumn.getAmount() + rc.getAmount());
													}
												}
											} else {
												proposalStrategy.setRequestColumns(new ArrayList<RequestColumn>());
												//loop thourgh our own rc for matching columns!
												RequestColumn newRc = new RequestColumn();
												newRc.setProposalStrategy(proposalStrategy);
												newRc.setAllocatedAmount(rc.getAllocatedAmount());
												newRc.setAmount(rc.getAmount());
												newRc.setColumn(rc.getColumn());
												
												proposalStrategy.getRequestColumns().add(newRc);
											}
											
										}
									}
								}
							}
							
							if(!found) {
								// we create a new one
								ProposalStrategy newPs=new ProposalStrategy();
								newPs.setFormulaStrategy(ps.getFormulaStrategy());
								newPs.setName(ps.getName());
								newPs.setTargetValue(ps.getTargetValue());
								newPs.setTotalCalculatedAmount(ps.getTotalCalculatedAmount());
								newPs.setAmountRequestNext1Year(ps.getAmountRequestNext1Year());
								newPs.setAmountRequestNext2Year(ps.getAmountRequestNext2Year());
								newPs.setAmountRequestNext3Year(ps.getAmountRequestNext3Year());
								
								if(addRequestColumn == true) {
								
									newPs.setRequestColumns(new ArrayList<RequestColumn>());
									
									for(RequestColumn rc : ps.getRequestColumns()) {
										//loop thourgh our own rc for matching columns!
										RequestColumn newRc = new RequestColumn();
										newRc.setProposalStrategy(newPs);
										newRc.setAllocatedAmount(rc.getAllocatedAmount());
										newRc.setAmount(rc.getAmount());
										newRc.setColumn(rc.getColumn());
										
										newPs.getRequestColumns().add(newRc);
									}
								}
								
							}
						}
					}
					return;
				}
				
			}
			
		}
		
		// otherwise add ths to new proposal
		BudgetProposal p = new BudgetProposal();
		
		//logger.debug("************************************************proposal.getBugetType() : {}", proposal.getBudgetType()==null?"null":proposal.getBudgetType().getId()) ;
		
		p.setBudgetType(proposal.getBudgetType());
		p.setForObjective(proposal.getForObjective());
		
		p.setAmountRequest(proposal.getAmountRequest());
		p.setAmountAllocated(proposal.getAmountAllocated());
		p.setAmountRequestNext1Year(proposal.getAmountRequestNext1Year());
		p.setAmountRequestNext2Year(proposal.getAmountRequestNext2Year());
		p.setAmountRequestNext3Year(proposal.getAmountRequestNext3Year());
		
		List<ProposalStrategy> proposalStrategies = new ArrayList<ProposalStrategy>();
		for(ProposalStrategy ps : proposal.getProposalStrategies()) {
			ProposalStrategy proposalStrategy = new ProposalStrategy();
			proposalStrategy.setFormulaStrategy(ps.getFormulaStrategy());
			proposalStrategy.setTotalCalculatedAmount(ps.getTotalCalculatedAmount());
			proposalStrategy.setAmountRequestNext1Year(ps.getAmountRequestNext1Year());
			proposalStrategy.setAmountRequestNext2Year(ps.getAmountRequestNext2Year());
			proposalStrategy.setAmountRequestNext3Year(ps.getAmountRequestNext3Year());
			
			if(addRequestColumn == true) {
				List<RequestColumn> requestColumns = new ArrayList<RequestColumn>();
				for(RequestColumn rc : ps.getRequestColumns()) {
					RequestColumn requestColumn = new RequestColumn();
					requestColumn.setAmount(rc.getAmount());
					requestColumn.setColumn(rc.getColumn());
					requestColumn.setProposalStrategy(proposalStrategy);
					
					requestColumns.add(requestColumn);
				}
			
				proposalStrategy.setRequestColumns(requestColumns);
			}
			
			proposalStrategies.add(proposalStrategy);
		}
		
		p.setProposalStrategies(proposalStrategies);
		
		
		this.sumBudgetTypeProposals.add(p);
	}
	
	public List<ReservedBudget> getReservedBudgets() {
		return reservedBudgets;
	}
	public void setReservedBudgets(List<ReservedBudget> reservedBudgets) {
		this.reservedBudgets = reservedBudgets;
	}
	public List<ObjectiveTarget> getTargets() {
		return targets;
	}
	public void setTargets(List<ObjectiveTarget> targets) {
		this.targets = targets;
	}
	public List<TargetValue> getFilterTargetValues() {
		return filterTargetValues;
	}
	public void setFilterTargetValues(List<TargetValue> filterTargetValues) {
		this.filterTargetValues = filterTargetValues;
	}
	
	public List<ObjectiveBudgetProposal> getObjectiveProposals() {
		return objectiveProposals;
	}
	public void setObjectiveProposals(
			List<ObjectiveBudgetProposal> objectiveProposals) {
		this.objectiveProposals = objectiveProposals;
	}
	public List<ObjectiveBudgetProposal> getFilterObjectiveBudgetProposals() {
		return filterObjectiveBudgetProposals;
	}
	public void setFilterObjectiveBudgetProposals(
			List<ObjectiveBudgetProposal> filterObjectiveBudgetProposals) {
		this.filterObjectiveBudgetProposals = filterObjectiveBudgetProposals;
	}
	public List<TargetValueAllocationRecord> getTargetValueAllocationRecords() {
		return targetValueAllocationRecords;
	}
	public void setTargetValueAllocationRecords(
			List<TargetValueAllocationRecord> targetValueAllocationRecords) {
		this.targetValueAllocationRecords = targetValueAllocationRecords;
	}
	public List<Long> getParentIds() {
		// OK will have to travesre back up .. we can get the parent path
		String parentPath = this.parentPath;
		if(this.parentPath == null) {
			return null;
		}
		
		//we will tokenize and put it in List<Long>
		List<Long> parentIds = new ArrayList<Long>();
		
		StringTokenizer tokens = new StringTokenizer(parentPath, ".");
		
		while(tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			//convert to Long
			Long parentId = Long.parseLong(token);
			
			parentIds.add(parentId);
		}
		return parentIds;
	}
	public Boolean addTarget(ObjectiveTarget ot) {
		if(ot == null) {
			
		}
		
		
		if(this.targets != null) {
			if(this.targets.lastIndexOf(ot) < 0) {
				this.targets.add(ot);
				return true;
			}
		} else {
			this.targets = new ArrayList<ObjectiveTarget>();
			this.targets.add(ot);
			return true;
		}
		
		return false;
		
	}
	public void addfilterTargetValue(TargetValue tv) {
		if(this.filterTargetValues == null) {
			this.filterTargetValues  = new ArrayList<TargetValue>();
		}
		
		this.filterTargetValues.add(tv);
	}
	public List<TargetValue> getTargetValues() {
		return targetValues;
	}
	public void setTargetValues(List<TargetValue> targetValues) {
		this.targetValues = targetValues;
	}
	public void addReplaceUnit(TargetUnit unit) {
		this.units = new ArrayList<TargetUnit>();
		this.units.add(unit);
		
	}
	public Integer calculateAndSetLineNumberForChildren() {
		Integer nextLineNumber = this.lineNumber+1;
		
		if(this.getChildren() == null || this.getChildren().size() == 0) {
			return nextLineNumber;
		}
		
		for(Objective child : this.children) {
			child.setLineNumber(nextLineNumber);
			nextLineNumber = child.calculateAndSetLineNumberForChildren();
		}
		
		return nextLineNumber;
		
	}
	public List<ObjectiveAllocationRecord> getObjectiveAllocationRecords() {
		return objectiveAllocationRecords;
	}
	public void setObjectiveAllocationRecords(
			List<ObjectiveAllocationRecord> objectiveAllocationRecords) {
		this.objectiveAllocationRecords = objectiveAllocationRecords;
	}
	public List<ObjectiveBudgetProposal> getSumBudgetTypeObjectiveProposals() {
		return sumBudgetTypeObjectiveProposals;
	}
	public void setSumBudgetTypeObjectiveProposals(
			List<ObjectiveBudgetProposal> sumBudgetTypeObjectiveProposals) {
		this.sumBudgetTypeObjectiveProposals = sumBudgetTypeObjectiveProposals;
	}


	
	

	
	
}
