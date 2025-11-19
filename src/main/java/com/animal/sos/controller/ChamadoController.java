package com.animal.sos.controller;

import com.animal.sos.model.Chamado;
import com.animal.sos.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {
    
    @Autowired
    private ChamadoService chamadoService;
    
    @GetMapping
    public ResponseEntity<List<Chamado>> listarTodos() {
        return ResponseEntity.ok(chamadoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscarPorId(@PathVariable Long id) {
        return chamadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Chamado>> buscarPorStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(chamadoService.buscarPorStatus(status));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> criarChamado(
            @RequestParam("endereco") String endereco,
            @RequestParam("telefone") String telefone,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem,
            @RequestParam("tipo") String tipo,
            @RequestParam("descricao") String descricao) {
        
        Map<String, Object> response = chamadoService.criarChamado(endereco, telefone, tipo, descricao, imagem);
        
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarChamado(
            @PathVariable Long id,
            @RequestBody Map<String, Object> dados) {
        
        Map<String, Object> response = chamadoService.atualizarChamado(id, dados);
        
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletarChamado(@PathVariable Long id) {
        boolean deletado = chamadoService.deletarChamado(id);
        
        if (deletado) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Chamado deletado com sucesso"
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

