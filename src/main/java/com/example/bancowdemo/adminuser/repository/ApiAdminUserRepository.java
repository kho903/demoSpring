package com.example.bancowdemo.adminuser.repository;

import com.example.bancowdemo.adminuser.entity.ApiAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiAdminUserRepository extends JpaRepository<ApiAdminUser, Long> {
    Optional<ApiAdminUser> findByEmail(String email);
}
