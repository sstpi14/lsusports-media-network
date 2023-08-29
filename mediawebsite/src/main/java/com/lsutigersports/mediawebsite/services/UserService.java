package com.lsutigersports.mediawebsite.services;
import com.lsutigersports.mediawebsite.models.User;
import com.lsutigersports.mediawebsite.models.UserRole;
import com.lsutigersports.mediawebsite.repositories.UserRepo;
import com.lsutigersports.mediawebsite.repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    private UserRepo userRepo;
    private UserRoleRepo userRoleRepo;

    @Autowired
    public UserService(UserRepo userRepo, UserRoleRepo userRoleRepo) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
    }

    public User createOne(User user) {
        try {
            User userByUsername = userRepo.findByUsername(user.getUsername());
            User userByEmail = userRepo.findByEmail(user.getEmail());

            if (userByUsername == null && userByEmail == null) {
                User userFromDb = userRepo.save(user);

                UserRole role = userRoleRepo.findById(userFromDb.getRole().getRoleId()).orElse(null);
                userFromDb.setRole(role);

                return userFromDb;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User validateCredentials(String username, String password) {
        User userFromDb = userRepo.findByUsername(username);

        if (userFromDb == null) {
            return null;
        }

        if (!Objects.equals(password, userFromDb.getPassword())) {
            return null;
        }
        return userFromDb;
    }

    public User updateUser(User user) {
        User userFromDb = userRepo.findById(user.getUserId()).orElse(null);
        if (userFromDb == null) {
            return null;
        }
        return userRepo.save(user);
    }

    public User getOneById(Integer userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User getOneByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
