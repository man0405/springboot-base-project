package com.manportfolio.base_project.Controllers;

import com.manportfolio.base_project.DTO.Auth.JwtResponse;
import com.manportfolio.base_project.DTO.Auth.LoginRequest;
import com.manportfolio.base_project.Enum.AuthProvider;
import com.manportfolio.base_project.Models.User;
import com.manportfolio.base_project.Repository.Interface.UserRepository;
import com.manportfolio.base_project.Services.Implement.CustomUserDetailsService;
import com.manportfolio.base_project.Services.Implement.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final JwtService jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;


    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (customUserDetailsService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        customUserDetailsService.save(user);

        return ResponseEntity.ok("User registered successfully");
    }


}