package com.animal.sos.service;

import com.animal.sos.model.Admin;
import com.animal.sos.repository.AdminRepository;
import com.animal.sos.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public Map<String, Object> login(String nome, String senha) {
        Map<String, Object> response = new HashMap<>();
        
        if (nome == null || senha == null || nome.isEmpty() || senha.isEmpty()) {
            response.put("success", false);
            response.put("message", "Nome e senha são obrigatórios");
            return response;
        }
        
        Optional<Admin> adminOpt = adminRepository.findByNome(nome);
        
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (passwordEncoder.matches(senha, admin.getSenha())) {
                String token = jwtUtil.generateToken(admin.getNome());
                response.put("success", true);
                response.put("message", "Login realizado com sucesso");
                response.put("token", token);
                response.put("adminId", admin.getId());
                response.put("nome", admin.getNome());
                return response;
            }
        }
        
        response.put("success", false);
        response.put("message", "Credenciais inválidas");
        return response;
    }
    
    public Map<String, Object> criarAdmin(String nome, String senha) {
        Map<String, Object> response = new HashMap<>();
        
        if (nome == null || senha == null || nome.isEmpty() || senha.isEmpty()) {
            response.put("success", false);
            response.put("message", "Nome e senha são obrigatórios");
            return response;
        }
        
        if (adminRepository.findByNome(nome).isPresent()) {
            response.put("success", false);
            response.put("message", "Admin com este nome já existe");
            return response;
        }
        
        Admin admin = new Admin();
        admin.setNome(nome);
        admin.setSenha(passwordEncoder.encode(senha));
        adminRepository.save(admin);
        
        response.put("success", true);
        response.put("message", "Admin criado com sucesso");
        return response;
    }
}

