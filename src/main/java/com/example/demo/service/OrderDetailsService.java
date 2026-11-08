package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderDetails;
import com.example.demo.repository.OrderDetailsRepository;



@Service
public class OrderDetailsService 
{
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	public List<OrderDetails> getOrderDetails(Integer orderId) {
		// TODO Auto-generated method stub
		return  orderDetailsRepository.findByOrderId(orderId);
	}

	public void save(OrderDetails orderDetail) 
	{
		// TODO Auto-generated method stub
		orderDetailsRepository.save(orderDetail);
	}

	

	

	

}
