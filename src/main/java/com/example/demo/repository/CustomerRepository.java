package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{

	 Customer findByEmailAndPassword(String email, String password);
	 
	 Customer findByEmail(String email);
	
}
