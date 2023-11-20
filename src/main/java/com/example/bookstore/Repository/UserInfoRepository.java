package com.example.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bookstore.Entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);
}

