package core.exception;

/**
 * Custom exception class for handling resource not found scenarios
 * This exception is thrown when a requested resource (like a medical report) is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructor with a custom message
     * @param message the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with a custom message and cause
     * @param message the error message
     * @param cause the cause of the exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
