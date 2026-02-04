package br.com.tabela_tarifaria_api.model;

import java.util.ArrayList;
import java.util.List;

import br.com.tabela_tarifaria_api.constants.Categoria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORIA_RELACAO", schema = "tabela_tarifaria_api")
public class CategoriaRelacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA_RELACAO")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "ID_TABELA", nullable = false)
    private TabelaTarifaria tabelaTarifaria;

    @OneToMany(mappedBy = "categoriaRelacao", cascade = CascadeType.ALL)
    private List<FaixaConsumo> faixasConsumo = new ArrayList<>();
}