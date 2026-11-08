package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.DailyReportDTO;
import com.example.demo.service.ReportService;

import org.springframework.ui.Model;

@Controller
public class ReportController 
{
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/admin/report")
	public String report()
	{
		return "admin/report";
	}
	
	@GetMapping("/admin/report/daily")
	public String dailyReport(Model model)
	{
		List<DailyReportDTO> allDailyReports = reportService.getDailyReportAllDays();
	    model.addAttribute("allDailyReports", allDailyReports);
		return "admin/report-daily";
	}
	
}
