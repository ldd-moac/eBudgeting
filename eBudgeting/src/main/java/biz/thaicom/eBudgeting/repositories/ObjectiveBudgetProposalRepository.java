package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.bgt.BudgetType;
import biz.thaicom.eBudgeting.models.bgt.ObjectiveBudgetProposal;
import biz.thaicom.eBudgeting.models.hrx.Organization;
import biz.thaicom.eBudgeting.models.pln.Objective;

public interface ObjectiveBudgetProposalRepository extends
		JpaSpecificationExecutor<ObjectiveBudgetProposal>, PagingAndSortingRepository<ObjectiveBudgetProposal, Long> {

	@Query("" +
			"SELECT obp " +
			"FROM ObjectiveBudgetProposal obp " +
			"	INNER JOIN FETCH obp.forObjective objective " +
			"	INNER JOIN FETCH obp.budgetType type " +
			"	LEFT OUTER JOIN FETCH obp.targets target " +
			"	LEFT OUTER JOIN FETCH target.unit unit " +
			"WHERE objective.id = ?1 and obp.owner.id = ?2 ")
	public List<ObjectiveBudgetProposal> findAllByForObjective_IdAndOwner_Id(
			Long objectiveId, Long ownerId);

	public ObjectiveBudgetProposal findByForObjectiveAndOwnerAndBudgetType(Objective o,
			Organization workAt, BudgetType budgetType);

	@Query("" +
			"SELECT sum(proposal.amountRequest) " +
			"FROM ObjectiveBudgetProposal proposal " +
			"WHERE proposal.forObjective.parent is null " +
			"	AND proposal.forObjective.fiscalYear = ?1 " +
			"	AND proposal.owner = ?2 ")
	public Long findSumTotalOfOwner(Integer fiscalYear, Organization workAt);
	
	@Query("" +
			"SELECT owner.id, sum(proposal.amountRequest) " +
			"FROM ObjectiveBudgetProposal proposal " +
			"WHERE proposal.forObjective.parent is null " +
			"	AND proposal.forObjective.fiscalYear = ?1 "
			+ "GROUP BY proposal.owner ")
	public List<Object[]>findSumTotalOfOwnerGroupByOwner(Integer fiscalYear);

	@Query("" +
			"SELECT distinct proposal " +
			"FROM ObjectiveBudgetProposal proposal " +
			"	INNER JOIN FETCH proposal.forObjective objective " +
			"	INNER JOIN FETCH proposal.owner owner " +
			"WHERE objective.fiscalYear =?1 and objective.parentPath like ?2 " +
			"ORDER BY proposal.budgetType.id asc")
	public List<ObjectiveBudgetProposal> findObjBudgetProposalByFiscalYearAndParentPath(
			Integer fiscalYear, String parentPathLikeString);

	@Query(""
			+ "SELECT obp "
			+ "FROM ObjectiveBudgetProposal obp "
			+ "WHERE obp.owner.id = ?1 "
			+ "		AND obp.bugetType.id = ?2 "
			+ "		AND obp.forObjective in (?3) ")
	public List<ObjectiveBudgetProposal> findAllByOnwerIdAndObjectiveIdIn(
			Long ownerId, Long budgetTypeId, List<Long> oldParentId);


}
