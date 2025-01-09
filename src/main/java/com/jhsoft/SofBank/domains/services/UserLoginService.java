package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.entities.UserLogin;
import com.jhsoft.SofBank.domains.repositories.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = userLoginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority(userLogin.getTypeRol().toString());

        return new org.springframework.security.core.userdetails.User(
                userLogin.getUsername(),
                userLogin.getPassword(),
                userLogin.isEnabled(),
                true,
                true,
                true,
                Collections.singletonList(authority)
        );
    }

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = userLoginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        Set<GrantedAuthority> authorities = userLogin.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getTypeRol().name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                userLogin.getUsername(),
                userLogin.getPassword(),
                userLogin.isEnabled(),
                true,
                true,
                true,
                authorities
        );

    }*/
}
