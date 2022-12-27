package com.report.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;
import com.report.binding.CitizenPlan;
import com.report.binding.SearchRequest;

public interface ReportService {
	
	public List<String> getPlanNames();
	
	public List<String> getPlanStatus();
	
	public List<CitizenPlan> getCitizenPlans(SearchRequest request);
	
	public void exportExcel(HttpServletResponse response) throws Exception;
	
	public void exportPdf(HttpServletResponse response) throws DocumentException, IOException;
}
