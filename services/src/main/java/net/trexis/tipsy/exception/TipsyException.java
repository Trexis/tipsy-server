package net.trexis.tipsy.exception;

import net.trexis.tipsy.enums.ErrorCode;

public class TipsyException extends Exception {
    private ErrorCode errorCode;

    public TipsyException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public TipsyException(ErrorCode errorCode, String message, Exception ex){
        super(message, ex);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
