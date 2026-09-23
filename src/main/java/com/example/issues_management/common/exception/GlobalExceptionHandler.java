package com.example.issues_management.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new LinkedHashMap<>();
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ApiErrorResponse response = new ApiErrorResponse(
			LocalDateTime.now(),
			HttpStatus.BAD_REQUEST.value(),
			"Validation failed",
			errors
		);

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
		ApiErrorResponse response = new ApiErrorResponse(
			LocalDateTime.now(),
			HttpStatus.NOT_FOUND.value(),
			exception.getMessage(),
			Map.of()
		);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler({IllegalArgumentException.class, BadCredentialsException.class})
	public ResponseEntity<ApiErrorResponse> handleBadRequestException(RuntimeException exception) {
		ApiErrorResponse response = new ApiErrorResponse(
			LocalDateTime.now(),
			HttpStatus.BAD_REQUEST.value(),
			exception.getMessage(),
			Map.of()
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException exception) {
		ApiErrorResponse response = new ApiErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				exception.getMessage(),
				Map.of()
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
