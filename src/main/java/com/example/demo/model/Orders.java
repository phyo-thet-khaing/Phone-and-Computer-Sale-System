package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Orders 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@DateTimeFormat
	private Date order_date;
	private Double total;
	private String status;
	private String address;
	
	@ManyToOne
	private Customer customer;
	
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetails> orderDetails;

	public List<OrderDetails> getOrderDetails() 
	{
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails)
	{
		this.orderDetails = orderDetails;
	}

	public Orders()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Orders(Integer id, Date order_date, Double total, String status, String address, Customer customer,
			List<OrderDetails> orderDetails) 
	{
		super();
		this.id = id;
		this.order_date = order_date;
		this.total = total;
		this.status = status;
		this.address = address;
		this.customer = customer;
		this.orderDetails = orderDetails;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
		
	}

	public Date getOrder_date() 
	{
		return order_date;
	}

	public void setOrder_date(Date order_date) 
	{
		this.order_date = order_date;
	}

	

	public Double getTotal() 
	{
		return total;
	}

	public void setTotal(Double total) 
	{
		this.total = total;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public Customer getCustomer() 
	{
		return customer;
	}

	public void setCustomer(Customer customer) 
	{
		this.customer = customer;
	}
	
	
	
}
