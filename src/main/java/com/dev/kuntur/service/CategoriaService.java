package com.dev.kuntur.service;

import com.dev.kuntur.model.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> obtenerTodas();
    Categoria obtenerPorId(Long id);
    Categoria crear(Categoria categoria);
    Categoria actualizar(Long id, Categoria categoria);
    void eliminar(Long id);
}
