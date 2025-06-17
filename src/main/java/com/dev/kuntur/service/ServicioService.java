package com.dev.kuntur.service;

import com.dev.kuntur.model.Servicio;

import java.util.List;

public interface ServicioService {
    List<Servicio> obtenerTodos();
    Servicio obtenerPorId(Long id);
    Servicio crear(Servicio servicio);
    Servicio actualizar(Long id, Servicio servicio);
    void eliminar(Long id);
}
