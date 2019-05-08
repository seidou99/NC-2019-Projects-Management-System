package com.netcracker.edu.name.service.impl;

import com.netcracker.edu.name.property.BackendApiProperties;
import com.netcracker.edu.name.model.User;
import com.netcracker.edu.name.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Primary
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BackendApiProperties backendApiProperties;

    @Override
    public User findByEmail(String email) {
        return restTemplate.getForObject(backendApiProperties.getUsersUri() + "?email=" + email, User.class);
    }

    @Override
    public User save(User user) {
        return restTemplate.postForObject(backendApiProperties.getUsersUri(), user, User.class);
    }

    @Override
    public Iterable<User> findAll() {
        User[] users = restTemplate.getForObject(backendApiProperties.getUsersUri(), User[].class);
        return Arrays.asList(users);
    }

    @Override
    public Iterable<User> findAllWithRoles(List<String> roles) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getUsersUri());
        for (String role : roles) {
            uri.queryParam("roles", role);
        }
        User[] users = restTemplate.getForObject(uri.toUriString(), User[].class);
        return Arrays.asList(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getAuthData().getEmail(), user.getAuthData().getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }
}
