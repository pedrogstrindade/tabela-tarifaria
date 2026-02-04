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
        // validar as categorias
        Categoria[] categoriasObrigatorias = Categoria.values();
        Set<Categoria> categoriasRecebidas = dto.getCategoriasRelacao().stream().map(CategoriaRelacaoDTO::getCategoria)
                .collect(Collectors.toSet());

        if (categoriasRecebidas.size() != dto.getCategoriasRelacao().size()) {
            throw new RuntimeException("Categorias duplicadas ou inexistentes!");
        }

        for (Categoria obrigatoria : categoriasObrigatorias) {
            if (!categoriasRecebidas.contains(obrigatoria)) {
                throw new RuntimeException("Todas categorias devem ser cadastradas!");
            }
        }

        // validar as faixas de consumo
        List<CategoriaRelacaoDTO> categoriasRelacao = dto.getCategoriasRelacao();
        for (CategoriaRelacaoDTO categoriaDTO : categoriasRelacao) {
            List<FaixaConsumoDTO> faixas = categoriaDTO.getFaixasConsumo();
            faixas.sort(Comparator.comparing(FaixaConsumoDTO::getInicio));

            if (faixas.get(0).getInicio() != 0) {
                throw new RuntimeException("A categoria deve começar com a faixa no 0!");
            }

            Integer faixaFimAnterior = -1;
            for (FaixaConsumoDTO faixa : faixas) {
                if (faixa.getInicio() >= faixa.getFim()) {
                    throw new RuntimeException(
                            "O início deve ser menor que o fim na categoria: " + categoriaDTO.getCategoria());
                }

                if (faixaFimAnterior != -1 && faixa.getInicio() <= faixaFimAnterior) {
                    throw new RuntimeException("Sobreposição de faixas na categoria: " + categoriaDTO.getCategoria());
                }

                if (faixa.getInicio() != faixaFimAnterior + 1) {
                    throw new RuntimeException("As faixas devem cobrir todos consumos possíveis! Gap encontrado em: " + categoriaDTO.getCategoria() + " (" + faixaFimAnterior + ")");
                }
                faixaFimAnterior = faixa.getFim();
            }

            if (faixaFimAnterior < 99999) {
                throw new RuntimeException("A última faixa deve cobrir o máximo de consumo! (min. 99999)");

            }

            if (tabelaTarifariaRepository.existsByNomeTabelaTarifariaAndDataVigencia(dto.getNomeTabelaTarifaria(),
                    dto.getDataVigencia())) {
                throw new RuntimeException("Já existe uma tabela cadastrada para este período.");
            }
        }
    }

    public TabelaTarifariaDTO criarTabelaTarifaria(TabelaTarifariaDTO dto) {

        validarTabela(dto);
        TabelaTarifaria tabelaTarifaria = new TabelaTarifaria();
        tabelaTarifaria.setNomeTabelaTarifaria(dto.getNomeTabelaTarifaria());
        tabelaTarifaria.setDataVigencia(dto.getDataVigencia());

        for (CategoriaRelacaoDTO categoriaDTO : dto.getCategoriasRelacao()) {
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
        dto.setCategoriasRelacao(tabela.getCategoriasRelacao().stream()
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