package com.techsolutions.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techsolutions.modelo.Equipo;
import com.techsolutions.service.ICatalogoService;
import com.techsolutions.service.IClienteService;
import com.techsolutions.util.ErroresForm;


@Controller
public class EquipoController {

	@Autowired
	private ICatalogoService catalogoService;

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/listarEquipos")
	public String listar(Model model) {
		model.addAttribute("equipos", catalogoService.listarEquipos());
		return "equipos";
	}

	@GetMapping("/nuevoEquipo")
	public String nuevo(Model model) {
		model.addAttribute("equipo", new Equipo());
		cargarCombos(model);
		return "formularioEquipo";
	}

	@GetMapping("/editarEquipo/{id}")
	public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Optional<Equipo> equipo = catalogoService.buscarEquipo(id);

		if (equipo.isPresent()) {
			model.addAttribute("equipo", equipo.get());
			cargarCombos(model);
			return "formularioEquipo";
		}
		redirectAttributes.addFlashAttribute("error", "❌ El equipo solicitado no existe.");
		return "redirect:/listarEquipos";
	}

	@PostMapping("/guardarEquipo")
	public String guardar(@ModelAttribute("equipo") Equipo equipo, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {

		// Selects sin elegir envían id vacío: se dejan en null
		if (equipo.getMarca() != null && equipo.getMarca().getId() == null) {
			equipo.setMarca(null);
		}
		if (equipo.getCliente() != null && equipo.getCliente().getIdCliente() == null) {
			equipo.setCliente(null);
		}

		String error = null;
		if (result.hasErrors()) {
			error = ErroresForm.primerError(result);
		} else if (equipo.getTipo() == null || equipo.getTipo().isBlank()) {
			error = "El tipo de equipo es obligatorio (laptop, celular, PC...).";
		} else if (equipo.getCliente() == null) {
			error = "Selecciona el cliente dueño del equipo.";
		}

		if (error != null) {
			model.addAttribute("error", "❌ " + error);
			cargarCombos(model);
			return "formularioEquipo";
		}

		catalogoService.guardarEquipo(equipo);
		redirectAttributes.addFlashAttribute("exito", "Equipo guardado correctamente.");
		return "redirect:/listarEquipos";
	}

	@GetMapping("/eliminarEquipo/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		try {
			catalogoService.eliminarEquipo(id);
			redirectAttributes.addFlashAttribute("exito", "Equipo eliminado correctamente.");
		} catch (IllegalStateException e) {
			// Tiene órdenes de servicio asociadas
			redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
		}
		return "redirect:/listarEquipos";
	}

	private void cargarCombos(Model model) {
		model.addAttribute("clientes", clienteService.getAllClientes());
		model.addAttribute("marcas", catalogoService.listarMarcas());
	}
}
