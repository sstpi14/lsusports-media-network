package com.lsutigersports.mediawebsite.repositories;

import com.lsutigersports.mediawebsite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
