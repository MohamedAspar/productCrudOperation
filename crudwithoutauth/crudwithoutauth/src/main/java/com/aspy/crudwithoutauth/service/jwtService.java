package com.aspy.crudwithoutauth.service;

import org.springframework.security.core.userdetails.UserDetails;


public interface jwtService {
	
	String getUsername(String token);
    
	String generateToken(UserDetails userDetails);

}
