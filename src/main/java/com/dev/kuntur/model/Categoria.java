package com.dev.kuntur.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    /*@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @Basic(fetch = FetchType.EAGER)
    private List<Servicio> servicios = new ArrayList<>();
     */

    
    public void setId(Long id) {
        this.id = id;
    }

}
