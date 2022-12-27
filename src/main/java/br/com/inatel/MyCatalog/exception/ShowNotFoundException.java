package br.com.inatel.MyCatalog.exception;

/**
 * Exception thrown when user tries to delete, update or search for a show thar doesn't exist.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class ShowNotFoundException extends RuntimeException {

    public ShowNotFoundException(String message) {
        super(message);
    }

}
