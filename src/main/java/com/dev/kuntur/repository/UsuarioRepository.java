package com.dev.kuntur.repository;

import com.dev.kuntur.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Buscar usuarios por rol (proveedores, clientes, admins)
    List<Usuario> findByRol(Usuario.Rol rol);

    // Buscar usuarios por nombre (búsqueda)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    // Verificar si existe un email (para registro)
    boolean existsByEmail(String email);
}