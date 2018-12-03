package net.trexis.tipsy.status.Impl;

import net.trexis.tipsy.enums.ResponseStatus;

public class SuccessResponse extends DefaultStatusResponseImpl {

    public SuccessResponse(String message, Object data){
        super(ResponseStatus.success, message, data);
    }

    public SuccessResponse(String message){
        super(ResponseStatus.success, message, null);
    }

    public SuccessResponse(Object data){
        super(ResponseStatus.success, null, data);
    }

    public SuccessResponse(){
        super(ResponseStatus.success, null, null);
    }

}
