package br.com.tabela_tarifaria_api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "TABELA_TARIFARIA", schema = "tabela_tarifaria_api")
public class TabelaTarifaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TABELA")
    private Long id;

    @NotNull
    @Column(name = "NOME_TABELA", nullable = false)
    private String nomeTabelaTarifaria;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_VIGENCIA", nullable = false)
    private LocalDate dataVigencia;

    @NotNull
    @OneToMany(mappedBy = "tabelaTarifaria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CategoriaRelacao> categoriasRelacao = new ArrayList<>();
}