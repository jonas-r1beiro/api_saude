package br.com.magnasistemas.api_saude.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(ArgumentoInvalidoException.class)
	public ResponseEntity<String> tratarErro400(ArgumentoInvalidoException ex) {
		var erros = ex.getMessage();
		
		return ResponseEntity.badRequest().body(erros);
	}
	
}
