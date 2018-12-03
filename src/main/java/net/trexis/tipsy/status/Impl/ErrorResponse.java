package net.trexis.tipsy.status.Impl;

import net.trexis.tipsy.enums.ResponseStatus;

public class ErrorResponse extends DefaultStatusResponseImpl {

    public ErrorResponse(String message, Object data){
        super(ResponseStatus.error, message, data);
    }

    public ErrorResponse(String message){
        super(ResponseStatus.error, message, null);
    }

    public ErrorResponse(Object data){
        super(ResponseStatus.error, null, data);
    }

    public ErrorResponse(){
        super(ResponseStatus.error, null, null);
    }

}