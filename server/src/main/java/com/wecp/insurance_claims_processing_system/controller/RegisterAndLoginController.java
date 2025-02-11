package com.wecp.insurance_claims_processing_system.controller;


import com.wecp.insurance_claims_processing_system.dto.LoginRequest;
import com.wecp.insurance_claims_processing_system.dto.LoginResponse;
import com.wecp.insurance_claims_processing_system.entity.User;
import com.wecp.insurance_claims_processing_system.jwt.JwtUtil;
import com.wecp.insurance_claims_processing_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


public class RegisterAndLoginController {


    @PostMapping("/api/user/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // register user
        // save the user based on the role and return the user
        // make sure data is saved in User table as well as in the respective role table
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        // login user
        // return jwt token in LoginResponse object
        // if authentication fails,response with 401 status code
    }
}
