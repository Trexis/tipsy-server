package net.trexis.tipsy.status.Impl;

import com.google.gson.Gson;
import net.trexis.tipsy.enums.ResponseStatus;
import net.trexis.tipsy.status.StatusResponse;

public class DefaultStatusResponseImpl implements StatusResponse {
    public ResponseStatus statusType = ResponseStatus.unknown;
    public String statusMessage = "";
    public Object statusData = null;

    public DefaultStatusResponseImpl(ResponseStatus statusType, String message, Object data){
        this.statusType = statusType;
        this.statusMessage = message;
        this.statusData = data;
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public ResponseStatus getStatus() {
        return this.statusType;
    }

    @Override
    public String getMessage() {
        return this.statusMessage;
    }

    @Override
    public Object getData() {
        return this.statusData;
    }
}
