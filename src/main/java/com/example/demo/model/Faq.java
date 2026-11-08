package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Faq 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String question_name;
	private String  answer;
	
	public Faq() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Faq(Integer id, String question_name, String answer)
	{
		super();
		this.id = id;
		this.question_name = question_name;
		this.answer = answer;
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion_name() 
	{
		return question_name;
	}

	public void setQuestion_name(String question_name) 
	{
		this.question_name = question_name;
	}

	public String getAnswer() 
	{
		return answer;
	}

	public void setAnswer(String answer) 
	{
		this.answer = answer;
	}
	
	
}
