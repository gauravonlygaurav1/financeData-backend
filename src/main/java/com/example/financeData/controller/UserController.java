package com.example.financeData.controller;

import com.example.financeData.dto.LoginRequest;
import com.example.financeData.entities.User;
import com.example.financeData.exceptions.ResourceNotFoundException;
import com.example.financeData.exceptions.UnauthorizedException;
import com.example.financeData.repository.UserRepository;
import com.example.financeData.services.JwtService;
import lombok.RequiredArgsConstructor;
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
    public User createUser(@RequestBody User user){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Invalid password");
        }
        return jwtService.generateToken(user.getId(), user.getRole().name());
    }

    @GetMapping("/list")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
