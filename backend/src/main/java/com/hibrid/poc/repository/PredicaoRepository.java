package com.hibrid.poc.repository;

import com.hibrid.poc.model.Predicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PredicaoRepository extends JpaRepository<Predicao, Long> {
    List<Predicao> findTop20ByOrderByCriadoEmDesc();
}

