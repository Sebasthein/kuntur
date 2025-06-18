package com.dev.kuntur.repository;

import com.dev.kuntur.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // Buscar servicios por categoría
    List<Servicio> findByCategoriaId(Long categoriaId);

    // Buscar servicios por proveedor
    List<Servicio> findByProveedorId(Long proveedorId);

    // Búsqueda avanzada: por palabra clave en título/descripción
    @Query("SELECT s FROM Servicio s WHERE " +
            "LOWER(s.titulo) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(s.descripcion) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Servicio> buscarPorPalabraClave(@Param("keyword") String keyword);

    // Servicios por rango de precios
    List<Servicio> findByPrecioBetween(Double precioMin, Double precioMax);

    // Servicios mejor calificados (promedio)
    /*@Query("SELECT s FROM Servicio s LEFT JOIN s.calificaciones c " +
            "GROUP BY s.id ORDER BY COALESCE(AVG(c.puntuacion), 0) DESC")
    List<Servicio> findTopRated();

     */
<<<<<<< Updated upstream
    @Query("SELECT s FROM Servicio s " +
    	       "WHERE s.categoria.id IN (SELECT c.id FROM Categoria c WHERE LOWER(c.nombre) = LOWER(:categoria))")
    	List<Servicio> findByNombreCategoria(@Param("categoria") String categoria);


=======

    @Query("SELECT s FROM Servicio s " +
            "LEFT JOIN FETCH s.categoria " +
            "LEFT JOIN FETCH s.proveedor " +
            "LEFT JOIN FETCH s.calificaciones")
    List<Servicio> findAllWithRelations();

    @Query("SELECT s FROM Servicio s " +
            "LEFT JOIN FETCH s.categoria " +
            "LEFT JOIN FETCH s.proveedor " +
            "LEFT JOIN FETCH s.calificaciones c " +
            "LEFT JOIN FETCH c.cliente " +
            "WHERE s.id = :id")
    Optional<Servicio> findByIdWithRelations(@Param("id") Long id);
>>>>>>> Stashed changes
}
