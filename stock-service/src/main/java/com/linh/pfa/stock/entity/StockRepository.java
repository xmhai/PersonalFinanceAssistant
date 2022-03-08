package com.linh.pfa.stock.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// use JpaRepository instead of CrudRepository because findAll() return List<>
@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
}