package br.com.tabela_tarifaria_api.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.tabela_tarifaria_api.constants.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculoResponseDTO {

    private Categoria categoria;
    private Integer consumoTotal;
    private BigDecimal valorTotal;
    private List<Detalhamento> detalhamento;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detalhamento {
        private Faixa faixa;

        private Integer m3Cobrados;

        private BigDecimal valorUnitario;

        private BigDecimal subtotal;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Faixa {

            private Integer inicio;

            private Integer fim;

        }
    }
}