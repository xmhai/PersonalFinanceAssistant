package com.linh.pfa.stock.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// use JpaRepository instead of CrudRepository because findAll() return List<>
@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {

	@Query(value="SELECT * FROM portfolio where (realized_price is null or realized_price = 0) and stock_id = ?1", nativeQuery = true) 
	List<PortfolioEntity> findByStockId(Long stockId);
	
	@Query(value="SELECT * FROM portfolio where realized_price is null or realized_price = 0", nativeQuery = true) 
	List<PortfolioEntity> findActivePortfolio(); 
}