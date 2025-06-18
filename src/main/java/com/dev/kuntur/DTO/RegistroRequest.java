package com.dev.kuntur.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {
	 @NotBlank(message = "El nombre es obligatorio")
	    private String nombre;
	    
	    @NotBlank(message = "El email es obligatorio")
	    @Email(message = "Debe ser un email válido")
	    private String email;
	    
	    @NotBlank(message = "La contraseña es obligatoria")
	    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	    private String password;
	    
	    @NotBlank(message = "Debe confirmar la contraseña")
	    private String confirmPassword;
	    
	    @NotBlank(message = "El teléfono es obligatorio")
	    private String telefono;
	    
	    @NotBlank(message = "La dirección es obligatoria")
	    private String direccion;
	    
	    private String descripcion;
	    
	    @NotBlank(message = "Debe seleccionar un tipo de usuario")
	    private String rol;
}
