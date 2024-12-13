package init.exception;

public class MicroserviceCommunicationException extends RuntimeException {
	//Spring Data JPA usa unchecked exceptions (que heredan RuntimeException) siguiendo la filosofía 
	//general de Spring Framework, que prefiere las excepciones no verificadas
	public MicroserviceCommunicationException(String message) {
        super(message);
    }
}
