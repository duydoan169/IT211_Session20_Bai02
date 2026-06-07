package org.example.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.bai2.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}