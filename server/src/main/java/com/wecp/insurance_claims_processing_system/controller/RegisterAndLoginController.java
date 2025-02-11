package com.wecp.insurance_claims_processing_system.controller;


import com.wecp.insurance_claims_processing_system.dto.LoginRequest;
import com.wecp.insurance_claims_processing_system.dto.LoginResponse;
import com.wecp.insurance_claims_processing_system.entity.User;
import com.wecp.insurance_claims_processing_system.jwt.JwtUtil;
import com.wecp.insurance_claims_processing_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
public class RegisterAndLoginController {

    
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/api/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.createUser(user));
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT);
        }
    }

    

    @PostMapping("/api/user/login")
     public ResponseEntity loginUser(@RequestBody LoginRequest loginRequest) {
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            } catch(AuthenticationException e) {
                 throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Invalid username or password" ,e);
            }
            final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            User foundUser = userService.getUserByUsername(loginRequest.getUsername());
            final String token = jwtUtil.generateToken(loginRequest.getUsername());
            String role = foundUser.getRole();
            Long userId = foundUser.getId();
            String username=foundUser.getUsername();
            String email=foundUser.getEmail();
            System.out.println("User Roles: " + role);
            return ResponseEntity.ok(new LoginResponse(userId, role, token, username, email));
        }
    
    
}



 
    // @Autowired
    // UserLoginServiceImpl userLoginService;

    // @Autowired
    // AuthenticationManager authenticationManager;

    // @Autowired
    // JwtUtil jwtUtil;

    // @PostMapping("/register")
    // public ResponseEntity<?> registerUser(@RequestBody User user) {
    //     try{
    //         return ResponseEntity.ok(userLoginService.createUser(user));
    //     } catch(Exception ex) {
    //         return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT);
    //     }
    // }

    // @PostMapping("/login")
    // public ResponseEntity loginUser(@RequestBody LoginRequest loginRequest) {
    //     try{
    //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    //     } catch(AuthenticationException e) {
    //          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Invalid username or password" ,e);
    //     }
    //     final UserDetails userDetails = userLoginService.loadUserByUsername(loginRequest.getUsername());
    //     User foundUser = userLoginService.getUserByUsername(loginRequest.getUsername());
    //     final String token = jwtUtil.generateToken(loginRequest.getUsername());
    //     String role = foundUser.getRole();
    //     Integer userId = foundUser.getUserId();
    //     System.out.println("User Roles: " + role);
    //     return ResponseEntity.ok(new LoginResponse(token, role, userId));
    // }