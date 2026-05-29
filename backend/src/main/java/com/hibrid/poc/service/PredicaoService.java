package com.hibrid.poc.service;

import com.hibrid.poc.model.Animal;
import com.hibrid.poc.model.Predicao;
import com.hibrid.poc.model.Semen;
import com.hibrid.poc.repository.AnimalRepository;
import com.hibrid.poc.repository.PredicaoRepository;
import com.hibrid.poc.repository.SemenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PredicaoService {

    private final AnimalRepository animalRepository;
    private final SemenRepository semenRepository;
    private final PredicaoRepository predicaoRepository;

    public PredicaoService(AnimalRepository animalRepository,
                           SemenRepository semenRepository,
                           PredicaoRepository predicaoRepository) {
        this.animalRepository = animalRepository;
        this.semenRepository = semenRepository;
        this.predicaoRepository = predicaoRepository;
    }

    public Map<String, Object> simular(Long vacaId, Long semenId) {
        Animal vaca = animalRepository.findById(vacaId)
                .orElseThrow(() -> new IllegalArgumentException("Vaca nao encontrada"));
        Semen semen = semenRepository.findById(semenId)
                .orElseThrow(() -> new IllegalArgumentException("Semen nao encontrado"));

        double leiteVaca = vaca.getProducaoLeiteDiaria() == null ? 18 : vaca.getProducaoLeiteDiaria();
        double pesoVaca = vaca.getPeso() == null ? 450 : vaca.getPeso();
        double pesoSemen = semen.getPeso() == null ? 800 : semen.getPeso();
        int procriacoes = vaca.getProcriacoes() == null ? 0 : vaca.getProcriacoes();

        boolean semenCorte = ehRacaDeCorte(semen.getRaca());
        boolean vacaLeiteira = ehRacaLeiteira(vaca.getRaca());

        int aptidaoLeiteira = limitar((int) Math.round(leiteVaca * 2.6) + (vacaLeiteira ? 12 : 0));
        int ganhoPeso = limitar((int) Math.round((pesoSemen / 12.0)) + (semenCorte ? 18 : 0));
        int rusticidade = limitar(40 + (semenCorte ? 35 : 15) + (int) (pesoSemen % 13));
        int fertilidade = limitar(60 + (procriacoes > 0 ? 18 : 8) + (int) (pesoVaca % 11));

        int scoreGenetico = limitar((aptidaoLeiteira + ganhoPeso + rusticidade + fertilidade) / 4);
        double producaoLeiteEstimada = arredondar((leiteVaca * 0.55) + (vacaLeiteira ? 10 : 6), 0);
        double ganhoPesoEstimado = arredondar(0.9 + (semenCorte ? 0.7 : 0.4) + (pesoSemen / 4000.0), 1);

        String tipoCruzamento = vaca.getRaca() + " x " + semen.getRaca();
        String resultadoPrevisto = scoreGenetico >= 80 ? "Alto Potencial"
                : (semenCorte ? "Aptidao Corte" : "Potencial Moderado");
        String recomendacao = montarRecomendacao(vaca, semen, semenCorte, vacaLeiteira, scoreGenetico);

        Predicao registro = new Predicao();
        registro.setVacaId(vaca.getId());
        registro.setVacaNome(vaca.getNome());
        registro.setSemenId(semen.getId());
        registro.setSemenCodigo(semen.getCodigo());
        registro.setScoreGenetico(scoreGenetico);
        registro.setProducaoLeiteEstimada(producaoLeiteEstimada);
        registro.setGanhoPesoEstimado(ganhoPesoEstimado);
        registro.setIndiceFertilidade(fertilidade);
        registro.setTipoCruzamento(tipoCruzamento);
        registro.setResultadoPrevisto(resultadoPrevisto);
        registro.setRecomendacao(recomendacao);
        registro.setCriadoEm(LocalDateTime.now());
        predicaoRepository.save(registro);

        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("vacaNome", vaca.getNome());
        resposta.put("racaVaca", vaca.getRaca());
        resposta.put("semenCodigo", semen.getCodigo());
        resposta.put("racaSemen", semen.getRaca());
        resposta.put("tipoCruzamento", tipoCruzamento);
        resposta.put("scoreGenetico", scoreGenetico);
        resposta.put("producaoLeiteEstimada", producaoLeiteEstimada);
        resposta.put("ganhoPesoEstimado", ganhoPesoEstimado);
        resposta.put("indiceFertilidade", fertilidade);
        resposta.put("resultadoPrevisto", resultadoPrevisto);
        resposta.put("recomendacao", recomendacao);
        resposta.put("composicaoGenetica", List.of(
                barra("Aptidao Leiteira", aptidaoLeiteira),
                barra("Ganho de Peso", ganhoPeso),
                barra("Rusticidade", rusticidade),
                barra("Fertilidade", fertilidade)
        ));
        resposta.put("probabilidadeCaracteristicas", List.of(
                barra("Femea leiteira", vacaLeiteira ? 52 : 41),
                barra("Macho para corte", semenCorte ? 38 : 30),
                barra("Alta resistencia", limitar(rusticidade / 3 + 8)),
                barra("Outros", 9)
        ));
        resposta.put("crescimentoPeso", curvaCrescimento(ganhoPesoEstimado));
        return resposta;
    }

    public List<Predicao> historico() {
        return predicaoRepository.findTop20ByOrderByCriadoEmDesc();
    }

    private Map<String, Object> barra(String label, int valor) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("label", label);
        item.put("valor", valor);
        return item;
    }

    private List<Map<String, Object>> curvaCrescimento(double ganhoDiario) {
        List<Map<String, Object>> pontos = new ArrayList<>();
        double peso = 38;
        for (int mes = 0; mes <= 12; mes += 2) {
            Map<String, Object> ponto = new LinkedHashMap<>();
            ponto.put("mes", mes);
            ponto.put("peso", arredondar(peso, 0));
            pontos.add(ponto);
            peso += ganhoDiario * 60;
        }
        return pontos;
    }

    private String montarRecomendacao(Animal vaca, Semen semen, boolean corte, boolean leiteira, int score) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cruzamento de ").append(vaca.getNome())
                .append(" (").append(vaca.getRaca()).append(") com o semen ")
                .append(semen.getCodigo()).append(" (").append(semen.getRaca()).append("). ");
        if (score >= 80) {
            sb.append("Combinacao de alto potencial: femeas com boa projecao leiteira e machos aproveitaveis para corte. ");
        } else if (corte) {
            sb.append("Indicado quando o foco e ganho de peso e rusticidade dos machos. ");
        } else {
            sb.append("Resultado equilibrado, sem destaque expressivo em corte ou leite. ");
        }
        if (leiteira) {
            sb.append("A matriz mantem aptidao leiteira no plantel.");
        } else {
            sb.append("Avaliar reposicao de matrizes leiteiras no plantel.");
        }
        return sb.toString();
    }

    private boolean ehRacaDeCorte(String raca) {
        if (raca == null) {
            return false;
        }
        String r = raca.toLowerCase();
        return r.contains("angus") || r.contains("nelore") || r.contains("brahman");
    }

    private boolean ehRacaLeiteira(String raca) {
        if (raca == null) {
            return false;
        }
        String r = raca.toLowerCase();
        return r.contains("holstein") || r.contains("jersey") || r.contains("gir") || r.contains("girolando");
    }

    private int limitar(int valor) {
        if (valor < 5) {
            return 5;
        }
        if (valor > 99) {
            return 99;
        }
        return valor;
    }

    private double arredondar(double valor, int casas) {
        double fator = Math.pow(10, casas);
        return Math.round(valor * fator) / fator;
    }
}

