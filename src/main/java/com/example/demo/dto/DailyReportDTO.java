package com.example.demo.dto;

import java.sql.Date;

public class DailyReportDTO 
{
	
	  private Date orderDate;
	  private Double totalAmount;
	  private Long totalCount;
	  
	  public DailyReportDTO(Date orderDate, Double totalAmount, Long totalCount) 
	  {
	  	super();
	  	this.orderDate = orderDate;
	  	this.totalAmount = totalAmount;
	  	this.totalCount = totalCount;
	  }

	  public DailyReportDTO() 
	  {
	  	super();
	  	// TODO Auto-generated constructor stub
	  }

	  public Date getOrderDate()
	  {
	  	return orderDate;
	  }
	  public void setOrderDate(Date orderDate) 
	  {
	  	this.orderDate = orderDate;
	  }
	  public Double getTotalAmount() 
	  {
	  	return totalAmount;
	  }
	  public void setTotalAmount(Double totalAmount)
	  {
	  	this.totalAmount = totalAmount;
	  }
	  public Long getTotalCount() 
	  {
	  	return totalCount;
	  }
	  public void setTotalCount(Long totalCount) 
	  {
	  	this.totalCount = totalCount;
	  }
}
