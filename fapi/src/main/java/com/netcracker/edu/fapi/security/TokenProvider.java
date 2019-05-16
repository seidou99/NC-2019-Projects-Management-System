package com.netcracker.edu.fapi.security;

import com.netcracker.edu.fapi.model.User;
import com.netcracker.edu.fapi.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements Serializable {

    private UserService userService;

    @Autowired
    public TokenProvider(UserService userService) {
        this.userService = userService;
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityJWTConstants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        User user = userService.findByEmail(authentication.getName());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setId("" + user.getId())
                .claim(SecurityJWTConstants.AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SecurityJWTConstants.SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + SecurityJWTConstants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000)))
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUsernameFromToken(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token, Authentication existingAuthentication, UserDetails userDetails) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityJWTConstants.SIGNING_KEY);
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        final Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(SecurityJWTConstants.AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
