package net.trexis.tipsy.enums;

public enum TransactionCode {
    openingbalance(001);
    private final int value;

    private TransactionCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
