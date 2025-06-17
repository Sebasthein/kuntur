package com.dev.kuntur.serviceImpl;

import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.repository.ServicioRepository;
import com.dev.kuntur.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public Servicio crear(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio actualizar(Long id, Servicio servicio) {
        if (servicioRepository.existsById(id)) {
            servicio.setId(id);
            return servicioRepository.save(servicio);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
}

