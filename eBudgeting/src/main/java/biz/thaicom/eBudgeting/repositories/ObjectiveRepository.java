package biz.thaicom.eBudgeting.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import biz.thaicom.eBudgeting.models.pln.Objective;
import biz.thaicom.eBudgeting.models.pln.ObjectiveName;
import biz.thaicom.eBudgeting.models.pln.ObjectiveType;

public interface ObjectiveRepository extends PagingAndSortingRepository<Objective, Long>, JpaSpecificationExecutor<Objective>{
	public List<Objective> findByTypeId(Long id);

	public List<Objective> findByParentIdAndFiscalYearAndParent_Name(Long id, Integer fiscalYear, String parentName);

	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE objective.name = 'ROOT' "
			+ " ORDER BY objective.fiscalYear asc " +
			"")
	public List<Objective> findRootFiscalYear();
	
	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE objective.name='ROOT' and fiscalYear = :fiscalYear " +
			"")
	public Objective findRootOfFiscalYear(
			@Param("fiscalYear") Integer fiscalYear);
	
	
	
//	@Query("" +  
//			"SELECT objective, proposal " +
//			"FROM BudgetProposal proposal " +
//			"	RIGHT OUTER JOIN proposal.owner owner with owner.id = ?2 " +
//			"	RIGHT OUTER JOIN proposal.forObjective objective with objective.fiscalYear = ?1 " +
//			"WHERE " +
//			"	" +
//			"	 objective.parent.id = ?3 ")
	@Query("" +  
			"SELECT objective " +
			"FROM Objective objective " +
			"	LEFT OUTER JOIN objective.proposals proposal with proposal.owner.id = :ownerId " +
			"WHERE objective.parent.id = :objectiveId and objective.fiscalYear = :fiscalYear " +
			"ORDER BY objective.id asc ")
	public List<Objective> findByObjectiveBudgetProposal(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("ownerId") Long ownerId, 
			@Param("objectiveId") Long objectiveId);

	
	
	@Query("" +  
			"SELECT distinct objective " +
			"FROM Objective objective" +
			"	INNER JOIN FETCH objective.parent parent " +
			"	INNER JOIN FETCH objective.type type " +
			"	LEFT OUTER JOIN FETCH objective.budgetTypes budgetTypes " +
			"	LEFT OUTER JOIN objective.proposals proposal with proposal.owner.id = :ownerId " +
			"WHERE objective.fiscalYear = :fiscalYear AND (objective.parentPath like :parentPathLikeString OR objective.parentPath is null) " +
			"ORDER BY objective.id asc ")
	public List<Objective> findFlatByObjectiveBudgetProposal(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("ownerId") Long ownerId,
			@Param("parentPathLikeString") String parentPathLikeString);
	
	
	@Query("" +  
			"SELECT distinct objective " +
			"FROM Objective objective" +
			"	INNER JOIN FETCH objective.parent parent " +
			"	INNER JOIN FETCH objective.type type " +
			"	LEFT OUTER JOIN FETCH objective.budgetTypes budgetTypes " +
			"	LEFT OUTER JOIN objective.objectiveProposals proposal with proposal.owner.id = :ownerId " +
			"WHERE objective.fiscalYear = :fiscalYear AND (objective.parentPath like :parentPathLikeString OR objective.parentPath is null) " +
			"ORDER BY objective.id asc ")	
	public List<Objective> findFlatByObjectiveObjectiveBudgetProposal(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("ownerId") Long ownerId,  
			@Param("parentPathLikeString") String parentPathLikeString);
	
	
	@Query("" +  
			"SELECT distinct objective " +
			"FROM Objective objective" +
			"	LEFT OUTER JOIN FETCH objective.parent parent " +
			"	LEFT OUTER JOIN FETCH objective.children" +
			"	INNER JOIN FETCH objective.type type " +
			"	LEFT OUTER JOIN objective.budgetTypes budgetTypes " +
			"	LEFT OUTER JOIN objective.proposals proposal " +
			"WHERE objective.fiscalYear = :fiscalYear "
			+ "	AND (objective.parentPath like :parentPathLikeString OR objective.parentPath is null) " +
			"ORDER BY objective.id asc ")
	public List<Objective> findFlatByObjectiveBudgetProposal(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("parentPathLikeString") String parentPathLikeString);
	
	@Query("" +  
			"SELECT distinct objective " +
			"FROM Objective objective" +
			"	LEFT OUTER JOIN FETCH objective.parent parent " +
			"	LEFT OUTER JOIN FETCH objective.children" +
			"	INNER JOIN FETCH objective.type type " +
			"	LEFT OUTER JOIN objective.budgetTypes budgetTypes " +
			"	LEFT OUTER JOIN objective.objectiveProposals proposal " +
			"WHERE objective.fiscalYear = :fiscalYear"
			+ "	 AND (objective.parentPath like :parentPathLikeString OR objective.parentPath is null) " +
			"ORDER BY objective.id asc ")
	public List<Objective> findFlatByObjectiveObjBudgetProposal(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("parentPathLikeString") String parentPathLikeString);
	
	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE objective.parentPath like :parentPathLikeString")
	public List<Objective> findAllDescendantOf(
			@Param("parentPathLikeString") String parentPathLikeString);
	
	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE objective.id in ( :ids ) " +
			"ORDER BY objective.parentPath DESC ")
	public List<Objective> findAllObjectiveByIds(
			@Param("ids") List<Long> ids);
	
	
	@Query("" +  
			"SELECT objective " +
			"FROM Objective objective" +
			"	INNER JOIN FETCH objective.type type " +
			"	LEFT OUTER JOIN FETCH objective.budgetTypes budgetTypes " +
			"WHERE objective.parent.id = :id  " +
			"ORDER BY objective.id asc ")
	public List<Objective> findChildrenWithParentAndTypeAndBudgetType(
			@Param("id") Long id);

	@Modifying
	@Query("update Objective objective " +
			"set index = index-1 " +
			"where index > :deleteIndex and objective.parent = :parent ")
	public int reIndex(
			@Param("deleteIndex") Integer deleteIndex,
			@Param("parent") Objective parent);

	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"	INNER JOIN FETCH objective.type type " +
			"	LEFT OUTER JOIN FETCH objective.parent parent " +
			"	LEFT OUTER JOIN FETCH objective.units unit " +
			"WHERE objective.fiscalYear= :fiscalYear " +
			"	AND objective.type.id= :typeId " +
			"ORDER BY objective.id asc ")
	public List<Objective> findAllByFiscalYearAndType_id(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("typeId") Long typeId);
	
	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE objective.fiscalYear= :fiscalYear " +
			"	AND objective.type.id= :typeId " )
	public Page<Objective> findPageByFiscalYearAndType_id(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("typeId") Long typeId, 
			Pageable pageable);
	
	@Query("" +
			"SELECT max(o.code) " +
			"FROM Objective o " +
			"WHERE o.type= :type AND o.fiscalYear= :fiscalYear ")
	public String findMaxCodeOfTypeAndFiscalYear(
			@Param("type") ObjectiveType type,
			@Param("fiscalYear") Integer fiscalYear);
	
	@Query("" +
			"SELECT max(o.lineNumber) " +
			"FROM Objective o " +
			"WHERE o.fiscalYear= :fiscalYear ")
	public Integer findMaxLineNumberFiscalYear(@Param("fiscalYear") Integer fiscalYear);
	
	@Modifying
	@Query("update Objective objective " +
			"set lineNumber = lineNumber + :amount  " +
			"where fiscalYear = :fiscalYear AND lineNumber >= :lineNumber ")
	public Integer insertFiscalyearLineNumberAt(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("lineNumber") Integer lineNumer,
			@Param("amount") Integer amount);

	@Modifying
	@Query("update Objective objective " +
			"set lineNumber = lineNumber - :amount  " +
			"where fiscalYear =:fiscalYear AND lineNumber > :lineNumber ")
	public Integer removeFiscalyearLineNumberAt(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("lineNumber") Integer lineNumer,
			@Param("amount") Integer amount);

	
	@Query("" +
			"SELECT max(o.lineNumber) " +
			"FROM Objective o " +
			"WHERE o.parent = :parent  ")
	public Integer findMaxLineNumberChildrenOf(@Param("parent") Objective parent);
	
	
	
	public List<Objective> findAllByFiscalYearAndParentPathLike(Integer fiscalYear, String parentPath);

	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE objective.type in ( :childrenSet ) and objective.parent is null ")
	public List<Objective> findAvailableChildrenOfObjectiveType(
			@Param("childrenSet") Set<ObjectiveType> childrenSet);

	public Objective findOneByFiscalYearAndName(Integer fiscalYear,
			String string);

	public List<Objective> findAllByObjectiveName(ObjectiveName on);

	
	@Query("" +
			"SELECT objective " +
			"FROM Objective objective" +
			"	INNER JOIN FETCH objective.proposals proposal " +
			"	INNER JOIN FETCH proposal.owner owner " +
			"	INNER JOIN FETCH proposal.budgetType budgetType " +
			"WHERE objective.fiscalYear = :fiscalYear AND objective.type.id = :typeId ")
	public List<Objective> findAllByTypeIdAndFiscalYearInitBudgetProposal(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("typeId") long typeId);


	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"WHERE fiscalYear = :fiscalYear and type.id = :typeId and " +
			"	(name like :query or code like :query) ")
	public Page<Objective> findByFiscalYearAndType_Id(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("typeId") Long typeId,
			@Param("query") String query, Pageable pageable);

	
	@Query("" +
			"SELECT objective " +
			"FROM Objective objective " +
			"	LEFT JOIN FETCH objective.children " +
			"	LEFT JOIN FETCH objective.parent ")
	public Iterable<Objective> findAllFetchChildrenParent();

}
