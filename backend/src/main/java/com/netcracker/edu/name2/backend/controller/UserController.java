package com.netcracker.edu.name2.backend.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.entity.UserAuthData;
import com.netcracker.edu.name2.backend.repository.UserRepository;
import com.netcracker.edu.name2.backend.service.UserService;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping(params = "roles")
    public Iterable<User> findAllUsersWithRoles(@RequestParam(name = "roles") List<String> roles) throws UnsupportedEncodingException {
        for (int i = 0; i < roles.size(); i++) {
            String decoded = URLDecoder.decode(roles.get(i), "UTF-8");
            roles.set(i, decoded);
        }
        System.out.println("roles " + roles);
        return userService.findAllWithRoles(roles);
    }

    @GetMapping(params = "email")
    public ResponseEntity findUserByEmail(@RequestParam(name = "email") String email) {
        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(user.get(), Map.class);
        Map authData = (Map)map.get("authData");
        authData.put("password", user.get().getAuthData().getPassword());
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity findUserById(@PathVariable(name = "userId") Long userId) {
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
    }
}
