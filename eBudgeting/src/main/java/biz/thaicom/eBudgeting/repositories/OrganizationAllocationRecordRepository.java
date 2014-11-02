package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.thaicom.eBudgeting.models.bgt.OrganizationAllocationRecord;
import biz.thaicom.eBudgeting.models.pln.Objective;

public interface OrganizationAllocationRecordRepository extends
		JpaRepository<OrganizationAllocationRecord, Long> {

	@Query(""
			+ "SELECT record "
			+ "FROM OrganizationAllocationRecord record "
			+ "WHERE record.round.fiscalYear = ?1 ")
	List<OrganizationAllocationRecord> findAllByFiscalYear(Integer fiscalYear);

	
	@Query(""
			+ "SELECT record "
			+ "FROM OrganizationAllocationRecord record "
			+ "WHERE record.forObjective = ?1 ")
	List<OrganizationAllocationRecord> findAllByObjective(Objective o);


	@Query(""
			+ "SELECT record "
			+ "FROM OrganizationAllocationRecord record "
			+ "WHERE record.forObjective.id in (?3) "
			+ "		AND record.owner.id = ?1 "
			+ "		AND record.budgetType.id = ?2 ")
	List<OrganizationAllocationRecord> findAllByOnwerIdAndObjectiveIdIn(
			Long ownerId, Long budgetTypeId, List<Long> objectiveIds);

}
