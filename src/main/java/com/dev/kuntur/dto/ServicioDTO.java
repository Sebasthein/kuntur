package com.dev.kuntur.dto;

import com.dev.kuntur.model.Calificacion;
import com.dev.kuntur.model.Categoria;
import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.model.Usuario;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private LocalDateTime fechaCreacion;
    private CategoriaResumenDTO categoria;
    private ProveedorResumenDTO proveedor;
    private List<CalificacionResumenDTO> calificaciones;
    private Double promedioCalificaciones;

    // Constructor que convierte entidad a DTO
    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.titulo = servicio.getTitulo();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecio();
        this.fechaCreacion = servicio.getFechaCreacion();

        // Manejo de relaciones
        this.categoria = servicio.getCategoria() != null ?
                new CategoriaResumenDTO(servicio.getCategoria()) : null;

        this.proveedor = servicio.getProveedor() != null ?
                new ProveedorResumenDTO(servicio.getProveedor()) : null;

        this.calificaciones = servicio.getCalificaciones() != null ?
                servicio.getCalificaciones().stream()
                        .map(CalificacionResumenDTO::new)
                        .collect(Collectors.toList()) : Collections.emptyList();

        // Cálculo de promedio de calificaciones
        this.promedioCalificaciones = servicio.getCalificaciones() != null ?
                servicio.getCalificaciones().stream()
                        .mapToInt(Calificacion::getPuntuacion)
                        .average()
                        .orElse(0.0) : 0.0;
    }

    // Clases DTO internas para relaciones
    public static class CategoriaResumenDTO {
        private Long id;
        private String nombre;

        public CategoriaResumenDTO(Categoria categoria) {
            this.id = categoria.getId();
            this.nombre = categoria.getNombre();
        }

        // Getters
        public Long getId() { return id; }
        public String getNombre() { return nombre; }
    }

    public static class ProveedorResumenDTO {
        private Long id;
        private String nombre;
        private String telefono;
        private String email;

        public ProveedorResumenDTO(Usuario usuario) {
            this.id = usuario.getId();
            this.nombre = usuario.getNombre();
            this.telefono = usuario.getTelefono();
            this.email = usuario.getEmail();
        }

        // Getters
        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getTelefono() { return telefono; }
        public String getEmail() { return email; }
    }

    public static class CalificacionResumenDTO {
        private Integer puntuacion;
        private String comentario;
        private LocalDateTime fecha;
        private String clienteNombre;

        public CalificacionResumenDTO(Calificacion calificacion) {
            this.puntuacion = calificacion.getPuntuacion();
            this.comentario = calificacion.getComentario();
            this.fecha = calificacion.getFechaCalificacion();
            this.clienteNombre = calificacion.getCliente().getNombre();
        }

        // Getters
        public Integer getPuntuacion() { return puntuacion; }
        public String getComentario() { return comentario; }
        public LocalDateTime getFecha() { return fecha; }
        public String getClienteNombre() { return clienteNombre; }
    }

    // Getters para los campos principales
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Double getPrecio() { return precio; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public CategoriaResumenDTO getCategoria() { return categoria; }
    public ProveedorResumenDTO getProveedor() { return proveedor; }
    public List<CalificacionResumenDTO> getCalificaciones() { return calificaciones; }
    public Double getPromedioCalificaciones() { return promedioCalificaciones; }
}