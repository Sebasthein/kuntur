package com.dev.kuntur.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    private String telefono;
    private String direccion;
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-servicios") 
    private List<Servicio> servicios = new ArrayList<>();


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Calificacion> calificaciones = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public enum Rol {
        CLIENTE, PROVEEDOR, ADMIN
    }
}
