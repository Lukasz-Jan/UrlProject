package launcher.services.exceptions;

public class WorkerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkerException(String message) {
		super(message);

	}

	public WorkerException(Throwable cause) {
		super(cause);
	}

}
