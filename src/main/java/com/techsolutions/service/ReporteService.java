package com.techsolutions.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Cliente;
import com.techsolutions.modelo.OrdenServicio;
import com.techsolutions.modelo.Proveedor;
import com.techsolutions.modelo.Repuesto;
import com.techsolutions.modelo.Tecnico;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteService {

	private byte[] generar(
	        String archivoJrxml,
	        List<?> datos,
	        String titulo) throws Exception {

	    String ruta = "/reports/" + archivoJrxml;

	    System.out.println("Generando reporte: " + ruta);
	    System.out.println("Cantidad de registros: " + datos.size());

	    InputStream stream = getClass().getResourceAsStream(ruta);

	    if (stream == null) {
	        throw new IllegalStateException(
	            "No se encontró la plantilla: " + ruta
	        );
	    }

	    try (InputStream plantilla = stream) {
	        JasperReport jasperReport =
	            JasperCompileManager.compileReport(plantilla);

	        Map<String, Object> params = new HashMap<>();
	        params.put("TITULO", titulo);

	        JRBeanCollectionDataSource dataSource =
	            new JRBeanCollectionDataSource(datos);

	        JasperPrint jasperPrint =
	            JasperFillManager.fillReport(
	                jasperReport, params, dataSource
	            );

	        return JasperExportManager.exportReportToPdf(jasperPrint);
	    }
	}

    public byte[] generarReporteOrdenes(List<OrdenServicio> datos, String titulo) throws Exception {
        return generar("reporte_ordenes.jrxml", datos, titulo);
    }

    public byte[] generarReporteClientes(List<Cliente> datos, String titulo) throws Exception {
        return generar("reporte_clientes.jrxml", datos, titulo);
    }

    public byte[] generarReporteTecnicos(List<Tecnico> datos, String titulo) throws Exception {
        return generar("reporte_tecnicos.jrxml", datos, titulo);
    }

    public byte[] generarReporteRepuesto(List<Repuesto> datos, String titulo) throws Exception {
        return generar("reporte_repuestos.jrxml", datos, titulo);
    }

    public byte[] generarReporteProveedores(List<Proveedor> datos, String titulo) throws Exception {
        return generar("reporte_proveedores.jrxml", datos, titulo);
    }

    // Mantener compatibilidad con el método anterior
    public byte[] generarReporte(List<OrdenServicio> ordenes, String titulo) throws Exception {
        return generarReporteOrdenes(ordenes, titulo);
    }

	
}