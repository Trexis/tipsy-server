package net.trexis.tipsy.enums;

public enum TransactionCode {
    OpeningBalance(001);
    private final int value;

    private TransactionCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
