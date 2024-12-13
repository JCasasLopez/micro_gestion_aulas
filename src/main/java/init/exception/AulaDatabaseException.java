package init.exception;

public class AulaDatabaseException extends RuntimeException {
	//Spring Data JPA usa unchecked exceptions (que heredan RuntimeException) siguiendo la filosofía 
	//general de Spring Framework, que prefiere las excepciones no verificadas
	public AulaDatabaseException(String message) {
        super(message);
    }
}
