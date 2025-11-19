package com.animal.sos.config;

import com.animal.sos.model.Admin;
import com.animal.sos.model.Chamado;
import com.animal.sos.repository.AdminRepository;
import com.animal.sos.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Criar admin inicial se não existir
        if (adminRepository.findByNome("admin").isEmpty()) {
            Admin admin = new Admin();
            admin.setNome("admin");
            admin.setSenha(passwordEncoder.encode("123456"));
            adminRepository.save(admin);
            System.out.println("========================================");
            System.out.println("Admin inicial criado:");
            System.out.println("Nome: admin");
            System.out.println("Senha: 123456");
            System.out.println("========================================");
        }

        // Criar chamados iniciais se não existirem
        if (chamadoRepository.findAll().isEmpty()) {
            // Chamado 1 - ABERTO
            Chamado chamado1 = new Chamado();
            chamado1.setEndereco("Rua das Flores, 123 - Centro");
            chamado1.setTelefone("(11) 98765-4321");
            chamado1.setTipo(Chamado.TipoAnimal.CACHORRO);
            chamado1.setDescricao("Cachorro encontrado na rua, parece estar perdido e com fome. Precisa de ajuda veterinária.");
            chamado1.setStatus(Chamado.StatusChamado.ABERTO);
            chamadoRepository.save(chamado1);
            
            // Chamado 2 - ATENDIDO
            Chamado chamado2 = new Chamado();
            chamado2.setEndereco("Avenida Principal, 456 - Jardim das Rosas");
            chamado2.setTelefone("(11) 91234-5678");
            chamado2.setTipo(Chamado.TipoAnimal.GATO);
            chamado2.setDescricao("Gato machucado na pata, já foi resgatado e levado ao veterinário. Situação resolvida.");
            chamado2.setStatus(Chamado.StatusChamado.ATENDIDO);
            chamadoRepository.save(chamado2);
            
            // Chamado 3 - CANCELADO
            Chamado chamado3 = new Chamado();
            chamado3.setEndereco("Rua dos Pássaros, 789 - Vila Nova");
            chamado3.setTelefone("(11) 99876-5432");
            chamado3.setTipo(Chamado.TipoAnimal.OUTROS);
            chamado3.setDescricao("Chamado cancelado - animal já foi encontrado pelo dono antes da nossa chegada.");
            chamado3.setStatus(Chamado.StatusChamado.CANCELADO);
            chamadoRepository.save(chamado3);
            
            System.out.println("========================================");
            System.out.println("3 chamados iniciais criados:");
            System.out.println("1. ABERTO - Cachorro perdido");
            System.out.println("2. ATENDIDO - Gato machucado");
            System.out.println("3. CANCELADO - Animal encontrado");
            System.out.println("========================================");
        }
    }
}
