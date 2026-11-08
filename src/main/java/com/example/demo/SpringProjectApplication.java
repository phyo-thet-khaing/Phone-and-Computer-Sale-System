package com.example.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.controller.AdminController;

@SpringBootApplication
public class SpringProjectApplication {

	public static void main(String[] args) 
	{
		new File(AdminController.uploadDirectory).mkdir();
		SpringApplication.run(SpringProjectApplication.class, args);
	}

}
