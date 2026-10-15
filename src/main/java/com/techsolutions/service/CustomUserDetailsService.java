package com.techsolutions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Usuario;
import com.techsolutions.repository.IUsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

		String nombre = usuario.getRol().getNombre().toUpperCase();
		String authority = nombre.startsWith("ROLE_") ? nombre : "ROLE_" + nombre;

		return new User(usuario.getEmail(), usuario.getPassword(), List.of(new SimpleGrantedAuthority(authority)));
	}
}