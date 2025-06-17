package com.dev.kuntur.service;

import com.dev.kuntur.model.Calificacion;

import java.util.List;

public interface CalificacionService {
    List<Calificacion> obtenerTodas();
    Calificacion obtenerPorId(Long id);
    Calificacion crear(Calificacion calificacion);
    Calificacion actualizar(Long id, Calificacion calificacion);
    void eliminar(Long id);
}
