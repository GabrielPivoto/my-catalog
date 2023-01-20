package br.com.inatel.MyCatalog.message;

/**
 * Auxiliary class for the other message classes, implementing Strategy
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public class MotherMessage {

    public String showMessage(String titleId){
        try {return "The id " + Integer.parseInt(titleId);}catch (Exception ignored){}
        return "The title " + titleId;
    }

}
