package br.com.inatel.MyCatalog.exception;

/**
 * Exception thrown when user tries to add a title already registered.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class ShowAlreadyRegisteredException extends RuntimeException {

    public ShowAlreadyRegisteredException(String message) {
        super(message);
    }

}
