package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;



@Service
public class ProductService
{
	
	@Autowired
	private  ProductRepository productRepository;

	public List<Product> findAll() 
	{
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	public Product save(Product product) 
	{
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	public Product findById(Integer id) 
	{
		// TODO Auto-generated method stub
		return productRepository.findById(id).get();
	}

	public Product update(Product product) 
	{
		// TODO Auto-generated method stub
		Product existProduct = productRepository.findById(product.getId()).get();
		
		existProduct.setProductName(product.getProductName());
		existProduct.setDescription(product.getDescription());
		existProduct.setQuantity(product.getQuantity());
		existProduct.setPrice(product.getPrice());
		existProduct.setImage(product.getImage());
		existProduct.setCategory(product.getCategory());
		
		return productRepository.save(existProduct);
	}

	public boolean deleteById(Integer id) 
	{
		// TODO Auto-generated method stub
		if (productRepository.existsById(id))
		{
			productRepository.deleteById(id);
			return true;
		}
		
		else
		{
			return false;
		}
	}

	
	public Page<Product> findPaginated(int page, int pageSize) 
	{
		// TODO Auto-generated method stub
		   
		Sort sort = Sort.by(Sort.Direction.ASC, "productName");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return productRepository.findAll(pageable);
	}

	public Page<Product> searchPaginated(String keyword, Pageable pageable)
	  {
	        if (keyword == null || keyword.isEmpty()) 
	        {
	            return productRepository.findAll(pageable);
	        }
	      //  return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
	        return productRepository.search(keyword, pageable);
	    }
	
	
	public Page<Product> findPaginated(Pageable pageable) 
	{
	        return productRepository.findAll(pageable);
	 }
	




	

}
