package com.techsolutions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.techsolutions.modelo.Repuesto;
import com.techsolutions.service.IProveedorService;
import com.techsolutions.service.RepuestoImplement;

import org.springframework.ui.Model;

@Controller
public class RepuestoController {

    @Autowired
    private RepuestoImplement repuestoImp;
    @Autowired
    private IProveedorService proveedorService;

    @GetMapping("/listarRepuestos")
    public String listar(Model model) {

        model.addAttribute( "repuestos",repuestoImp.getAllRepuesto());
        model.addAttribute("proveedores", proveedorService.listarTodos());


        return "repuestos";
    }

   @GetMapping("/nuevoRepuestos")
    public String nuevo(Model model) {

        System.out.println(">>> ESTOY EN /nuevoRepuesto");

        model.addAttribute("repuesto", new Repuesto());
        model.addAttribute("proveedores", proveedorService.listarTodos());

        return "formularioRepuesto";
   }

    @PostMapping("/guardarRepuestos")
    public String guardar(Repuesto repuesto) {

        repuestoImp.createRepuesto(repuesto);

        return "redirect:/listarRepuestos";
    }

    @GetMapping("/editarRepuestos/{id}")
    public String editar(
            @PathVariable int id,
            Model model) {

        model.addAttribute(
                "repuesto",
                repuestoImp.buscarRepuesto(id).get());

        return "formularioRepuesto";
    }

    @GetMapping("/eliminarRepuestos/{id}")
    public String eliminar(
            @PathVariable int id) {

        repuestoImp.deleteRepuesto(id);

        return "redirect:/listarRepuestos";
    }

    @PostMapping("/buscarRepuestos")
    public String buscar(
            @RequestParam("nombre") String nombre,
            Model model) {

        model.addAttribute(
                "repuestos",
                repuestoImp.buscarPorNombre(nombre));

        return "repuestos";
    }
    
   

}