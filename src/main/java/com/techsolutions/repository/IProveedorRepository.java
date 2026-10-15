package com.techsolutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.Proveedor;

public interface IProveedorRepository extends JpaRepository<Proveedor, Integer> {
	List<Proveedor> findByRazonsocialContainingIgnoreCase(String nombre);
}

