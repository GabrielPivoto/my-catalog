package br.com.inatel.MyCatalog.message;

public class NotFoundMessage extends Message {

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " could not be found. Check if it is valid";
    }

}
