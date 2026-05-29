package com.hibrid.poc.controller;

import com.hibrid.poc.model.Animal;
import com.hibrid.poc.repository.AnimalRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final AnimalRepository animalRepository;

    public DashboardController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping
    public Map<String, Object> resumo() {
        List<Animal> vacas = animalRepository.findByTipo("VACA");

        double leiteDiario = vacas.stream()
                .filter(a -> Boolean.TRUE.equals(a.getProduzindoLeite()))
                .mapToDouble(a -> a.getProducaoLeiteDiaria() == null ? 0 : a.getProducaoLeiteDiaria())
                .sum();

        double leiteMensal = vacas.stream()
                .filter(a -> Boolean.TRUE.equals(a.getProduzindoLeite()))
                .mapToDouble(a -> a.getProducaoLeiteMensal() == null ? 0 : a.getProducaoLeiteMensal())
                .sum();

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("totalVacas", animalRepository.countByTipo("VACA"));
        resumo.put("vacasProduzindo", animalRepository.countByTipoAndProduzindoLeiteTrue("VACA"));
        resumo.put("totalBois", animalRepository.countByTipo("BOI"));
        resumo.put("totalBezerros", animalRepository.countByTipo("BEZERRO"));
        resumo.put("leiteDiario", leiteDiario);
        resumo.put("leiteMensal", leiteMensal);
        return resumo;
    }
}

