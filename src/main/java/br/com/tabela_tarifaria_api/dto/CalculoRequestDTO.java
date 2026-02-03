package br.com.tabela_tarifaria_api.dto;

import br.com.tabela_tarifaria_api.constants.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculoRequestDTO {
   
    private Categoria categoria;
   
    private Integer consumo;
}