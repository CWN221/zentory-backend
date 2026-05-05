package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
}
