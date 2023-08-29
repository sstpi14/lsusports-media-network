package com.lsutigersports.mediawebsite.controllers;

import com.lsutigersports.mediawebsite.models.User;
import com.lsutigersports.mediawebsite.services.UserService;
import com.lsutigersports.mediawebsite.utils.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("session")
public class SessionController {
    private UserService userService;

    @Autowired
    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public JsonResponse loginSession (HttpSession httpSession, @RequestBody User userLoggingIn) {
        User retriveUser = userService.validateCredentials(userLoggingIn.getUsername(),userLoggingIn.getPassword());

        if (retriveUser == null) {
            return new JsonResponse(false, "Invalid Username or Password", null);
        } else {
          httpSession.setAttribute("user", retriveUser);
          return new JsonResponse(true, "Login Successful", retriveUser);
        }
    }

    @DeleteMapping
    public JsonResponse logoutSession (HttpSession httpSession) {
        httpSession.invalidate();
        return new JsonResponse(true, "Successfully logged out and session invalidated", null);
    }

    @GetMapping JsonResponse checkSession (HttpSession httpSession) {
        User userLoggedIn = (User) httpSession.getAttribute("user");

        if (userLoggedIn == null) {
            return new JsonResponse(false, "No session found", null);
        } else {
            return new JsonResponse(true, "Session found", userLoggedIn);
        }
    }
}
