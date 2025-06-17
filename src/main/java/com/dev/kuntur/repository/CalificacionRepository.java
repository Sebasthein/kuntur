package com.dev.kuntur.repository;


import com.dev.kuntur.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    // Calificaciones de un servicio específico
    List<Calificacion> findByServicioId(Long servicioId);

    // Calificaciones hechas por un usuario
    List<Calificacion> findByClienteId(Long clienteId);

    // Calificaciones recibidas por un proveedor
    @Query("SELECT c FROM Calificacion c JOIN c.servicio s WHERE s.proveedor.id = :proveedorId")
    List<Calificacion> findByProveedorId(@Param("proveedorId") Long proveedorId);

    // Promedio de calificaciones de un servicio
    @Query("SELECT COALESCE(AVG(c.puntuacion), 0) FROM Calificacion c WHERE c.servicio.id = :servicioId")
    Double calcularPromedioPorServicio(@Param("servicioId") Long servicioId);

    // Verificar si un usuario ya calificó un servicio
    boolean existsByServicioIdAndClienteId(Long servicioId, Long clienteId);
}
