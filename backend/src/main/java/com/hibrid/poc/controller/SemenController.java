package com.hibrid.poc.controller;

import com.hibrid.poc.model.Semen;
import com.hibrid.poc.repository.SemenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/semen")
public class SemenController {

    private final SemenRepository repository;

    public SemenController(SemenRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Semen> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Semen criar(@RequestBody Semen semen) {
        semen.setId(null);
        return repository.save(semen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

