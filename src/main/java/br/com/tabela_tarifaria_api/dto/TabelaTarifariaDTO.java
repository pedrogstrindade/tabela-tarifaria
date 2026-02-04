package br.com.tabela_tarifaria_api.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TabelaTarifariaDTO {

    private String nomeTabelaTarifaria;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVigencia;
    
    private List<CategoriaRelacaoDTO> categoriasRelacao;
}