package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Orders;
import com.example.demo.repository.OrdersRepository;



@Service
public class OrdersService 
{
	@Autowired
	private OrdersRepository ordersRepository;
	
	
	
	public Page<Orders> findPaginated(int pageNo, int pageSize) 
	{
		// TODO Auto-generated method stub
		 Pageable pageable = PageRequest.of(pageNo- 1, pageSize);
	        return ordersRepository.findAll(pageable);
	}



	public Orders findById(Integer id)
	{
		// TODO Auto-generated method stub
		
				return ordersRepository.findById(id).get();
	}



	public Orders save(Orders order)
	{
		// TODO Auto-generated method stub
		return ordersRepository.save(order);
	}



	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		return ordersRepository.findAll();
	}

}
