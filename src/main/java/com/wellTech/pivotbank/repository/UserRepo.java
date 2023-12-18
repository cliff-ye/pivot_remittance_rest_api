package com.wellTech.pivotbank.repository;

import com.wellTech.pivotbank.dto.AccountDetailsDTO;
import com.wellTech.pivotbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);
    User findByAccountNumber(String accountNumber);
    Boolean existsByAccountNumber(String accountNumber);

    Optional<User> findByEmail(String email);
}
