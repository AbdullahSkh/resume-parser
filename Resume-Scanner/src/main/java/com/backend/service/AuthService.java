package com.backend.service;

import com.backend.model.User;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String email, String password, User.Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword, role);
        userRepository.save(user);
        return userRepository.save(user);
    }

    public String authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(), authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Authenticated Successfully";
    }
}
