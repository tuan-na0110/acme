package com.acme;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.acme.model.ResponseModel;

/**
 * The class handles exceptions in the application
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseModel handleException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		return new ResponseModel(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
    }
    
    @ExceptionHandler(InvalidPropertyException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseModel handleException(InvalidPropertyException ex, WebRequest request) {
		return new ResponseModel(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        return new ResponseModel(HttpStatus.UNAUTHORIZED.value(), ex.getLocalizedMessage());
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseModel handleException(HttpMessageNotReadableException ex, WebRequest request) {
		return new ResponseModel(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
    }
    
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseModel handleException(HttpClientErrorException.Unauthorized ex, WebRequest request) {
		return new ResponseModel(HttpStatus.UNAUTHORIZED.value(), ex.getLocalizedMessage());
    }

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseModel handleAllException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage());
	}
}
