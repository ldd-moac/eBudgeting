package biz.thaicom.eBudgeting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.thaicom.eBudgeting.models.bgt.AllocationRecord;
import biz.thaicom.eBudgeting.models.bgt.AllocationRecordStrategy;
import biz.thaicom.eBudgeting.models.bgt.FormulaStrategy;

public interface AllocationRecordStrategyRepository extends
		JpaRepository<AllocationRecordStrategy, Long> {

	@Query("" +
			"SELECT strategy " +
			"FROM AllocationRecordStrategy strategy " +
			"WHERE strategy.allocationRecord.index = ?1 " +
			"	and strategy.strategy.fiscalYear = ?2 ")
	List<AllocationRecordStrategy> findAllByIndexAndFiscalYear(Integer index,
			Integer fiscalYear);

	List<AllocationRecordStrategy> findAllByStrategy(FormulaStrategy fs);

	AllocationRecordStrategy findOneByAllocationRecordAndStrategy(
			AllocationRecord ar, FormulaStrategy fs);

	@Query(""
			+ "SELECT sum(strategy.totalCalculatedAmount) "
			+ "FROM AllocationRecordStrategy strategy "
			+ "WHERE allocationRecord = ?1 ")
	Long findSumTotalCalculatedAmount(AllocationRecord record);

}
