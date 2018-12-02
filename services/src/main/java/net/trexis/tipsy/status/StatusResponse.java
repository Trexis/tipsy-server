package net.trexis.tipsy.status;


import net.trexis.tipsy.enums.ResponseStatus;

public interface StatusResponse {
    public String toJson();
    public ResponseStatus getStatus();
    public String getMessage();
    public Object getData();
}
