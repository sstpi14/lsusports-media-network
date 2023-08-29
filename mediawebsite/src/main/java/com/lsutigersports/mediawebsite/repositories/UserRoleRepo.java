package com.lsutigersports.mediawebsite.repositories;

import com.lsutigersports.mediawebsite.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
}
