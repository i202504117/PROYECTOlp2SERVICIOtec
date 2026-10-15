
package com.techsolutions.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techsolutions.modelo.Tecnico;
import com.techsolutions.service.TecnicoImplement;

@Controller
public class TenicoController {
	
	@Autowired
	private TecnicoImplement tecnicoImpl;
	
	// LISTAR TODOS
    @GetMapping("/listarTecnicos")
    public String listarTecnicos(Model model) {
        model.addAttribute("tecnicos", tecnicoImpl.getAllTecnico());
        return "tecnico";
    }

    // NUEVO FORMULARIO
    @GetMapping("/nuevoTecnico")
    public String nuevoTecnico(Model model) {
        model.addAttribute("tecnico", new Tecnico());
        return "formularioTecnico";
    }

 // CARGAR FORMULARIO CON DATOS
    @GetMapping("/editarTecnico/{id}")
    public String editarTecnico(@PathVariable int id, Model model) {
        Optional<Tecnico> tecnico = tecnicoImpl.buscarTecnico(id);
        model.addAttribute("tecnico", tecnico.get());
        return "formularioTecnico";
    }

    // GUARDAR CAMBIOS
    @PostMapping("/guardarTecnico")
    public String guardarTecnico(Tecnico tecnico) {
    	tecnicoImpl.createTecnico(tecnico);
        return "redirect:/listarTecnicos";
    }

    // ELIMINAR
    @GetMapping("/eliminarTecnico/{id}")
    public String eliminarTecnico(@PathVariable Integer id) {
    	tecnicoImpl.deleteTecnico(id);
        return "redirect:/listarTecnicos";
    }

    // BUSCAR POR NOMBRE
    @GetMapping("/buscarTecnico")
    public String buscarTecnico(@RequestParam String nombre, Model model) {
        model.addAttribute("tecnicos", tecnicoImpl.buscarNombre(nombre));
        return "tecnico";
    }

}
