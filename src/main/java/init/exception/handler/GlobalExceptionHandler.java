package init.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import init.exception.AulaDatabaseException;
import init.exception.MicroserviceCommunicationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AulaDatabaseException.class)
	public ResponseEntity<String> handleAulaDatabaseException(AulaDatabaseException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MicroserviceCommunicationException.class)
	public ResponseEntity<String> handleMicroserviceCommunicationException(MicroserviceCommunicationException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}

}
