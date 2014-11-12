package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import biz.thaicom.eBudgeting.models.bgt.ActualBudget;
import biz.thaicom.eBudgeting.models.bgt.BudgetType;
import biz.thaicom.eBudgeting.models.bgt.ReservedBudget;
import biz.thaicom.eBudgeting.models.pln.Objective;

public interface ActualBudgetRepository extends JpaRepository<ActualBudget, Long>,
	JpaSpecificationExecutor<ActualBudget> {

	
	@Query(""
			+ "SELECT actualBudget "
			+ "FROM ActualBudget actualBudget "
			+ "WHERE actualBudget.forObjective.fiscalYear = ?1" )
	List<ActualBudget> findAllByFiscalYear(Integer fiscalYear);
	
	@Query("" +
			"SELECT actualBudget " +
			"FROM ActualBudget actualBudget " +
			"WHERE actualBudget.budgetType = ?1 and actualBudget.forObjective = ?2 ")
	ActualBudget findOneByBudgetTypeAndObjective(BudgetType budgetType,
			Objective forObjective);

	@Query("" +
			"SELECT actualBudget " +
			"FROM ActualBudget actualBudget " +
			"WHERE actualBudget.forObjective.fiscalYear = ?1 AND actualBudget.forObjective.parentPath like ?2 ")
	List<ActualBudget> findAllByFiscalYearAndParentPathLike(Integer fiscalYear,
			String parentPathLikeString);
}
