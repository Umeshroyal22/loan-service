package com.example.demo.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * JWT Utility Class
 */

@Component
public class JwtUtil {

    private static final String SECRET =
            "myfinmyfinmyfinmyfinmyfinmyfin12";

    private Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());


    // Generate Token

    public String generateToken(
    		String username,
    		String role){

    		return Jwts.builder()

    		.setSubject(username)

    		.claim("role", role)

    		.setIssuedAt(new Date())

    		.setExpiration(
    		new Date(System.currentTimeMillis()+86400000))

    		.signWith(key,
    		SignatureAlgorithm.HS256)

    		.compact();
    		}


    // Extract Username

    public String extractUsername(String token){

        return getClaims(token).getSubject();
    }


    // Validate Token

    public boolean validateToken(String token){

        try{

            getClaims(token);

            return true;

        }catch(Exception e){

            return false;
        }
    }


    // Get Claims

    private Claims getClaims(String token){

        return Jwts.parserBuilder()

        .setSigningKey(key)

        .build()

        .parseClaimsJws(token)

        .getBody();
    }


    public String extractRole(String token){

    	return Jwts.parserBuilder()

    	.setSigningKey(key)

    	.build()

    	.parseClaimsJws(token)

    	.getBody()

    	.get("role",String.class);

    	}

}