package com.techsolutions.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ErroresForm {

	private ErroresForm() {

	}

	public static Map<String, String> obtener(BindingResult result) {
		Map<String, String> errores = new LinkedHashMap<>();
		if (result == null) {
			return errores;
		}
		for (FieldError error : result.getFieldErrors()) {
			errores.putIfAbsent(error.getField(), error.getDefaultMessage());
		}
		return errores;
	}

	public static List<String> mensajes(BindingResult result) {
		List<String> mensajes = new ArrayList<>();
		if (result == null) {
			return mensajes;
		}
		for (ObjectError error : result.getAllErrors()) {
			mensajes.add(error.getDefaultMessage());
		}
		return mensajes;
	}

	public static String primerError(BindingResult result) {
		List<String> mensajes = mensajes(result);
		return mensajes.isEmpty() ? null : mensajes.get(0);
	}
}