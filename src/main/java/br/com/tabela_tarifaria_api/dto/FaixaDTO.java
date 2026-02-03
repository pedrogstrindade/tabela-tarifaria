package br.com.tabela_tarifaria_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaixaDTO {

    private Integer inicio;
    
    private Integer fim;
}