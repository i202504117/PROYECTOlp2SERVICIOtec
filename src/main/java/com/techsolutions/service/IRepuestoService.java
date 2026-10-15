package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import com.techsolutions.modelo.Repuesto;

public interface IRepuestoService {
	public List<Repuesto> getAllRepuesto();

	public Repuesto createRepuesto(Repuesto repuesto);

	public void deleteRepuesto(int id);

	public Optional<Repuesto> buscarRepuesto(int id);
	
	public List<Repuesto> buscarPorNombre(String nombre);
}
