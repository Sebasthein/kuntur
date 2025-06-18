package com.dev.kuntur.serviceImpl;

import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.repository.ServicioRepository;
import com.dev.kuntur.service.ServicioService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAllWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> obtenerPorId(Long id) {
        return servicioRepository.findByIdWithRelations(id);
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

    // Usa la anotación de Spring con readOnly
    @Transactional(readOnly = true)
    public List<Servicio> findAll() {
        return servicioRepository.findAllWithRelations();
    }

    @Transactional(readOnly = true)
    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findByIdWithRelations(id);
    }
}

