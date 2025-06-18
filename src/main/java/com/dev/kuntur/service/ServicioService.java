package com.dev.kuntur.service;

import com.dev.kuntur.model.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioService {
    List<Servicio> obtenerTodos();
    Optional<Servicio> obtenerPorId(Long id);
    Servicio crear(Servicio servicio);
    Servicio actualizar(Long id, Servicio servicio);
    void eliminar(Long id);

}
