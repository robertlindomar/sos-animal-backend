package com.animal.sos.controller;

import com.animal.sos.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String nome = credentials.get("nome");
        String senha = credentials.get("senha");
        
        Map<String, Object> response = adminService.login(nome, senha);
        
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @PostMapping("/criar")
    public ResponseEntity<Map<String, Object>> criarAdmin(@RequestBody Map<String, String> dados) {
        String nome = dados.get("nome");
        String senha = dados.get("senha");
        
        Map<String, Object> response = adminService.criarAdmin(nome, senha);
        
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

