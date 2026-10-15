package com.techsolutions.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsolutions.dto.ItemOrden;
import com.techsolutions.modelo.DetalleServicio;
import com.techsolutions.modelo.EstadoServicio;
import com.techsolutions.modelo.OrdenServicio;
import com.techsolutions.modelo.Repuesto;
import com.techsolutions.repository.IEstadoServicioRepository;
import com.techsolutions.repository.IOrdenServicioRepository;
import com.techsolutions.repository.IRepuestoRepository;

@Service
@Transactional
public class OrdenServicioImplement implements IOrdenServicioService {

	@Autowired
	private IOrdenServicioRepository ordenRepo;

	@Autowired
	private IRepuestoRepository repuestoRepo;

	@Autowired
	private IEstadoServicioRepository estadoRepo;

	@Override
	@Transactional(readOnly = true)
	public List<OrdenServicio> getAllOrdenes() {
		return ordenRepo.listarConDetalle();
	}

	@Override
	public OrdenServicio saveOrden(OrdenServicio orden) {
		if (orden.getEstado() == null || orden.getEstado().getId() == null) {
			orden.setEstado(estadoPendiente());
		}

		if (orden.getIdorden() == null) {
			return ordenRepo.save(orden);
		}

		OrdenServicio existente = ordenRepo.findById(orden.getIdorden())
				.orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + orden.getIdorden()));

		existente.setFecha(orden.getFecha());
		existente.setProblema(orden.getProblema());
		existente.setCostoManoObra(orden.getCostoManoObra());
		existente.setCliente(orden.getCliente());
		existente.setTecnico(orden.getTecnico());
		existente.setEquipo(orden.getEquipo());
		existente.setEstado(orden.getEstado());
		return ordenRepo.save(existente);
	}

	@Override
	public OrdenServicio registrarOrden(OrdenServicio orden, List<ItemOrden> items) {
		if (orden.getEstado() == null || orden.getEstado().getId() == null) {
			orden.setEstado(estadoPendiente());
		}

		Map<Integer, Integer> cantidades = new LinkedHashMap<>();
		if (items != null) {
			for (ItemOrden item : items) {
				if (item.idRepuesto() == null) {
					continue;
				}
				if (item.cantidad() <= 0) {
					throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
				}
				cantidades.merge(item.idRepuesto(), item.cantidad(), Integer::sum);
			}
		}

		for (Map.Entry<Integer, Integer> e : cantidades.entrySet()) {
			Repuesto r = repuestoRepo.findById(e.getKey())
					.orElseThrow(() -> new IllegalArgumentException("Repuesto no encontrado: " + e.getKey()));

			int cantidad = e.getValue();
			if (r.getStock() < cantidad) {
				throw new IllegalStateException(
						"Stock insuficiente de " + r.getNombre() + " (disponible: " + r.getStock() + ")");
			}

			r.setStock(r.getStock() - cantidad);
			orden.agregarDetalle(r, cantidad, r.getPrecioVenta());
		}
		return ordenRepo.save(orden);
	}

	@Override
	public void deleteOrden(Integer id) {
		ordenRepo.findById(id).ifPresent(orden -> {
			for (DetalleServicio d : orden.getDetalles()) {
				Repuesto r = d.getRepuesto();
				r.setStock(r.getStock() + d.getCantidad());
			}
			ordenRepo.delete(orden);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<OrdenServicio> buscarOrdenPorId(Integer id) {
		return ordenRepo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenServicio> getOrdenesPorTecnico(Integer idtecnico) {
		return ordenRepo.findByTecnicoIdtecnico(idtecnico);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenServicio> listarPorClienteId(Integer idCliente) {
		return ordenRepo.findByClienteIdCliente(idCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> contarOrdenesPorTecnico() {
		return ordenRepo.contarOrdenesPorTecnico();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> contarPorEstado(long minimo) {
		return ordenRepo.contarPorEstado(minimo);
	}

	private EstadoServicio estadoPendiente() {
		return estadoRepo.findByNombre("Pendiente")
				.orElseThrow(() -> new IllegalStateException("Falta el estado 'Pendiente' en tbl_estado_servicio."));
	}
}