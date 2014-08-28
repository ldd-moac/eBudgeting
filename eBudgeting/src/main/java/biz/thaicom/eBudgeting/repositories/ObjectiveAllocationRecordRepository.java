package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.bgt.AllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.BudgetType;
import biz.thaicom.eBudgeting.models.bgt.ObjectiveAllocationRecord;
import biz.thaicom.eBudgeting.models.pln.Objective;

public interface ObjectiveAllocationRecordRepository extends
		PagingAndSortingRepository<ObjectiveAllocationRecord, Long>, JpaSpecificationExecutor<ObjectiveAllocationRecord> {

	@Query("" +
			"SELECT distinct record " +
			"FROM ObjectiveAllocationRecord record " +
			"	LEFT JOIN FETCH record.budgetType budgetType " +
			"	INNER JOIN FETCH record.forObjective objective " +
			"WHERE objective.fiscalYear =?1 and objective.parentPath like ?2 " +
			"ORDER BY record.budgetType.id asc")
	List<ObjectiveAllocationRecord> findBudgetProposalByFiscalYearAndOwnerAndParentPath(
			Integer fiscalYear, String parentPathLikeString);

	@Query("" +
			"SELECT record " +
			"FROM ObjectiveAllocationRecord record " +
			"WHERE record.budgetType = ?1 and record.forObjective = ?2 and record.index =?3 ")
	ObjectiveAllocationRecord findOneByBudgetTypeAndObjectiveAndIndex(
			BudgetType budgetType, Objective parent, Integer index);

	@Query("" +
			"SELECT record " +
			"FROM ObjectiveAllocationRecord record " +
			"WHERE record.forObjective.fiscalYear = ?1 and record.index =?2 ")
	List<ObjectiveAllocationRecord> findAllByForObjective_fiscalYearAndIndex(
			Integer fiscalYear, Integer index);

	@Query(""
			+ "SELECT sum(record.amountAllocated) "
			+ "FROM ObjectiveAllocationRecord record "
			+ "WHERE record.forObjective = ?1 and record.index = ?2 ")
	Long findSumAllocationForObjectiveAndIndex(Objective obj, int i);

	@Query("" +
			"SELECT record " +
			"FROM ObjectiveAllocationRecord record " +
			"WHERE record.forObjective = ?1 and record.index =?2 ")
	List<ObjectiveAllocationRecord> findAllByForObjectiveAndIndex(
			Objective objectvie, int i);

	@Query(""
			+ "SELECT sum(record.amountAllocated) "
			+ "FROM ObjectiveAllocationRecord record "
			+ "WHERE record.forObjective.fiscalYear =?1 "
			+ "		AND record.forObjective.parent.name like 'ROOT' "
			+ "		AND record.index = ?2 ")
	Long findSumAllocationForObjectiveAndIndex(Integer fiscalYear, Integer round);

}
