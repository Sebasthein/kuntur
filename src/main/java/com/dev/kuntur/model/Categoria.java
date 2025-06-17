package com.dev.kuntur.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Servicio> servicios = new ArrayList<>();

    // Getters, Setters, Constructores...
}
