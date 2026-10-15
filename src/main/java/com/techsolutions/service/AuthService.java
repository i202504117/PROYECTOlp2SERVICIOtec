package com.techsolutions.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsolutions.modelo.Usuario;
import com.techsolutions.repository.IUsuarioRepository;

@Service
public class AuthService {

	@Autowired
	private IUsuarioRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public String saveUser(Usuario usuario) {

		if (repository.findByEmail(usuario.getEmail()).isPresent()) {
			throw new IllegalStateException("El correo " + usuario.getEmail() + " ya está registrado.");
		}

		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		repository.save(usuario);

		return "Usuario registrado";
	}


	@Transactional(readOnly = true)
	public boolean validarCredenciales(String email, String password) {
		Optional<Usuario> usuarioOpt = repository.findByEmail(email);

		if (usuarioOpt.isEmpty()) {
			return false;
		}
		return passwordEncoder.matches(password, usuarioOpt.get().getPassword());
	}

	@Transactional(readOnly = true)
	public String obtenerRol(String email) {
		return repository.findByEmail(email).map(u -> u.getRol().getNombre()).orElse(null);
	}
}