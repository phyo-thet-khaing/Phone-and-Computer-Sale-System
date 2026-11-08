package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService 
{

	
	@Autowired
	private AdminRepository adminRepository;
	
	public  Admin findByEmailAndPassword(String email, String password)
	{
		// TODO Auto-generated method stub
		return adminRepository.findByEmailAndPassword(email,password);
	}
	
	
	public Admin save(Admin admin)
	{
		return adminRepository.save(admin);	
	}




	




	

}
