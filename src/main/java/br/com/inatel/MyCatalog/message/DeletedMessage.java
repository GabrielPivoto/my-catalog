package br.com.inatel.MyCatalog.message;

public class DeletedMessage extends Message{

    @Override
    public String showMessage(String titleId) {
        return super.showMessage(titleId) + " has been deleted";
    }

}
