package com.product.registration.user.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserRegistrationExceptionController {
	@ExceptionHandler(value = UserRegistrationGenericException.class)
	   public ResponseEntity<Object> exception(UserRegistrationGenericException exception) {
	      return new ResponseEntity<>("User Registration Error. Error processing the request.", HttpStatus.BAD_REQUEST);
	   }
}

 