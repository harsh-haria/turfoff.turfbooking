package com.turfoff.turfbooking.config;

import com.turfoff.turfbooking.jwt.AuthEntryPointJwt;
import com.turfoff.turfbooking.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeRequests) ->
            authorizeRequests
                    .requestMatchers("/admin/new").permitAll()
                    .requestMatchers("/admin/signin").permitAll()
                    .anyRequest().authenticated()
        );
        http.sessionManagement(sessionManagement ->  sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt));
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
