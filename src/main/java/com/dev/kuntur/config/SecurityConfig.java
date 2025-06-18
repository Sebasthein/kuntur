package com.dev.kuntur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dev.kuntur.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	  private final UserDetailsServiceImpl userDetailsService;

	    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(
	            AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/login","/service/create", "/registro", "/api/**", "/css/**", "/js/**", "/images/**", "/plugins/**").permitAll()
	                .requestMatchers("/admin/**").hasRole("ADMIN")
	                .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	                .loginPage("/login") // Página personalizada de login
	                .loginProcessingUrl("/login") // URL al que apunta tu formulario
	                .usernameParameter("username") // Nombre del campo de usuario en el formulario
	                .passwordParameter("password") // Nombre del campo de contraseña
	                .defaultSuccessUrl("/dashboard", true) // Redirigir tras login exitoso
	                .failureUrl("/login?error=true") // Redirigir si falla el login
	                .permitAll()
	            )
	            .exceptionHandling(exception -> exception
	                .accessDeniedPage("/acceso-denegado") // Manejo de acceso denegado
	            )
	            .logout(logout -> logout
	                .logoutUrl("/logout") // URL para cerrar sesión
	                .logoutSuccessUrl("/login?logout=true") // Redirigir tras logout
	                .permitAll()
	            );

	        return http.build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); // Codificador seguro de contraseñas
	    }
}
