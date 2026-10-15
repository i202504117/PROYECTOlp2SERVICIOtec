package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Proveedor;
import com.techsolutions.repository.IProveedorRepository;

@Service
public class ProveedorImplement implements IProveedorService {

	@Autowired
	private IProveedorRepository repo;

	@Override
	public List<Proveedor> getAllProveedor() {
		return repo.findAll();
	}

	@Override
	public Proveedor createProveedor(Proveedor proveedor) {
		return repo.save(proveedor);
	}

	@Override
	public void deleteProveedor(int id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException("No se puede eliminar el proveedor porque tiene repuestos asociados.");
		}
	}

	@Override
	public Optional<Proveedor> buscarProveedor(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Proveedor> buscarProveedor(String nombre) {
		return repo.findByRazonsocialContainingIgnoreCase(nombre);
	}

	@Override
	public List<Proveedor> listarTodos() {
		return repo.findAll();
	}
}