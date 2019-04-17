package com.netcracker.edu.name.controllers;

import com.netcracker.edu.name.models.AuthToken;
import com.netcracker.edu.name.models.User;
import com.netcracker.edu.name.models.UserAuthData;
import com.netcracker.edu.name.security.TokenProvider;
import com.netcracker.edu.name.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

//    @Secured("Admin")
    @PostMapping(value = "/users")
    public ResponseEntity register(@RequestBody User user) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        this.userService.save(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthToken> login(@RequestBody UserAuthData userAuthData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthData.getEmail(),
                        userAuthData.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @GetMapping(value = "/users")
    public Iterable<User> findAllUsers() {
        return userService.findAll();
    }
}
