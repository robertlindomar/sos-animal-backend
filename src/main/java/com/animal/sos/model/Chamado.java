package com.animal.sos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String endereco;
    
    @Column(nullable = false)
    private String telefone;
    
    @Column(length = 1000)
    private String imagem; // URL ou caminho da imagem
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAnimal tipo;
    
    @Column(nullable = false, length = 2000)
    private String descricao;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusChamado status = StatusChamado.ABERTO;
    
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    public enum TipoAnimal {
        CACHORRO, GATO, OUTROS
    }
    
    public enum StatusChamado {
        ABERTO, ATENDIDO, CANCELADO
    }
}

