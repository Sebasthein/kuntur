package com.dev.kuntur.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dev.kuntur.DTO.RegistroRequest;
import com.dev.kuntur.model.Usuario;
import com.dev.kuntur.repository.UsuarioRepository;

@Controller
public class ViewController {
	
	   @Autowired
	    private UsuarioRepository usuarioRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre de la plantilla Thymeleaf
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("registro", new RegistroRequest());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(RegistroRequest request, Model model) {
    	 // Validar confirmación de contraseña
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        // Validar si el correo ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado.");
            model.addAttribute("registroRequest", request);
            return "registro";
        }

 

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setDescripcion(request.getDescripcion());
        usuario.setRol(Usuario.Rol.valueOf(request.getRol())); // CLIENTE o PROVEEDOR
        usuario.setFechaRegistro(LocalDateTime.now());

        usuarioRepository.save(usuario);

        // Redirigir al login con mensaje de éxito
        return "redirect:/login?registroExitoso";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Optional<Usuario> optionalUser = usuarioRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                model.addAttribute("user", optionalUser.get());
            } else {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }

        return "dashboard";
    }
}
