package com.turfoff.turfbooking.config;

import com.turfoff.turfbooking.jwt.AdminAuthTokenFilter;
import com.turfoff.turfbooking.jwt.AuthEntryPointJwt;
import com.turfoff.turfbooking.jwt.UserAuthTokenFilter;
import com.turfoff.turfbooking.services.CustomAdminDetailsService;
import com.turfoff.turfbooking.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAdminDetailsService customAdminDetailsService;

    @Bean
    public UserAuthTokenFilter authTokenFilter() {
        return new UserAuthTokenFilter();
    }

    @Bean
    public AdminAuthTokenFilter adminAuthTokenFilter(){
        return new AdminAuthTokenFilter();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProviderForUser());
        auth.authenticationProvider(authProviderForAdmin());
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeRequests) ->
            authorizeRequests
                    .requestMatchers("/admin/new").permitAll()
                    .requestMatchers("/admin/signin").permitAll()
                    .requestMatchers("/users/new").permitAll()
                    .requestMatchers("/users/signin").permitAll()
                    .requestMatchers("/users/serveralive").permitAll()
                    .anyRequest().authenticated()
        );
//        http.getSharedObject(AuthenticationManagerBuilder.class).authenticationProvider(authProviderForUser());
        http.authenticationProvider(authProviderForUser());
        http.authenticationProvider(authProviderForAdmin());
        http.sessionManagement(sessionManagement ->  sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt));
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(adminAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private AuthenticationProvider authProviderForUser() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authProvider.setUserDetailsService(customUserDetailsService);
        return authProvider;
    }

    private AuthenticationProvider authProviderForAdmin() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authProvider.setUserDetailsService(customAdminDetailsService);
        return authProvider;
    }
}
