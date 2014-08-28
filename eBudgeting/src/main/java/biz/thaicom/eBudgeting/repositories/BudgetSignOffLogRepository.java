package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.bgt.BudgetSignOffLog;
import biz.thaicom.eBudgeting.models.hrx.Organization;

public interface BudgetSignOffLogRepository extends
		JpaSpecificationExecutor<BudgetSignOffLog>, PagingAndSortingRepository<BudgetSignOffLog, Long> {

	@Query(""
			+ "SELECT log "
			+ "FROM BudgetSignOffLog log "
			+ "WHERE log.fiscalYear=?1 "
			+ "		AND log.round=?2 "
			+ "		AND log.organization=?3 "
			+ "ORDER BY log.timestamp desc")
	List<BudgetSignOffLog> findAllByFiscalYearRoundAndOrganization(
			Integer fiscalYear, Integer round, Organization org);

	@Query(""
			+ "SELECT log "
			+ "FROM BudgetSignOffLog log "
			+ "WHERE log.fiscalYear=?1 "
			+ "		AND log.round=?2 "
			+ "ORDER BY log.timestamp desc")	List<BudgetSignOffLog> findAllByFiscalYearRound(Integer fiscalYear,
			Integer round);

}
