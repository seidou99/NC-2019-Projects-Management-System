package com.netcracker.edu.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.edu.backend.entity.User;
import com.netcracker.edu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Iterable<User> findAllUsersWithRoles(@RequestParam(name = "roles") List<String> roles)
            throws UnsupportedEncodingException {
        for (int i = 0; i < roles.size(); i++) {
            String decoded = URLDecoder.decode(roles.get(i), "UTF-8");
            roles.set(i, decoded);
        }
        return userService.findAllWithRoles(roles);
    }

    @GetMapping(params = "email")
    public ResponseEntity findUserByEmail(@RequestParam(name = "email") String email) {
        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(user.get(), Map.class);
        Map authData = (Map) map.get("authData");
        authData.put("password", user.get().getAuthData().getPassword());
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable(name = "userId") Long userId) {
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletResponse response) throws IOException {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
}
