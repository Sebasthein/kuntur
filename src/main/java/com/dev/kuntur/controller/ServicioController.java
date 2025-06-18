package com.dev.kuntur.controller;

import com.dev.kuntur.dto.ServicioDTO;
import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.repository.ServicioRepository;
import com.dev.kuntur.service.ServicioService;
import com.dev.kuntur.serviceImpl.ServicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        List<Servicio> servicios = servicioService.obtenerTodos();
        List<ServicioDTO> dtos = servicios.stream()
                .map(ServicioDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public Optional<Servicio> getServicioById(@PathVariable Long id) {
        return servicioRepository.findById(id);
    }

    @PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @PutMapping("/{id}")
    public Servicio updateServicio(@PathVariable Long id, @RequestBody Servicio servicio) {
        servicio.setId(id);
        return servicioRepository.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void deleteServicio(@PathVariable Long id) {
        servicioRepository.deleteById(id);
    }
    
    
    @GetMapping("/categoria/{categoria}")
    public List<Servicio> getServicioByCategoria(@PathVariable String categoria) {
        return servicioRepository.findByNombreCategoria(categoria);
    }
    
}

