package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class CategoryService
{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	public Category save(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	public Category findById(Integer id)
	{
		// TODO Auto-generated method stub
		return categoryRepository.findById(id).get();
	}

	public Category update(Category category) {
		// TODO Auto-generated method stub
		Category existCategory = categoryRepository.findById(category.getId()).get();
		
		existCategory.setName(category.getName());
		existCategory.setDescription(category.getDescription());
		
		return categoryRepository.save(existCategory);
	}

	public boolean deleteById(Integer id) {
		// TODO Auto-generated method stub
		if (categoryRepository.existsById(id))
		{
			//productRepository.updateCategoryToNull(id);
			categoryRepository.deleteById(id);
			return true;
		}
		else
		{
			return false;
		}
	}

	public Category findByName(String categoryName) {
	
		Optional<Category> categoryOpt = categoryRepository.findByName(categoryName);
        return categoryOpt.orElse(null); // returns null if not found
	}
	
	

}
