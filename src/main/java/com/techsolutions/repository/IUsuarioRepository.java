
package com.techsolutions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario>findByEmail(String email);

}