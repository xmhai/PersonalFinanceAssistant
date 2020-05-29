package com.linh.pfa.account.entity;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// use JpaRepository instead of CrudRepository because findAll() return List<>
@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
	@Modifying
    @Query(value = "delete from asset_history where record_date = ?1", nativeQuery = true)
    void deleteByRecordDate(LocalDate d);
}