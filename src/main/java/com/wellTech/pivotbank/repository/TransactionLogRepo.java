package com.wellTech.pivotbank.repository;

import com.wellTech.pivotbank.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepo extends JpaRepository<TransactionLog,String> {


}
