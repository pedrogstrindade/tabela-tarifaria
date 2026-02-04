package br.com.tabela_tarifaria_api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tabela_tarifaria_api.constants.Categoria;
import br.com.tabela_tarifaria_api.dto.CategoriaRelacaoDTO;
import br.com.tabela_tarifaria_api.dto.FaixaConsumoDTO;
import br.com.tabela_tarifaria_api.dto.TabelaTarifariaDTO;
import br.com.tabela_tarifaria_api.model.CategoriaRelacao;
import br.com.tabela_tarifaria_api.model.FaixaConsumo;
import br.com.tabela_tarifaria_api.model.TabelaTarifaria;
import br.com.tabela_tarifaria_api.repository.TabelaTarifariaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TabelaTarifariaService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    public void validarTabela(TabelaTarifariaDTO dto) {
        Categoria[] categoriasObrigatorias = Categoria.values();
        Set<Categoria> categoriasRecebidas = dto.getCategorias().stream().map(CategoriaRelacaoDTO::getCategoria)
                .collect(Collectors.toSet());

        if (categoriasRecebidas.size() != dto.getCategorias().size()) {
            throw new RuntimeException("Categorias duplicadas ou inexistentes!");
        }

        for (Categoria obrigatoria : categoriasObrigatorias) {
            if (!categoriasRecebidas.contains(obrigatoria)) {
                throw new RuntimeException("Todas categorias devem ser cadastradas!");
            }
        }

    }

    public TabelaTarifariaDTO criarTabelaTarifaria(TabelaTarifariaDTO dto) {
        if (tabelaTarifariaRepository.existsByNomeTabelaTarifariaAndDataVigencia(dto.getNomeTabelaTarifaria(),
                dto.getDataVigencia())) {
            throw new RuntimeException("Já existe uma tabela cadastrada para este período.");
        }

        validarTabela(dto);
        TabelaTarifaria tabelaTarifaria = new TabelaTarifaria();
        tabelaTarifaria.setNomeTabelaTarifaria(dto.getNomeTabelaTarifaria());
        tabelaTarifaria.setDataVigencia(dto.getDataVigencia());

        for (CategoriaRelacaoDTO categoriaDTO : dto.getCategorias()) {
            CategoriaRelacao categoriaRelacao = new CategoriaRelacao();
            categoriaRelacao.setCategoria(categoriaDTO.getCategoria());
            categoriaRelacao.setTabelaTarifaria(tabelaTarifaria);

            for (FaixaConsumoDTO faixaDTO : categoriaDTO.getFaixasConsumo()) {
                FaixaConsumo faixaConsumo = new FaixaConsumo();
                faixaConsumo.setInicio(faixaDTO.getInicio());
                faixaConsumo.setFim(faixaDTO.getFim());
                faixaConsumo.setValorUnitario(faixaDTO.getValorUnitario());
                faixaConsumo.setCategoriaRelacao(categoriaRelacao);

                categoriaRelacao.getFaixasConsumo().add(faixaConsumo);
            }

            tabelaTarifaria.getCategoriasRelacao().add(categoriaRelacao);
        }

        tabelaTarifariaRepository.save(tabelaTarifaria);
        return converterParaDTO(tabelaTarifaria);

    }

    public List<TabelaTarifariaDTO> listarTabelaTarifarias() {
        List<TabelaTarifaria> lista = tabelaTarifariaRepository.findAll();
        List<TabelaTarifariaDTO> listaDTO = new ArrayList<>();
        for (TabelaTarifaria tabelaTarifaria : lista) {
            listaDTO.add(converterParaDTO(tabelaTarifaria));
        }
        return listaDTO;
    }

    public TabelaTarifaria buscarTabelaPeloId(Long id) {
        Optional<TabelaTarifaria> tabelaTarifaria = tabelaTarifariaRepository.findById(id);
        if (tabelaTarifaria.isPresent()) {
            return tabelaTarifaria.get();
        }

        return null;
    }
    
    public void deletarTabelaTarifaria(Long id) {
        tabelaTarifariaRepository.deleteById(id);
    }

    public TabelaTarifariaDTO converterParaDTO(TabelaTarifaria tabela) {
        TabelaTarifariaDTO dto = new TabelaTarifariaDTO();
        dto.setNomeTabelaTarifaria(tabela.getNomeTabelaTarifaria());
        dto.setDataVigencia(tabela.getDataVigencia());
        dto.setCategorias(tabela.getCategoriasRelacao().stream()
                .map(relacao -> {
                    CategoriaRelacaoDTO categoriaDTO = new CategoriaRelacaoDTO();
                    categoriaDTO.setCategoria(relacao.getCategoria());
                    categoriaDTO.setFaixasConsumo(relacao.getFaixasConsumo().stream()
                            .sorted(Comparator.comparing(FaixaConsumo::getInicio))
                            .map(faixa -> new FaixaConsumoDTO(
                                    faixa.getInicio(),
                                    faixa.getFim(),
                                    faixa.getValorUnitario()))
                            .collect(Collectors.toList()));

                    return categoriaDTO;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}