package br.com.inatel.MyCatalog.message;

public class AlreadyRegisteredMessage extends Message{

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " is already registered. Try adding another one";
    }

}
