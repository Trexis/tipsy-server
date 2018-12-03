package net.trexis.tipsy.exception;

public class ApplicationJsonException extends Exception {
    public ApplicationJsonException(String message){
        super(message);
    }
    public ApplicationJsonException(String message, Exception ex){
        super(message, ex);
    }
}
