package br.com.tabela_tarifaria_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tabela_tarifaria_api.dto.CalculoRequestDTO;
import br.com.tabela_tarifaria_api.dto.CalculoResponseDTO;
import br.com.tabela_tarifaria_api.service.CalculoService;

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {
    
    @Autowired
    private CalculoService tabelaTarifariaService;

    @PostMapping
    public ResponseEntity<CalculoResponseDTO> calculoConsumo(@RequestBody CalculoRequestDTO calculoRequestDTO) {
        return ResponseEntity.ok().body(tabelaTarifariaService.calcularConsumo(calculoRequestDTO));
    }
}