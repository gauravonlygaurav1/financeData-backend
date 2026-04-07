package com.example.financeData.controller;

import com.example.financeData.dto.LoginRequest;
import com.example.financeData.entities.User;
import com.example.financeData.exceptions.ResourceNotFoundException;
import com.example.financeData.exceptions.UnauthorizedException;
import com.example.financeData.repository.UserRepository;
import com.example.financeData.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Invalid password");
        }
        String token= jwtService.generateToken(user.getId(), user.getRole().name());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request){

        Long userId= (Long)request.getAttribute("userId");

        User user= userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException(("User not found")));

        String role= user.getRole().name();

        if( !role.equals("ADMIN")){
            throw new UnauthorizedException("Access denied: Admin only");
        }
        return ResponseEntity.ok(userRepository.findAll());
    }
}
