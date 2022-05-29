package launcher.services.exceptions;

public class ContentUpdateException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContentUpdateException() {

	}

	public ContentUpdateException(String message) {
		super(message);

	}

	public ContentUpdateException(Throwable cause) {
		super(cause);

	}

	public ContentUpdateException(String message, Throwable cause) {
		super(message, cause);

	}

	public ContentUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
