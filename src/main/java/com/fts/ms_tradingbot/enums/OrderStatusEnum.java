package com.fts.ms_tradingbot.enums;

public enum OrderStatusEnum {

    NEW("NEW"),
    FILLED("FILLED"),
    PARTIALLY_FILLED("PARTIALLY_FILLED"),
    CANCELED("CANCELED"),
    PENDING_CANCEL("PENDING_CANCEL"),
    REJECTED("REJECTED"),
    EXPIRED("EXPIRED");

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
