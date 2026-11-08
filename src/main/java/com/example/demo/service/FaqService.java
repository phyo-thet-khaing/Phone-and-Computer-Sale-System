package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Faq;
import com.example.demo.repository.FaqRepository;

@Service
public class FaqService
{
   
	@Autowired
	private FaqRepository faqRepository;
	
	public List<Faq> findAll()
	{
		// TODO Auto-generated method stub
		return faqRepository.findAll();
	}

	public Faq save(Faq faq) 
	{
		// TODO Auto-generated method stub
		return faqRepository.save(faq);
	}

	public Faq findById(Integer id) {
		// TODO Auto-generated method stub
		return faqRepository.findById(id).get();
	}

	public Faq updateFaq(Faq faq) 
	{
	    Faq existFaq = faqRepository.findById(faq.getId())
	            .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + faq.getId()));

	    existFaq.setQuestion_name(faq.getQuestion_name());
	    existFaq.setAnswer(faq.getAnswer());

	    return faqRepository.save(existFaq);
	}

	public boolean deleteById(Integer id) 
	{
	    if (faqRepository.existsById(id)) 
	    {
	        faqRepository.deleteById(id);
	        return true;
	    }
	    return false;
	}



	

}
