package bg.plambis.data.api;

/**
 * Data module exception
 * 
 * @author plambis
 */
public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 2747701607877591656L;

	public DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

}
