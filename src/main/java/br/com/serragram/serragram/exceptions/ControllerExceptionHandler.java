package br.com.serragram.serragram.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserException.class)
	protected ResponseEntity<?> handleUserException (Exception ex) {
		
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
}
