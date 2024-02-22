package org.example.services.api;

public class ClientResponseContract {

    public boolean IsSuccess = false;
    public String ErrorMessage = "Not sendnd";
    public String Data;

    public ClientResponseContract error(String ErrorMessage){
        this.ErrorMessage = ErrorMessage;
        this.IsSuccess = false;
        return  this;
    }
}
