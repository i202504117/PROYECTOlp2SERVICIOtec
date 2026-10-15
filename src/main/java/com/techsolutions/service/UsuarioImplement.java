package com.techsolutions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Usuario;
import com.techsolutions.repository.IUsuarioRepository;

@Service
public class UsuarioImplement implements IUsuarioService {

	@Autowired
	private IUsuarioRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Usuario> listarUsuarios() {
		return repo.findAll();
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		String clave = usuario.getPassword();
		if (clave != null && !clave.startsWith("$2")) {
			usuario.setPassword(passwordEncoder.encode(clave));
		}
		return repo.save(usuario);
	}
}