package com.report.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.report.binding.CitizenPlan;
import com.report.binding.SearchRequest;
import com.report.service.ReportService;

@RestController
@CrossOrigin
public class ReportRestController {
	
	@Autowired
	public ReportService reportService;
	
	@GetMapping("/plans/Names")
	public List<String> getPlanNames(){
		return reportService.getPlanNames();
	}
	
	@GetMapping("/plans/Status")
	public List<String> getPlanStatus(){
		return reportService.getPlanStatus();
	}
	
	@GetMapping("/search")
	public List<CitizenPlan> getCitizenPlans(SearchRequest request){
		return reportService.getCitizenPlans(request);
	}
	
	@GetMapping("/excel")
	public void exportExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=report.xls";
		response.setHeader(headerKey, headerValue);
		reportService.exportExcel(response);
	}
	
	@GetMapping("/pdf")
	public void exportPdf(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=report.pdf";
		response.setHeader(headerKey, headerValue);
		reportService.exportPdf(response);
	}

}
