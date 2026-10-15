package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.techsolutions.repository.IRepuestoRepository;

import com.techsolutions.modelo.Repuesto;

@Service
public class RepuestoImplement implements IRepuestoService {

	@Autowired
	private IRepuestoRepository repo;

	@Override
	public List<Repuesto> getAllRepuesto() {
		return repo.findAll();
	}

	@Override
	public Repuesto createRepuesto(Repuesto repuesto) {
		return repo.save(repuesto);
	}

	@Override
	public void deleteRepuesto(int id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException(
					"No se puede eliminar el repuesto porque ya fue usado en órdenes de servicio.");
		}
	}

	@Override
	public Optional<Repuesto> buscarRepuesto(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Repuesto> buscarPorNombre(String nombre) {
		return repo.findByNombreContaining(patron(nombre));
	}

	private String patron(String texto) {
		if (texto == null) {
			return "%";
		}
		if (texto.contains("%")) {
			return texto;
		}
		String limpio = texto.trim()
				.replace("\\", "\\\\")
				.replace("_", "\\_");
		return "%" + limpio + "%";
	}
}