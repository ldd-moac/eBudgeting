package biz.thaicom.eBudgeting.services;

import java.util.ArrayList;
import java.util.List;

import net.bull.javamelody.MonitoredWithSpring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biz.thaicom.eBudgeting.models.pln.Objective;
import biz.thaicom.eBudgeting.models.pln.ObjectiveTypeId;
import biz.thaicom.eBudgeting.repositories.ObjectiveRelationsRepository;
import biz.thaicom.eBudgeting.repositories.ObjectiveRepository;

@Service
@Transactional
@MonitoredWithSpring
public class ExcelReportServiceJPA implements ExcelReportService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReportServiceJPA.class);
	
	@Autowired
	private ObjectiveRepository objectiveRepository;
	
	@Autowired
	private ObjectiveRelationsRepository objectiveRelationsRepository;
	
	@Override
	public List<String[]> getRowsForM51R16(Integer fiscalYear) {
		List<Objective> objs = objectiveRepository.findAllByFiscalYearAndType_id(fiscalYear, 103L);
		
		List<String[]> returnRows = new ArrayList<String[]>();
		
		for(Objective obj : objs) {
			String[] row = new String[7];
			
			row[0] = "<" + obj.getCode() + ">" + obj.getName();
			row[1] = "<" + obj.getParent().getCode() + ">" + obj.getParent().getName();
			
			Objective เป้าหมายบริการหน่วยงาน = objectiveRelationsRepository.findParentByObjectiveAndParentType(obj.getParent(), ObjectiveTypeId.เป้าหมายบริการหน่วยงาน.getValue());
			
			if(เป้าหมายบริการหน่วยงาน != null) {
			
				row[2] = "<" + เป้าหมายบริการหน่วยงาน.getCode() + ">" + เป้าหมายบริการหน่วยงาน.getName();
				row[3] = "<" + เป้าหมายบริการหน่วยงาน.getParent().getCode() + ">" + เป้าหมายบริการหน่วยงาน.getParent().getName();
			}
			
			Objective แนวทางการจัดสรรงบประมาณ = objectiveRelationsRepository.findParentByObjectiveAndParentType(
					obj, ObjectiveTypeId.แนวทางการจัดสรรงบประมาณ.getValue());
			
			if(แนวทางการจัดสรรงบประมาณ != null) {
				row[6] = "<" + แนวทางการจัดสรรงบประมาณ.getCode() + ">" + แนวทางการจัดสรรงบประมาณ.getName();
			}
			
			
			row[5] = "<" + obj.getParent().getParent().getCode() + ">" + obj.getParent().getParent().getName();
			
			
			returnRows.add(row);
		}
		
		return returnRows;
	}

}
