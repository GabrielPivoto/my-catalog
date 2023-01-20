package br.com.inatel.MyCatalog.message;

/**
 * Message presented when the user tries to add a show that is already registered
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class AlreadyRegisteredMessage extends MotherMessage {

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " is already registered. Try adding another one";
    }

}
