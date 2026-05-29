package com.hibrid.poc.repository;

import com.hibrid.poc.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByTipo(String tipo);
    long countByTipo(String tipo);
    long countByTipoAndProduzindoLeiteTrue(String tipo);
}

