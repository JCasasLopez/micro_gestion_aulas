package init.exception;

public class MicroserviceCommunicationException extends RuntimeException {
	public MicroserviceCommunicationException(String message) {
        super(message);
    }
}