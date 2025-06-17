package com.dev.kuntur.controller;

import com.dev.kuntur.model.Calificacion;
import com.dev.kuntur.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@CrossOrigin(origins = "*")
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @GetMapping
    public List<Calificacion> getAll() {
        return calificacionRepository.findAll();
    }

    @GetMapping("/servicio/{servicioId}")
    public List<Calificacion> getByServicio(@PathVariable Long servicioId) {
        return calificacionRepository.findByServicioId(servicioId);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Calificacion> getByCliente(@PathVariable Long clienteId) {
        return calificacionRepository.findByClienteId(clienteId);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<Calificacion> getByProveedor(@PathVariable Long proveedorId) {
        return calificacionRepository.findByProveedorId(proveedorId);
    }

    @GetMapping("/promedio/{servicioId}")
    public Double getPromedioPorServicio(@PathVariable Long servicioId) {
        return calificacionRepository.calcularPromedioPorServicio(servicioId);
    }

    @PostMapping
    public Calificacion create(@RequestBody Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }
}
