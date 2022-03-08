package com.linh.pfa.stock.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// use JpaRepository instead of CrudRepository because findAll() return List<>
@Repository
public interface ProfitRepository extends JpaRepository<ProfitEntity, Long> {

	List<ProfitEntity> findByStockId(Long stockId);
}