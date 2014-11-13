package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.thaicom.eBudgeting.models.bgt.OrganizationAllocationRound;

public interface OrganizationAllocationRoundRepository extends 
	JpaRepository<OrganizationAllocationRound, Long> {

	@Query(""
			+ "SELECT round "
			+ "FROM OrganizationAllocationRound round "
			+ "WHERE round.fiscalYear = ?1 "
			+ "ORDER BY round.round ASC ")
	List<OrganizationAllocationRound> findAllByFiscalYear(Integer fiscalYear);
	
	@Query(""
			+ "SELECT round "
			+ "FROM OrganizationAllocationRound round "
			+ "WHERE round.fiscalYear = ?1 "
			+ "AND round.round = (select max(r2.round) FROM OrganizationAllocationRound r2 where r2.fiscalYear = ?1) ")
	OrganizationAllocationRound findMaxOrgAllocRound(Integer fiscalYear);

}
