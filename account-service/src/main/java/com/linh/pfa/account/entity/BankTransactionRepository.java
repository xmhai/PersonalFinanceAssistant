package com.linh.pfa.account.entity;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    public Optional<BankTransaction> findByTransactionDateAndDescription(LocalDate transactionDate, String description);
}