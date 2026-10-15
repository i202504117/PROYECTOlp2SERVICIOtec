package com.techsolutions.service;

import java.util.List;

import com.techsolutions.modelo.Usuario;

public interface IUsuarioService {
	 List<Usuario> listarUsuarios();

	    Usuario guardarUsuario(Usuario usuario);
}
