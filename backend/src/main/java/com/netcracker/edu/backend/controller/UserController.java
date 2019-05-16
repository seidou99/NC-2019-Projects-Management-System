package com.netcracker.edu.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.edu.backend.entity.User;
import com.netcracker.edu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
}
