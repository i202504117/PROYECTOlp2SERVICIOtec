package com.techsolutions.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techsolutions.dto.ItemOrden;
import com.techsolutions.modelo.OrdenServicio;
import com.techsolutions.modelo.Tecnico;
import com.techsolutions.service.ICatalogoService;
import com.techsolutions.service.IClienteService;
import com.techsolutions.service.IOrdenServicioService;
import com.techsolutions.service.IRepuestoService;
import com.techsolutions.service.ITecnicoSevice;
import com.techsolutions.util.ErroresForm;

import jakarta.validation.Valid;

@Controller
public class OrdenServicioController {

	@Autowired
	private IOrdenServicioService ordenService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private ITecnicoSevice tecService;

	@Autowired
	private IRepuestoService repuestoService;

	@Autowired
	private ICatalogoService catalogoService;

	// Listar órdenes según el rol del usuario autenticado
	@GetMapping("/listarOrdenes")
	public String listar(Model model, Authentication auth) {
		if (auth == null) {
			return "redirect:/login";
		}

		List<OrdenServicio> ordenes;

		if (tieneRol(auth, "ROLE_ADMINISTRADOR")) {
			// El administrador ve todas las órdenes
			ordenes = ordenService.getAllOrdenes();

		} else if (tieneRol(auth, "ROLE_TECNICO")) {
			// El técnico ve solo las suyas
			Tecnico tecnico = tecService.findByNombre(auth.getName());
			ordenes = (tecnico != null) ? ordenService.getOrdenesPorTecnico(tecnico.getIdtecnico())
					: ordenService.getAllOrdenes();

		} else {
			// Cualquier otro rol (cliente) NO debe ver las órdenes de los demás:
			// se le envía a "Mis órdenes", que solo muestra las suyas.
			return "redirect:/mis-ordenes";
		}

		model.addAttribute("ordenes", ordenes);
		return "ordenes";
	}

	// Formulario para nueva orden
	@GetMapping("/nuevaOrden")
	public String nuevo(Model model) {
		model.addAttribute("orden", new OrdenServicio());
		cargarListas(model);
		return "formularioOrden";
	}

	// Edición segura con validación de Optional
	@GetMapping("/editarOrden/{id}")
	public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Optional<OrdenServicio> optOrden = ordenService.buscarOrdenPorId(id);

		if (optOrden.isPresent()) {
			model.addAttribute("orden", optOrden.get());
			cargarListas(model);
			return "formularioOrden";
		}
		redirectAttributes.addFlashAttribute("error", "❌ La orden de servicio solicitada no existe.");
		return "redirect:/listarOrdenes";
	}

	/**
	 * Guarda una orden. - Orden NUEVA: se registra junto con sus repuestos
	 * (idRepuesto[] y cantidad[] del formulario) y se descuenta el stock en una
	 * sola transacción. - Orden EXISTENTE: solo se editan sus datos básicos; sus
	 * repuestos no se tocan.
	 */
	@PostMapping("/guardarOrden")
	public String guardar(@Valid @ModelAttribute("orden") OrdenServicio orden, BindingResult result,
			@RequestParam(name = "idRepuesto", required = false) List<Integer> idRepuestos,
			@RequestParam(name = "cantidad", required = false) List<Integer> cantidades,
			RedirectAttributes redirectAttributes, Model model) {

		limpiarReferenciasVacias(orden);
		validarReferencias(orden, result);

		if (result.hasErrors()) {
			model.addAttribute("error", "❌ " + ErroresForm.primerError(result));
			cargarListas(model);
			return "formularioOrden";
		}

		try {
			if (orden.getIdorden() == null) {
				ordenService.registrarOrden(orden, construirItems(idRepuestos, cantidades));
			} else {
				ordenService.saveOrden(orden);
			}
			redirectAttributes.addFlashAttribute("exito", "Orden de servicio guardada correctamente.");
			return "redirect:/listarOrdenes";

		} catch (IllegalArgumentException | IllegalStateException e) {
			// Stock insuficiente, repuesto inexistente, falta el estado "Pendiente"...
			// La transacción ya hizo rollback: no se guardó nada.
			model.addAttribute("error", "❌ " + e.getMessage());
			cargarListas(model);
			return "formularioOrden";
		}
	}

	// Eliminar orden (los repuestos reservados vuelven al stock)
	@GetMapping("/eliminarOrden/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		ordenService.deleteOrden(id);
		redirectAttributes.addFlashAttribute("exito", "Orden eliminada exitosamente.");
		return "redirect:/listarOrdenes";
	}

	// ---------- Métodos auxiliares ----------

	// Listas desplegables del formulario
	private void cargarListas(Model model) {
		model.addAttribute("clientes", clienteService.getAllClientes());
		model.addAttribute("tecnicos", tecService.getAllTecnico());
		model.addAttribute("repuestos", repuestoService.getAllRepuesto());
		model.addAttribute("equipos", catalogoService.listarEquipos());
		model.addAttribute("estados", catalogoService.listarEstados());
	}

	private boolean tieneRol(Authentication auth, String rol) {
		return auth.getAuthorities().contains(new SimpleGrantedAuthority(rol));
	}

	// Un select sin elegir envía id vacío: esa referencia se deja en null
	private void limpiarReferenciasVacias(OrdenServicio orden) {
		if (orden.getCliente() != null && orden.getCliente().getIdCliente() == null) {
			orden.setCliente(null);
		}
		if (orden.getTecnico() != null && orden.getTecnico().getIdtecnico() <= 0) {
			orden.setTecnico(null);
		}
		if (orden.getEquipo() != null && orden.getEquipo().getId() == null) {
			orden.setEquipo(null);
		}
		// El estado es opcional: si no se elige, el service asigna "Pendiente"
		if (orden.getEstado() != null && orden.getEstado().getId() == null) {
			orden.setEstado(null);
		}
	}

	private void validarReferencias(OrdenServicio orden, BindingResult result) {
		if (orden.getCliente() == null) {
			result.rejectValue("cliente", "required", "Selecciona un cliente.");
		}
		if (orden.getTecnico() == null) {
			result.rejectValue("tecnico", "required", "Selecciona un técnico.");
		}
		if (orden.getEquipo() == null) {
			result.rejectValue("equipo", "required", "Selecciona un equipo.");
		}
	}

	// Une las dos listas paralelas del formulario (idRepuesto[] y cantidad[]) en
	// items
	private List<ItemOrden> construirItems(List<Integer> idRepuestos, List<Integer> cantidades) {
		List<ItemOrden> items = new ArrayList<>();
		if (idRepuestos == null) {
			return items;
		}
		for (int i = 0; i < idRepuestos.size(); i++) {
			Integer idRepuesto = idRepuestos.get(i);
			if (idRepuesto == null) {
				continue; // fila sin repuesto elegido
			}
			Integer cantidad = (cantidades != null && i < cantidades.size()) ? cantidades.get(i) : null;
			items.add(new ItemOrden(idRepuesto, cantidad != null ? cantidad : 1));
		}
		return items;
	}
}
