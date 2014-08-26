package biz.thaicom.eBudgeting.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.bgt.BudgetSignOffLog;

public interface BudgetSignOffLogRepository extends
		JpaSpecificationExecutor<BudgetSignOffLog>, PagingAndSortingRepository<BudgetSignOffLog, Long> {

}
