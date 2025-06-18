package com.dev.kuntur.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.kuntur.model.Usuario;
import com.dev.kuntur.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	 @Autowired
	    private UsuarioRepository usuarioRepository;

	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     Usuario usuario = usuarioRepository.findByEmail(username)
	         .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

	     return new org.springframework.security.core.userdetails.User(
	         usuario.getEmail(),
	         usuario.getPassword(),
	         new ArrayList<>()
	     );
	 }

}
