package br.com.inatel.MyCatalog.message;

/**
 * Message presented when the show is not in the database or external api
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class NotFoundMessage extends MotherMessage {

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " could not be found. Check if it is valid";
    }

}
