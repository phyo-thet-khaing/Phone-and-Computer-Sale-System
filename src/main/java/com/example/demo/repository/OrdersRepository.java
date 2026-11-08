package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.DailyReportDTO;
import com.example.demo.model.Orders;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository // Optional but helps Spring detect it
public interface OrdersRepository extends JpaRepository<Orders, Integer>
{
	
	Page<Orders> findAll(Pageable pageable); // for pagination

	@Query(value ="SELECT date(order_date) as order_date, sum(total) as total_amount, COUNT(*) as total_count from orders WHERE date(order_date)= :sqlDate GROUP BY date(order_date)",
			nativeQuery=true)
	DailyReportDTO getDailyReport(@Param("sqlDate") Date sqlDate);
	
	
	 @Query(value = """
			    SELECT
			        DATE(order_date) AS orderDate,
			        SUM(total) AS totalAmount,
			        COUNT(*) AS totalCount
			    FROM orders
			    GROUP BY DATE(order_date)
			    ORDER BY DATE(order_date)
			    """, nativeQuery = true)
			List<DailyReportDTO> getDailyReportAllDays();

	
}
