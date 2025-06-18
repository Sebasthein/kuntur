package com.dev.kuntur.dto;

import com.dev.kuntur.model.Calificacion;
import com.dev.kuntur.model.Categoria;
import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Double precio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime fechaCreacion;
    private CategoriaResumenDTO categoria;
    private ProveedorResumenDTO proveedor;
    private List<CalificacionResumenDTO> calificaciones;
    private Double promedioCalificaciones;



    // Constructor para convertir entidad a DTO
    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.titulo = servicio.getTitulo();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecio();
        this.fechaCreacion = servicio.getFechaCreacion();

        if (servicio.getCategoria() != null) {
            this.categoria = new CategoriaResumenDTO(servicio.getCategoria());
        }

        if (servicio.getProveedor() != null) {
            this.proveedor = new ProveedorResumenDTO(servicio.getProveedor());
        }

        if (servicio.getCalificaciones() != null) {
            this.calificaciones = servicio.getCalificaciones().stream()
                    .map(CalificacionResumenDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters y Setters REQUERIDOS para todas las propiedades
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public CategoriaResumenDTO getCategoria() { return categoria; }
    public void setCategoria(CategoriaResumenDTO categoria) { this.categoria = categoria; }

    public ProveedorResumenDTO getProveedor() { return proveedor; }
    public void setProveedor(ProveedorResumenDTO proveedor) { this.proveedor = proveedor; }

    public List<CalificacionResumenDTO> getCalificaciones() { return calificaciones; }
    public void setCalificaciones(List<CalificacionResumenDTO> calificaciones) { this.calificaciones = calificaciones; }

    public Double getPromedioCalificaciones() { return promedioCalificaciones; }
    public void setPromedioCalificaciones(Double promedioCalificaciones) { this.promedioCalificaciones = promedioCalificaciones; }

    // Clases internas también deben tener constructores vacíos y getters/setters
    public static class CategoriaResumenDTO {
        private Long id;
        private String nombre;

        public CategoriaResumenDTO() {}  // Constructor vacío

        public CategoriaResumenDTO(Categoria categoria) {
            this.id = categoria.getId();
            this.nombre = categoria.getNombre();
        }

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }

    public static class ProveedorResumenDTO {
        private Long id;
        private String nombre;
        private String telefono;
        private String email;

        public ProveedorResumenDTO() {}  // Constructor vacío

        public ProveedorResumenDTO(Usuario usuario) {
            this.id = usuario.getId();
            this.nombre = usuario.getNombre();
            this.telefono = usuario.getTelefono();
            this.email = usuario.getEmail();
        }

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class CalificacionResumenDTO {
        private Integer puntuacion;
        private String comentario;
        private LocalDateTime fecha;
        private String clienteNombre;

        public CalificacionResumenDTO() {}  // Constructor vacío

        public CalificacionResumenDTO(Calificacion calificacion) {
            this.puntuacion = calificacion.getPuntuacion();
            this.comentario = calificacion.getComentario();
            this.fecha = calificacion.getFechaCalificacion();
            this.clienteNombre = calificacion.getCliente().getNombre();
        }

        // Getters y Setters
        public Integer getPuntuacion() { return puntuacion; }
        public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

        public String getComentario() { return comentario; }
        public void setComentario(String comentario) { this.comentario = comentario; }

        public LocalDateTime getFecha() { return fecha; }
        public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

        public String getClienteNombre() { return clienteNombre; }
        public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    }
}