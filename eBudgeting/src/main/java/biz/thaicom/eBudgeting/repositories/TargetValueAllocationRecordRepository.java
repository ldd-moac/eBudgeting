package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.pln.Objective;
import biz.thaicom.eBudgeting.models.pln.ObjectiveTarget;
import biz.thaicom.eBudgeting.models.pln.TargetValueAllocationRecord;

public interface TargetValueAllocationRecordRepository extends
		JpaSpecificationExecutor<TargetValueAllocationRecord>, PagingAndSortingRepository<TargetValueAllocationRecord, Long> {
	
	public TargetValueAllocationRecord findOneByIndexAndForObjective(Integer index, Objective objective);

	
	public List<TargetValueAllocationRecord> findAllByForObjectiveAndIndex(
			Objective forObjective, int index);


	public TargetValueAllocationRecord findOneByTargetAndForObjectiveAndIndex(
			ObjectiveTarget target, Objective forObjective, int index);


	public TargetValueAllocationRecord findOneByIndexAndForObjectiveAndTarget(
			int i, Objective o, ObjectiveTarget target);


	@Query(""
			+ "SELECT tvar "
			+ "FROM TargetValueAllocationRecord tvar "
			+ "WHERE tvar.target = ?1")
	public List<TargetValueAllocationRecord> findAllByTarget(ObjectiveTarget t);

}
