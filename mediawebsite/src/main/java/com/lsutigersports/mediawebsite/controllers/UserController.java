package com.lsutigersports.mediawebsite.controllers;
import com.lsutigersports.mediawebsite.models.User;
import com.lsutigersports.mediawebsite.services.UserService;
import com.lsutigersports.mediawebsite.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public JsonResponse createOne(@RequestBody User user) {
        JsonResponse jsonResponse;

        try {
            User userFromDb = userService.createOne(user);

            if (userFromDb != null) {
                jsonResponse = new JsonResponse(true, "User has been created", userFromDb);
            } else {
                jsonResponse = new JsonResponse(false, "Username or Email already taken", null);
            }
        } catch (Exception e) {
            jsonResponse = new JsonResponse(false, "An Error Has Occurred", null);
        }
        return jsonResponse;
    }

    @PutMapping
    public JsonResponse updateUser (@RequestBody User user) {
        JsonResponse jsonResponse;
        User userFromDb = userService.updateUser(user);
        if (userFromDb == null) {
            jsonResponse = new JsonResponse(false, "User could not be updated", null);
        } else {
            jsonResponse = new JsonResponse(true, "User has been successfully updated", userFromDb);
        }
        return jsonResponse;
    }

    @GetMapping("id/{userId}")
    public JsonResponse getOneById(@PathVariable Integer userId) {
        JsonResponse jsonResponse;
        User userFromDb = userService.getOneById(userId);
        if (userFromDb == null) {
            jsonResponse = new JsonResponse(false, "Invalid userId", null);
        } else {
            jsonResponse = new JsonResponse(true, "User has been successfully found", userFromDb);
        }
        return jsonResponse;
    }

    @GetMapping("username/{username}")
    public JsonResponse getOneByUsername(@PathVariable String username){
        JsonResponse jsonResponse;
        User userFromDb = userService.getOneByUsername(username);
        if (userFromDb == null) {
            jsonResponse = new JsonResponse(false, "Invalid username", null);
        } else {
            jsonResponse = new JsonResponse(true, "User has been successfully found", userFromDb);
        }
        return jsonResponse;
    }

    @GetMapping
    public JsonResponse getAllUsers(){
        JsonResponse jsonResponse;
        List<User> usersFromDb = userService.getAllUsers();
        Collections.sort(usersFromDb, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUserId() - o2.getUserId();
            }
        });
        if (usersFromDb.isEmpty()) {
            jsonResponse = new JsonResponse(false, "No user in the database", null);
        } else {
            jsonResponse = new JsonResponse(true, "Found all users available in the database", usersFromDb);
        }
        return jsonResponse;
    }

    @GetMapping("validate/{username}")
    public JsonResponse validateCredentials(@PathVariable String username, @RequestBody String password) {
        JsonResponse jsonResponse;
        User userFromDb = userService.getOneByUsername(username);
        if (userFromDb == null) {
            jsonResponse = new JsonResponse(false, "User with username: " + username + " does not exist.", null);
        } else {
            String passwordFromDb = userFromDb.getPassword();
            if (Objects.equals(passwordFromDb, password)) {
                jsonResponse = new JsonResponse(true, "Credentials Validated", userFromDb);
            } else {
                jsonResponse = new JsonResponse(false, "Password incorrect", null);
            }
        }
        return jsonResponse;
    }
}
