package com.netcracker.edu.fapi.controller;

import com.netcracker.edu.fapi.model.AuthToken;
import com.netcracker.edu.fapi.model.User;
import com.netcracker.edu.fapi.model.UserAuthData;
import com.netcracker.edu.fapi.security.TokenProvider;
import com.netcracker.edu.fapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

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

    @PostMapping(value = "/users", produces = "application/json")
    public ResponseEntity register(@RequestBody User user) {
        try {
            return new ResponseEntity<User>(this.userService.save(user), HttpStatus.CREATED);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
        }
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
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @GetMapping(value = "/users")
    public Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/users", params = "roles")
    public Iterable<User> findAllUsersWithRoles(@RequestParam(name = "roles") List<String> roles) {
        return userService.findAllWithRoles(roles);
    }
}
