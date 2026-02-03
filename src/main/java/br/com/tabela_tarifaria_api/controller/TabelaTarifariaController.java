package br.com.tabela_tarifaria_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tabela_tarifaria_api.dto.TabelaTarifariaDTO;
import br.com.tabela_tarifaria_api.service.TabelaTarifariaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tabelas-tarifarias")
public class TabelaTarifariaController {
    
    @Autowired
    private TabelaTarifariaService tabelaTarifariaService;

    @PostMapping
    public ResponseEntity<TabelaTarifariaDTO> criarTabelaTarifaria(@Valid @RequestBody TabelaTarifariaDTO tabelaTarifaria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tabelaTarifariaService.criarTabelaTarifaria(tabelaTarifaria));
    }

    @GetMapping
    public ResponseEntity<List<TabelaTarifariaDTO>> listarTabelasTarifarias() {
        return ResponseEntity.ok().body(tabelaTarifariaService.listarTabelaTarifarias());
    }
 
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTabelaTarifaria(@Valid @PathVariable Long id) {
        tabelaTarifariaService.deletarTabelaTarifaria(id);
        return ResponseEntity.noContent().build();
    }
}