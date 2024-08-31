package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mysql.entities.AdminEntity;
import com.turfoff.turfbooking.repositories.mysql.AdminRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public CustomAdminDetailsService (AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AdminEntity> adminEntity = Optional.ofNullable(adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No admin found with this email id")));
        if (!adminEntity.isPresent()) {
            throw new UsernameNotFoundException("No admin found with this email id");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        AdminEntity admin = adminEntity.get();
        return new User(
                admin.getEmail(), admin.getPassword(),
                true, true,
                true, true,
                grantedAuthorities
        );
    }
}
