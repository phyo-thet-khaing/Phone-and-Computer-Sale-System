package com.example.demo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    private String productName;
	private String description;
	private String quantity;
	private	Double price;
	private String image;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date  date;
	
	@ManyToOne
	private Category category;
	
	public Product() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Integer id, String productName, String description, String quantity, Double price, String image,
			Date date, Category category)
	{
		super();
		this.id = id;
		this.productName = productName;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.image = image;
		this.date = date;
		this.category = category;
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}



	public String getProductName() 
	{
		return productName;
	}

	public void setProductName(String productName) 
	{
		this.productName = productName;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(String quantity) 
	{
		this.quantity = quantity;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public String getImage() 
	{
		return image;
	}

	public void setImage(String image) 
	{
		this.image = image;
	}

	public Date getDate() 
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Category getCategory() 
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}	
	
}
