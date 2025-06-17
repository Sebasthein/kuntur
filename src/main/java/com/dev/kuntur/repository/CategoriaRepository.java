package com.dev.kuntur.repository;

import com.dev.kuntur.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Buscar categoría por nombre exacto
    Optional<Categoria> findByNombre(String nombre);

    // Buscar categorías que contengan un término (búsqueda)
    List<Categoria> findByNombreContainingIgnoreCase(String term);
}