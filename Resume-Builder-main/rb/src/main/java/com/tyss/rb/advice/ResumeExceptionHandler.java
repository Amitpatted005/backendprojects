package com.tyss.rb.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tyss.rb.exception.ResumeDetailsNotFound;
import com.tyss.rb.exception.SomethingWentWrongException;
import com.tyss.rb.response.AppResponse;

@RestControllerAdvice
public class ResumeExceptionHandler {
	@Autowired
	private AppResponse appResponse;

	@ExceptionHandler(ResumeDetailsNotFound.class)
	public ResponseEntity<AppResponse> resumeDetailsNotFound(ResumeDetailsNotFound ex) {
		appResponse.setError(true);
		appResponse.setStatus(HttpStatus.NOT_FOUND.value());
		appResponse.setMessage(ex.getMessage());
		appResponse.setData(null);
		return new ResponseEntity<AppResponse>(appResponse, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(SomethingWentWrongException.class)
	public ResponseEntity<AppResponse> somethingWentWrong(ResumeDetailsNotFound ex) {
		appResponse.setError(true);
		appResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		appResponse.setMessage(ex.getMessage());
		appResponse.setData(null);
		return new ResponseEntity<AppResponse>(appResponse, HttpStatus.BAD_REQUEST);

	}


}
