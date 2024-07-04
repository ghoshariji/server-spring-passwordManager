package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.dbinheritence.UserRepository;
import com.server.server.model.UserModel;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel post) {
        try {
            if (userRepository.existsByEmail(post.getEmail())) {
                return ResponseEntity.badRequest().body("User Already Exists");
            }
            if (post.getEmail().isEmpty() || post.getName().isEmpty() || post.getPassword().isEmpty()
                    || post.getPhone().isEmpty()) {
                return ResponseEntity.badRequest().body("All fields are required");
            }
            UserModel user = new UserModel();
            user.setName(post.getName());
            user.setEmail(post.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(post.getPassword()));
            user.setPhone(post.getPhone());
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            System.out.println("Error" + e);
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel post) {
        try {
            if (!userRepository.existsByEmail(post.getEmail())) {
                return ResponseEntity.badRequest().body("Email doesn't exist");
            }
            UserModel data = userRepository.findByEmail(post.getEmail());
            if (!bCryptPasswordEncoder.matches(post.getPassword(), data.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid Password");
            }
            return ResponseEntity.ok("Login Successfully");
        } catch (Exception e) {
            System.out.println("Error" + e);
            return ResponseEntity.badRequest().body("Something went wrong");
        }

    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserModel post, @RequestParam String Id) {
        try {
            if (!userRepository.existsByEmail(post.getEmail())) {
                return ResponseEntity.badRequest().body("User Don't Exist");
            }
            UserModel user = userRepository.findByEmail(post.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(post.getPassword()));
            userRepository.save(user);
            return ResponseEntity.badRequest().body("Password Changed Successfully");
        } catch (Exception e) {
            System.out.println("Error" + e);
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

}
