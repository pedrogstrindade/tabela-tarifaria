package br.com.tabela_tarifaria_api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tabela_tarifaria_api.dto.CalculoRequestDTO;
import br.com.tabela_tarifaria_api.dto.CalculoResponseDTO;
import br.com.tabela_tarifaria_api.dto.DetalhamentoDTO;
import br.com.tabela_tarifaria_api.dto.FaixaDTO;
import br.com.tabela_tarifaria_api.model.CategoriaRelacao;
import br.com.tabela_tarifaria_api.model.FaixaConsumo;
import br.com.tabela_tarifaria_api.model.TabelaTarifaria;
import br.com.tabela_tarifaria_api.repository.TabelaTarifariaRepository;

@Service
public class CalculoService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    public CalculoResponseDTO calcularConsumo(CalculoRequestDTO calculoRequestDTO) {

        TabelaTarifaria tabelaTarifariaRecente = tabelaTarifariaRepository
                .findFirstByDataVigenciaLessThanEqualOrderByDataVigenciaDesc(LocalDate.now(ZoneId.of("America/Recife")))
                .orElseThrow(() -> new RuntimeException("Nenhuma tabela tarifária vigente encontrada"));

        CategoriaRelacao categoria = tabelaTarifariaRecente.getCategoriasRelacao().stream()
                .filter(c -> c.getCategoria().equals(calculoRequestDTO.getCategoria())).findFirst()
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada na tabela tarifária"));

        // lista e organiza as faixas em ordem crescente
        List<FaixaConsumo> faixas = categoria.getFaixasConsumo().stream()
                .sorted(Comparator.comparingInt(FaixaConsumo::getInicio)).toList();

        List<DetalhamentoDTO> detalhamento = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        int consumoRestante = calculoRequestDTO.getConsumo();

        // looping para calcular os consumos de cada intervalo de faixa acrescentando ao
        // detalhamento no fim
        for (FaixaConsumo faixa : faixas) {
            if (consumoRestante <= 0) {
                break;
            }

            int capacidadeFaixa;

            if (faixa.getFim() == null) {
                capacidadeFaixa = consumoRestante;
            } else {
                capacidadeFaixa = (faixa.getFim() - faixa.getInicio()) + 1;
            }

            int m3Cobrado = Math.min(capacidadeFaixa, consumoRestante);

            BigDecimal subtotal = faixa.getValorUnitario().multiply(BigDecimal.valueOf(m3Cobrado));
            valorTotal = valorTotal.add(subtotal);

            // apenas para ficar no formato solicitado
            FaixaDTO faixaDetalhada = new FaixaDTO();
            faixaDetalhada.setInicio(faixa.getInicio());
            faixaDetalhada.setFim(faixa.getFim());

            detalhamento.add(new DetalhamentoDTO(faixaDetalhada, m3Cobrado, faixa.getValorUnitario(), subtotal));

            consumoRestante -= m3Cobrado;
        }

        // arredondamento para o formato de dinheiro
        valorTotal = valorTotal.setScale(2, RoundingMode.HALF_UP);

        return new CalculoResponseDTO(calculoRequestDTO.getCategoria(), calculoRequestDTO.getConsumo(), valorTotal,
                detalhamento);
    }
}