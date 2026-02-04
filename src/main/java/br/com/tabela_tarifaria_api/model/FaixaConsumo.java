package br.com.tabela_tarifaria_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FAIXA_CONSUMO", schema = "tabela_tarifaria_api")
public class FaixaConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAIXA_ID")
    private Long id;

    @NotNull
    @Column(name = "INICIO_FAIXA", nullable = false)
    private Integer inicio;

    @NotNull
    @Column(name = "FIM_FAIXA", nullable = false)
    private Integer fim;

    @NotNull
    @Column(name = "VALOR_UNITARIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA_RELACAO", nullable = false)
    private CategoriaRelacao categoriaRelacao;
}