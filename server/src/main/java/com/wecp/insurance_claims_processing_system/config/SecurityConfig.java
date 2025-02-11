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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter,
            PasswordEncoder passwordEncoder) {

        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/register", "/api/user/login").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/adjuster/claim/**").hasAnyAuthority("ADJUSTER")
                .antMatchers(HttpMethod.GET, "/api/adjuster/claims/**").hasAnyAuthority("ADJUSTER")
                .antMatchers(HttpMethod.GET, "/api/adjuster/underwriters/**").hasAnyAuthority("ADJUSTER")
                .antMatchers(HttpMethod.PUT, "/api/adjuster/claim/{claimId}/assign/**").hasAnyAuthority("ADJUSTER")
                .antMatchers(HttpMethod.POST, "/api/policyholder/claim/**").hasAnyAuthority("POLICYHOLDER")
                .antMatchers(HttpMethod.GET, "/api/policyholder/claims/**").hasAnyAuthority("POLICYHOLDER")
                .antMatchers(HttpMethod.POST, "/api/investigator/investigation/**").hasAnyAuthority("INVESTIGATOR")
                .antMatchers(HttpMethod.PUT, "/api/investigator/investigation/**").hasAnyAuthority("INVESTIGATOR")
                .antMatchers(HttpMethod.GET, "/api/investigator/investigations/**").hasAnyAuthority("INVESTIGATOR")
                .antMatchers(HttpMethod.GET, "/api/underwriter/claims/**").hasAnyAuthority("UNDERWRITER")
                .antMatchers(HttpMethod.PUT, "/api/underwriter/claim/{id}/review/**").hasAnyAuthority("UNDERWRITER")

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // configure the security of the application such that
    // /api/user/register and /api/user/login are permitted to all
    // /api/adjuster/claim/{id} and /api/adjuster/claims are permitted to ADJUSTER
    // /api/adjuster/underwriters permitted to ADJUSTER
    // /api/adjuster/claim/{claimId}/assign is permitted to ADJUSTER
    // /api/policyholder/claim and /api/policyholder/claims are permitted to
    // POLICYHOLDER
    // /api/investigator/investigation permitted to INVESTIGATOR
    // /api/investigator/investigation/{id} is permitted to INVESTIGATOR
    // /api/investigator/investigations is permitted to INVESTIGATOR
    // /api/underwriter/claim/{id}/review is permitted to UNDERWRITER
    // /api/underwriter/claims is permitted to UNDERWRITER

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // return new UserDetailsService();
    // }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



        // @Bean
        // public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        //     return authenticationConfiguration.getAuthenticationManager();
        // }

}
