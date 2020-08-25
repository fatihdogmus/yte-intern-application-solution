package yte.intern.spring.application.common.config;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import yte.intern.spring.application.common.dto.MessageResponse;
import yte.intern.spring.application.common.enums.MessageType;

@ControllerAdvice
public class ValidationControllerAdvice {

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public MessageResponse handleValidationException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return new MessageResponse(message, MessageType.ERROR);
	}
}
