package com.example.mobilehomesbooking.controller;

import com.example.mobilehomesbooking.model.User;
import com.example.mobilehomesbooking.repository.UserRepository;
import com.example.mobilehomesbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        System.out.println("Primljen user: " + user.getEmail() + ", pass: " + user.getPassword());
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        User loggedUser = userService.login(email, password);
        return ResponseEntity.ok(loggedUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
