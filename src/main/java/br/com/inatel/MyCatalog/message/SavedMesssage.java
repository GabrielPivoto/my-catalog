package br.com.inatel.MyCatalog.message;

/**
 * Message presented when a new show is saved
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class SavedMesssage extends MotherMessage {

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " has been saved";
    }

}
