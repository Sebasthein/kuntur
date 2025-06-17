package com.dev.kuntur.serviceImpl;

import com.dev.kuntur.model.Calificacion;
import com.dev.kuntur.repository.CalificacionRepository;
import com.dev.kuntur.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Override
    public List<Calificacion> obtenerTodas() {
        return calificacionRepository.findAll();
    }

    @Override
    public Calificacion obtenerPorId(Long id) {
        return calificacionRepository.findById(id).orElse(null);
    }

    @Override
    public Calificacion crear(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    @Override
    public Calificacion actualizar(Long id, Calificacion calificacion) {
        if (calificacionRepository.existsById(id)) {
            calificacion.setId(id);
            return calificacionRepository.save(calificacion);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        calificacionRepository.deleteById(id);
    }
}
