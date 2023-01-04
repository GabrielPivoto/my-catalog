package br.com.inatel.MyCatalog.message;

/**
 * Message presented when the user deletes a show
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class DeletedMessage extends Message {

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " has been deleted";
    }

}
