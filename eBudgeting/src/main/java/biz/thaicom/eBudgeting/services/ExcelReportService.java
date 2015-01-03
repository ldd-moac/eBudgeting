package biz.thaicom.eBudgeting.services;

import java.util.List;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface ExcelReportService {
	public List<String[]> getRowsForM51R16(Integer fiscalYear);
}
