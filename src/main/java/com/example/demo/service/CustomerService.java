package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService 
{
	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	public boolean deleteById(Integer id) {
		// TODO Auto-generated method stub
		if (customerRepository.existsById(id))
		{
		
			customerRepository.deleteById(id);
			return true;
		}
		else
		{
			return false;
		}
	}

	public Customer findByEmail(String email) {
		// TODO Auto-generated method stub
		return customerRepository.findByEmail(email);
	}

	public Customer save(Customer customer) 
	{
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}
	
	

}
