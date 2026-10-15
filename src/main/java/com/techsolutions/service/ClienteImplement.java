package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsolutions.modelo.Cliente;
import com.techsolutions.repository.IClienteRepository;

@Service
@Transactional
public class ClienteImplement implements IClienteService {

	@Autowired
	private IClienteRepository clienteRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> getAllClientes() {
		return clienteRepo.findByActivoTrue();
	}

	@Override
	public void saveCliente(Cliente cliente) {
		Cliente porDni = clienteRepo.findByDni(cliente.getDni());
		if (porDni != null && cliente.getIdCliente() != null && !porDni.getIdCliente().equals(cliente.getIdCliente())) {
			throw new RuntimeException("El DNI " + cliente.getDni() + " ya pertenece a otro cliente.");
		}

		if (porDni != null && cliente.getIdCliente() == null && Boolean.TRUE.equals(porDni.getActivo())) {
			throw new RuntimeException("El DNI " + cliente.getDni() + " ya se encuentra registrado en TechSolutions.");
		}

		Cliente destino = (cliente.getIdCliente() != null) ? clienteRepo.findById(cliente.getIdCliente()).orElse(null)
				: porDni;

		if (destino == null) {
			cliente.setActivo(true);
			clienteRepo.save(cliente);
			return;
		}

		destino.setDni(cliente.getDni());
		destino.setNombre(cliente.getNombre());
		destino.setApellido(cliente.getApellido());
		destino.setTelefono(cliente.getTelefono());
		destino.setDireccion(cliente.getDireccion());
		destino.setActivo(true);

		clienteRepo.save(destino);
	}

	@Override
	public void deleteCliente(Integer id) {
		clienteRepo.findById(id).ifPresent(c -> {
			c.setActivo(false);
			clienteRepo.save(c);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> buscarClientePorId(Integer id) {
		return clienteRepo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente buscarPorDni(String dni) {
		return clienteRepo.findByDni(dni);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorDniContaining(String dni) {
		return clienteRepo.findByDniContainingAndActivoTrue(dni);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNombre(String nombre) {
		return clienteRepo.findByNombreContainingIgnoreCaseAndActivoTrue(nombre);
	}
}