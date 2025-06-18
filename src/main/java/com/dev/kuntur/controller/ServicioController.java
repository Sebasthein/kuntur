package com.dev.kuntur.controller;

import com.dev.kuntur.dto.ServicioDTO;
import com.dev.kuntur.model.Categoria;
import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.model.Usuario;
import com.dev.kuntur.repository.CategoriaRepository;
import com.dev.kuntur.repository.ServicioRepository;
import com.dev.kuntur.repository.UsuarioRepository;
import com.dev.kuntur.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioRepository servicioRepository;
    private final ServicioService servicioService;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ServicioController(ServicioRepository servicioRepository,
                              ServicioService servicioService,
                              CategoriaRepository categoriaRepository,
                              UsuarioRepository usuarioRepository) {
        this.servicioRepository = servicioRepository;
        this.servicioService = servicioService;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        List<Servicio> servicios = servicioService.obtenerTodos();
        List<ServicioDTO> dtos = servicios.stream()
                .map(ServicioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Long id) {
        return servicioService.obtenerPorId(id)
                .map(ServicioDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicioDTO> createServicio(@RequestBody ServicioDTO servicioDTO) {
        // Convertir DTO a entidad
        Servicio servicio = new Servicio();
        servicio.setTitulo(servicioDTO.getTitulo());
        servicio.setDescripcion(servicioDTO.getDescripcion());
        servicio.setPrecio(servicioDTO.getPrecio());
        servicio.setFechaCreacion(LocalDateTime.now()); // Usar fecha actual

        // Manejar relaciones
        if (servicioDTO.getCategoria() != null && servicioDTO.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(servicioDTO.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            servicio.setCategoria(categoria);
        }

        if (servicioDTO.getProveedor() != null && servicioDTO.getProveedor().getId() != null) {
            Usuario proveedor = usuarioRepository.findById(servicioDTO.getProveedor().getId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            servicio.setProveedor(proveedor);
        }

        Servicio savedServicio = servicioRepository.save(servicio);
        return ResponseEntity.ok(new ServicioDTO(savedServicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> updateServicio(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO) {
        return servicioRepository.findById(id)
                .map(existingServicio -> {
                    existingServicio.setTitulo(servicioDTO.getTitulo());
                    existingServicio.setDescripcion(servicioDTO.getDescripcion());
                    existingServicio.setPrecio(servicioDTO.getPrecio());

                    // Actualizar categoría si se proporciona
                    if (servicioDTO.getCategoria() != null && servicioDTO.getCategoria().getId() != null) {
                        Categoria categoria = categoriaRepository.findById(servicioDTO.getCategoria().getId())
                                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                        existingServicio.setCategoria(categoria);
                    }

                    // Actualizar proveedor si se proporciona
                    if (servicioDTO.getProveedor() != null && servicioDTO.getProveedor().getId() != null) {
                        Usuario proveedor = usuarioRepository.findById(servicioDTO.getProveedor().getId())
                                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
                        existingServicio.setProveedor(proveedor);
                    }

                    Servicio updatedServicio = servicioRepository.save(existingServicio);
                    return ResponseEntity.ok(new ServicioDTO(updatedServicio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categoria/{categoriaNombre}")
    public ResponseEntity<List<ServicioDTO>> getServicioByCategoria(@PathVariable String categoriaNombre) {
        List<Servicio> servicios = servicioRepository.findByNombreCategoria(categoriaNombre);
        List<ServicioDTO> dtos = servicios.stream()
                .map(ServicioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}