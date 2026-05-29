package com.hibrid.poc.controller;

import com.hibrid.poc.model.Predicao;
import com.hibrid.poc.service.PredicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/predicao")
public class PredicaoController {

    private final PredicaoService service;

    public PredicaoController(PredicaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> simular(@RequestBody Map<String, Long> corpo) {
        Long vacaId = corpo.get("vacaId");
        Long semenId = corpo.get("semenId");
        if (vacaId == null || semenId == null) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Informe vacaId e semenId"));
        }
        try {
            return ResponseEntity.ok(service.simular(vacaId, semenId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/historico")
    public List<Predicao> historico() {
        return service.historico();
    }
}

