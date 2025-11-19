package com.animal.sos.security;

import com.animal.sos.model.Admin;
import com.animal.sos.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByNome(nome)
                .orElseThrow(() -> new UsernameNotFoundException("Admin não encontrado: " + nome));
        
        return User.builder()
                .username(admin.getNome())
                .password(admin.getSenha())
                .roles("ADMIN")
                .build();
    }
}

