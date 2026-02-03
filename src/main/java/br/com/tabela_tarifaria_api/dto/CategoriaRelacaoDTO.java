package br.com.tabela_tarifaria_api.dto;

import java.util.List;

import br.com.tabela_tarifaria_api.constants.Categoria;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRelacaoDTO {
    
    @NotNull
    private Categoria categoria;

    @NotNull
    private List<FaixaConsumoDTO> faixasConsumo;
}