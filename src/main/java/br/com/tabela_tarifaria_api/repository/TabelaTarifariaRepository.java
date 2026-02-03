package br.com.tabela_tarifaria_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tabela_tarifaria_api.model.TabelaTarifaria;

public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, Long>{
}