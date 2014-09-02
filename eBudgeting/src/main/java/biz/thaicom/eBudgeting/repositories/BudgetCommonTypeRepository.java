package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.bgt.BudgetCommonType;
import biz.thaicom.eBudgeting.models.bgt.BudgetType;

public interface BudgetCommonTypeRepository extends
		JpaSpecificationExecutor<BudgetCommonType>, PagingAndSortingRepository<BudgetCommonType, Long> {

	List<BudgetCommonType> findAllByFiscalYear(Integer fiscalYear, Sort sort);
	
	@Query(""
			+ "SELECT bct "
			+ "FROM BudgetCommonType bct "
			+ "WHERE bct.fiscalYear = ?1 AND bct.name like ?2 ")
	Page<BudgetCommonType> findAllByFiscalYearAndNameLike(Integer fiscalYear, String query, Pageable pageable);

}
