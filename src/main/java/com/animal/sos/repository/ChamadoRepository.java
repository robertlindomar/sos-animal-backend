package com.animal.sos.repository;

import com.animal.sos.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByStatus(Chamado.StatusChamado status);
    List<Chamado> findByTipo(Chamado.TipoAnimal tipo);
}

