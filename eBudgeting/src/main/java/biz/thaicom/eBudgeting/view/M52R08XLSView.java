package biz.thaicom.eBudgeting.view;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import biz.thaicom.eBudgeting.controllers.ExcelReportsController;
import biz.thaicom.eBudgeting.models.bgt.BudgetType;
import biz.thaicom.eBudgeting.models.pln.Objective;
import biz.thaicom.eBudgeting.models.pln.ObjectiveType;
import biz.thaicom.security.models.ThaicomUserDetail;

public class M52R08XLSView extends AbstractExcelView {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:sss");
	
	private static final Logger logger = LoggerFactory.getLogger(M52R08XLSView.class);
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		java.awt.Font currFont = new java.awt.Font("Tahoma", 0, 14);
		
		ThaicomUserDetail currentUser = (ThaicomUserDetail) model.get("currentUser");

		Map<String, CellStyle> styles = createStyles(workbook);

		ObjectiveType type = (ObjectiveType) model.get("type");
		List<Objective> objectiveList = (List<Objective>) model.get("objectiveList");

		
		Integer fiscalYear = (Integer) model.get("fiscalYear");
		Sheet sheet = workbook.createSheet("sheet1");
		sheet.setColumnWidth(0, 13000);
		sheet.setColumnWidth(1, 13000);

		Row Row11 = sheet.createRow(0);
		Cell cell11 = Row11.createCell(0);
		cell11.setCellValue("(ผู้พิมพ์รายงาน " +   currentUser.getPerson().getFirstName() + " " +	currentUser.getPerson().getLastName() +
				" หน่วยงาน " + currentUser.getPerson().getWorkAt().getName() +
				" เวลาที่จัดทำรายงาน " +  sdf.format(new Date()) + "น.)");

		Row Row21 = sheet.createRow(1);
		Cell cell21 = Row21.createCell(0);
		cell21.setCellValue("ความเชื่อมโยงกับแผนบริหารราชการแผ่นดิน พ.ศ." + fiscalYear);
		cell21.setCellStyle(styles.get("title"));

		Row21.setHeightInPoints(2*sheet.getDefaultRowHeightInPoints());
		
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
		
				
		Row Row31 = sheet.createRow(2);
		Cell cell31 = Row31.createCell(0);
		cell31.setCellValue(type.getParent().getName() );
		cell31.setCellStyle(styles.get("header"));
		Cell cell32 = Row31.createCell(1);
		cell32.setCellValue(type.getName());
		cell32.setCellStyle(styles.get("header"));
		
		Row31.setHeightInPoints(1.5F * sheet.getDefaultRowHeightInPoints());
		
		
		
		int i = 3;
		Long pid = null;
		String gname = null;
		String maxName = null;
		int gnameLength = 0;
		for (Objective o:objectiveList) {
			if (pid != o.getId()) {
				gname = o.getName();
				gnameLength = o.getName().length();
				pid = o.getId();
			}
			
			for (Objective child:o.getChildren()){
				Row rows = sheet.createRow(i);
				Cell cell41 = rows.createCell(0);
				
				if(gname!=null) {
					cell41.setCellValue(o.getName());
					cell41.setCellStyle(styles.get("cell1"));
					
				}
				else {
					cell41.setCellStyle(styles.get("cell1"));
				}

				Cell cell42 = rows.createCell(1);
				cell42.setCellValue(child.getName());
				
				if (gname!=null) {
					cell42.setCellStyle(styles.get("cell1"));
				}
				else {
					cell42.setCellStyle(styles.get("cell1"));
				}
				i++;
				
				maxName = child.getName();
				
				if(gnameLength > child.getName().length()) {
					maxName=gname;
				}
				
				
				AttributedString attrStr = new AttributedString(maxName);
				attrStr.addAttribute(TextAttribute.FONT, currFont);
				
				FontRenderContext frc = new FontRenderContext(null, true, true);
				LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
				int nextPos = 0;
				int lineCnt = 0;
				while (measurer.getPosition() < maxName.length())
				{
				    nextPos = measurer.nextOffset(sheet.getColumnWidth(0)/65); // mergedCellWidth is the max width of each line
				    lineCnt++;
				    measurer.setPosition(nextPos);
				}

				logger.debug("lineCnt: " + lineCnt);
				logger.debug("rows.getHeight(): " + rows.getHeight());
				logger.debug("sheet.getColumnWidth(0): " + sheet.getColumnWidth(0));
				
				rows.setHeight((short)(rows.getHeight() * lineCnt));
				
				gname = null;
				gnameLength=0;
				
				
			}
				
			 
			
		}
		
		Row rowx1 = sheet.createRow(i);
		Cell cellx1 = rowx1.createCell(0);
		cellx1.setCellValue("หมายเหตุ * แต่ละเป้าหมายการให้บริการกระทรวงมีความสัมพันธ์กับเป้าหมายการให้บริการหน่วยงานชัดเจน(ในลักษณะหนึ่งต่อหลาย)");
		//cellx1.setCellStyle(styles.get("cell1"));
		i++;
		Row rowxx1 = sheet.createRow(i);
		Cell cellxx1 = rowxx1.createCell(0);
		cellxx1.setCellValue("       ** กลยุทธ์หน่วยงาน สามารถกำหนดให้มีความสัมพันธ์ซ้ำกันระหว่างเป้าหมายการให้บริการหน่วยงานได้(หลายต่อหลาย)");
		//cellxx1.setCellStyle(styles.get("cell1"));
		
		
		

		
		
		

	}
	
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();

        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)14);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setFontName("Tahoma");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);
        
        Font cell1Font = wb.createFont();
        cell1Font.setFontName("Tahoma");
        cell1Font.setFontHeightInPoints((short) 12); 

        Font monthFont = wb.createFont();
        monthFont.setFontName("Tahoma");
        monthFont.setFontHeightInPoints((short)12);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(cell1Font);
        styles.put("cell1", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell2", style);

        return styles;
    }


}
