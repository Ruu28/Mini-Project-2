package com.report.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.report.binding.CitizenPlan;
import com.report.binding.SearchRequest;
import com.report.repository.CitizenPlanRepository;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private CitizenPlanRepository cpr;

	@Override
	public List<String> getPlanNames() {
		return cpr.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return cpr.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> getCitizenPlans(SearchRequest request) {
		CitizenPlan entity = new CitizenPlan();
		
		//TO TEST ALL 4 scenarios --> 1)PlanName Selected 2)PlanStatus Selected 3)Both Selected 4)Nothing Selected
//		request.setPlanName("Basic");
		
		if(request.getPlanName()!=null && !request.getPlanName().isEmpty()) {
			entity.setPlanName(request.getPlanName());
		}
		if(request.getPlanStatus()!=null && !request.getPlanStatus().isEmpty()) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		
		Example<CitizenPlan> example = Example.of(entity); //BASED ON DATA IN ENTITY THE QUERY IS CREATED DYNAMICALLY
		
		return cpr.findAll(example);
		
//		if (entity.getPlanName() == null || entity.getPlanName().isEmpty()) {
//			if (entity.getPlanStatus() != null || !entity.getPlanStatus().isEmpty()) {
//				cpr.findByPlanStatus(entity.getPlanStatus());
//			} else {
//				cpr.findAll();
//			}
//		}else 
//		return null;
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws Exception {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("CITIZENS_INFO");
		XSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Id");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("PhNo");
		headerRow.createCell(5).setCellValue("SSN");
		headerRow.createCell(6).setCellValue("PlanName");
		headerRow.createCell(7).setCellValue("PlanStatus");
		
		List<CitizenPlan> records = cpr.findAll();
		int dataRowIndex=1;
		
		for(CitizenPlan record: records) {
			XSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(record.getCid());
			dataRow.createCell(1).setCellValue(record.getName());
			dataRow.createCell(2).setCellValue(record.getEmail());
			dataRow.createCell(3).setCellValue(record.getGender());
			dataRow.createCell(4).setCellValue(record.getPhno());
			dataRow.createCell(5).setCellValue(record.getSsn());
			dataRow.createCell(6).setCellValue(record.getPlanName());
			dataRow.createCell(7).setCellValue(record.getPlanStatus());
			dataRowIndex++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	@Override
	public void exportPdf(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font f = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        f.setSize(18);
        f.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("CITIZEN PLANS INFO", f);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.3f, 3.5f, 4.0f, 2.2f, 2.5f, 1.5f, 1.5f, 1.5f});
        table.setSpacingBefore(10);
        
        //Set Table Header Data
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Phno", font));
        table.addCell(cell); 
        
        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("PlanName", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("PlanStatus", font));
        table.addCell(cell);
        
        //Set table Data
        List<CitizenPlan> records = cpr.findAll();
        
        for(CitizenPlan record: records) {
        	table.addCell(String.valueOf(record.getCid()));
        	table.addCell(record.getName());
        	table.addCell(record.getEmail());
        	table.addCell(record.getGender());
        	table.addCell(String.valueOf(record.getPhno()));
        	table.addCell(String.valueOf(record.getSsn()));
        	table.addCell(record.getPlanName());
        	table.addCell(record.getPlanStatus());        	
        }
        
        document.add(table);
        document.close();
	}

}
