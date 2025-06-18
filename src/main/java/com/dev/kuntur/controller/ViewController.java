package com.dev.kuntur.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.kuntur.DTO.RegistroRequest;
import com.dev.kuntur.model.Servicio;
import com.dev.kuntur.model.Usuario;
import com.dev.kuntur.repository.ServicioRepository;
import com.dev.kuntur.repository.UsuarioRepository;
import com.dev.kuntur.service.UsuarioService;

@Controller
public class ViewController {
	
	   @Autowired
	    private UsuarioRepository usuarioRepository;
	   
	   @Autowired
	   private UsuarioService usuarioService;

	   @Autowired
	    private ServicioRepository servicioRepository;
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
    
    @GetMapping("/profile/edit")
    public String mostrarEditarPerfil(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "edit-profile";
    }
    @PostMapping("/profile/edit")
    public String actualizarPerfil(Usuario usuario, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        // Obtener el usuario actual desde la base de datos
        Optional<Usuario> optionalUser = usuarioRepository.findByEmail(principal.getName());

        if (optionalUser.isPresent()) {
            Usuario userDB = optionalUser.get();

            // Actualizar campos permitidos
            userDB.setNombre(usuario.getNombre());
            userDB.setTelefono(usuario.getTelefono());
            userDB.setDireccion(usuario.getDireccion());
            userDB.setDescripcion(usuario.getDescripcion());

            usuarioRepository.save(userDB);
        }

        return "redirect:/dashboard";
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
    
    @GetMapping("/services/create")
    public String showCreateServiceForm() {
        return "create-service";
    }

    @PostMapping("/services/create")
    @ResponseBody
    public ResponseEntity<?> createService(
            @RequestParam String titulo,
            @RequestParam double precio,
            @RequestParam String description,
            Principal principal) {

        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new IllegalArgumentException("El título del servicio es requerido");
            }

            if (precio <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a cero");
            }

            // Aquí deberías obtener al usuario actual
            Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            Servicio nuevoServicio = new Servicio();
            nuevoServicio.setTitulo(titulo);
            nuevoServicio.setPrecio(precio);
            nuevoServicio.setDescripcion(description);
            nuevoServicio.setFechaCreacion(LocalDateTime.now());
            nuevoServicio.setProveedor(usuario); // Asociar servicio al proveedor

            servicioRepository.save(nuevoServicio);

            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "Servicio creado exitosamente",
                    "redirect", "/dashboard/services"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
    
    
}
