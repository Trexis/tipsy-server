package net.trexis.tipsy.enums;

public enum ErrorCode {
    systemdisabled(001);

    private final int value;

    private ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
