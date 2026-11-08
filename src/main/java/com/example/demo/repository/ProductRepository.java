package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>
{
	@Query("""
	        SELECT p FROM Product p
	        WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))
	           OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
	           OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
	    """)
	Page<Product> search(String keyword, Pageable pageable);


}
