package com.example.demo.blogapplication.Security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


    // get UserName
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // get Expiration Time
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Check Token Expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // generate JWT token
    public String generateToken(Authentication authentication, long time) {
        String username = authentication.getName();
        Date expireDate = new Date(new Date().getTime() + time * jwtExpirationDate);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }
    // Signing key
    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }


    public Claims extractAllClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"token is Incorrect");
        }

    }
    // validate Jwt token
    public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}