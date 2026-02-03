package br.com.tabela_tarifaria_api.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tabela_tarifaria_api.model.TabelaTarifaria;

public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, Long> {
    Optional<TabelaTarifaria> findFirstByDataVigenciaLessThanEqualOrderByDataVigenciaDesc(LocalDate data);
}