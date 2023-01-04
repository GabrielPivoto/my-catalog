package br.com.inatel.MyCatalog.message;

public class Message {

    public String showMessage(String titleId){
        try {return "The id " + Integer.parseInt(titleId);}catch (Exception ignored){}
        return "The title " + titleId;
    }

}
