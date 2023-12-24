package com.aspy.crudwithoutauth.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aspy.crudwithoutauth.service.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements jwtService{
	
	public String generateToken(UserDetails userDetails) {
	    return Jwts.builder()
	            .setSubject(userDetails.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
	            .signWith(getSiginKey(), SignatureAlgorithm.HS256)
	            .compact();
	}
	
    public String generateRefreshToken(Map<String,Object> extraClaims , UserDetails userDetails) {
    	return Jwts.builder().setClaims(extraClaims)
	            .setSubject(userDetails.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 60480000))
	            .signWith(getSiginKey(), SignatureAlgorithm.HS256)
	            .compact();
    	
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
   }
	private  Claims extractAllClaims(String token) {
	// TODO Auto-generated method stub
	return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
}

	private Key getSiginKey() {
		byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
	    return Keys.hmacShaKeyFor(key);
	}
	public  String getUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
    	final  String username =getUsername(token);
    	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
