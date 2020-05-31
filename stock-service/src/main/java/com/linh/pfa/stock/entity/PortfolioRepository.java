package com.linh.pfa.stock.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// use JpaRepository instead of CrudRepository because findAll() return List<>
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

	@Query(value="SELECT * FROM portfolio where quantity > 0 and stock_id = ?1", nativeQuery = true) 
	List<Portfolio> findByStockId(Long stockId);
	
	@Query("SELECT t FROM Portfolio t where t.quantity > 0") 
	List<Portfolio> findActivePortfolio(); 
}