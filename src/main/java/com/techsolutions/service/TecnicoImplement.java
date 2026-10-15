package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Tecnico;
import com.techsolutions.repository.ITecnicoRepository;

@Service
public class TecnicoImplement implements ITecnicoSevice {

	@Autowired
	private ITecnicoRepository tecRepo;

	@Override
	public List<Tecnico> getAllTecnico() {
		return tecRepo.findAll();
	}

	@Override
	public Tecnico createTecnico(Tecnico tecnico) {
		return tecRepo.save(tecnico);
	}

	@Override
	public void deleteTecnico(int id) {
		try {
			tecRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException(
					"No se puede eliminar el técnico porque tiene órdenes de servicio asociadas.");
		}
	}

	@Override
	public Optional<Tecnico> buscarTecnico(int id) {
		return tecRepo.findById(id);
	}

	@Override
	public List<Tecnico> buscarNombre(String nombre) {
		return tecRepo.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	public Tecnico editarTecnico(Tecnico tecnico) {
		return tecRepo.save(tecnico);
	}

	@Override
	public Tecnico findByNombre(String nombre) {
		return tecRepo.findFirstByNombre(nombre);
	}
}