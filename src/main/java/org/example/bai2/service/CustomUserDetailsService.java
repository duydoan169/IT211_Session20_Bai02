package org.example.bai2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.bai2.entity.Account;
import org.example.bai2.repository.AccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        List<SimpleGrantedAuthority> authorities = account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).toList();
        return new User(account.getUsername(), account.getPassword(), account.isActive(), true, true, true, authorities);
    }
}