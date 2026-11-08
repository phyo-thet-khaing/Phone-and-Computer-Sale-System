package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DailyReportDTO;
import com.example.demo.repository.OrdersRepository;

@Service
public class ReportService 
{
	@Autowired
	private OrdersRepository ordersRepository;
	
	public DailyReportDTO getDailyReport(Date sqlDate) 
	{
		// TODO Auto-generated method stub
		return ordersRepository.getDailyReport(sqlDate);
	}

	public List<DailyReportDTO> getDailyReportAllDays() 
	{
		// TODO Auto-generated method stub
		return ordersRepository.getDailyReportAllDays();
	}
	
}
