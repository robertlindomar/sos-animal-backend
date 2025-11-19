package com.animal.sos.service;

import com.animal.sos.model.Chamado;
import com.animal.sos.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChamadoService {
    
    private static final String UPLOAD_DIR = "uploads/";
    
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    public List<Chamado> listarTodos() {
        return chamadoRepository.findAll();
    }
    
    public Optional<Chamado> buscarPorId(Long id) {
        return chamadoRepository.findById(id);
    }
    
    public List<Chamado> buscarPorStatus(String status) {
        try {
            Chamado.StatusChamado statusEnum = Chamado.StatusChamado.valueOf(status.toUpperCase());
            return chamadoRepository.findByStatus(statusEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }
    
    public Map<String, Object> criarChamado(String endereco, String telefone, String tipo, 
                                            String descricao, MultipartFile imagem) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Chamado chamado = new Chamado();
            chamado.setEndereco(endereco);
            chamado.setTelefone(telefone);
            chamado.setDescricao(descricao);
            chamado.setTipo(Chamado.TipoAnimal.valueOf(tipo.toUpperCase()));
            chamado.setStatus(Chamado.StatusChamado.ABERTO);
            
            if (imagem != null && !imagem.isEmpty()) {
                String imagemUrl = salvarImagem(imagem);
                chamado.setImagem(imagemUrl);
            }
            
            chamadoRepository.save(chamado);
            
            response.put("success", true);
            response.put("message", "Chamado criado com sucesso");
            response.put("chamado", chamado);
            return response;
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao criar chamado: " + e.getMessage());
            return response;
        }
    }
    
    public Map<String, Object> atualizarChamado(Long id, Map<String, Object> dados) {
        Map<String, Object> response = new HashMap<>();
        Optional<Chamado> chamadoOpt = chamadoRepository.findById(id);
        
        if (chamadoOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Chamado não encontrado");
            return response;
        }
        
        Chamado chamado = chamadoOpt.get();
        
        if (dados.containsKey("endereco")) {
            chamado.setEndereco((String) dados.get("endereco"));
        }
        if (dados.containsKey("telefone")) {
            chamado.setTelefone((String) dados.get("telefone"));
        }
        if (dados.containsKey("tipo")) {
            chamado.setTipo(Chamado.TipoAnimal.valueOf(((String) dados.get("tipo")).toUpperCase()));
        }
        if (dados.containsKey("descricao")) {
            chamado.setDescricao((String) dados.get("descricao"));
        }
        if (dados.containsKey("status")) {
            chamado.setStatus(Chamado.StatusChamado.valueOf(((String) dados.get("status")).toUpperCase()));
        }
        
        chamadoRepository.save(chamado);
        
        response.put("success", true);
        response.put("message", "Chamado atualizado com sucesso");
        response.put("chamado", chamado);
        return response;
    }
    
    public boolean deletarChamado(Long id) {
        if (chamadoRepository.existsById(id)) {
            chamadoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private String salvarImagem(MultipartFile imagem) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        String nomeArquivo = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
        Path filePath = uploadPath.resolve(nomeArquivo);
        Files.write(filePath, imagem.getBytes());
        
        return "/uploads/" + nomeArquivo;
    }
}

