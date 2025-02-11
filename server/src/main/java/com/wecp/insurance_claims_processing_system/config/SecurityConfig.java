package com.wecp.insurance_claims_processing_system.config;

import com.wecp.insurance_claims_processing_system.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class SecurityConfig  {

    // configure the security of the application such that
    // /api/user/register and /api/user/login are permitted to all
    // /api/adjuster/claim/{id} and /api/adjuster/claims are permitted to ADJUSTER
    // /api/adjuster/underwriters permitted to ADJUSTER
    // /api/adjuster/claim/{claimId}/assign is permitted to ADJUSTER
    // /api/policyholder/claim and /api/policyholder/claims are permitted to POLICYHOLDER
    // /api/investigator/investigation  permitted to INVESTIGATOR
    // /api/investigator/investigation/{id} is permitted to INVESTIGATOR
    // /api/investigator/investigations is permitted to INVESTIGATOR
    // /api/underwriter/claim/{id}/review is permitted to UNDERWRITER
    // /api/underwriter/claims is permitted to UNDERWRITER

}