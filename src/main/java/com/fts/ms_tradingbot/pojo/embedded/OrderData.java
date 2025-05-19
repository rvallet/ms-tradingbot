package com.fts.ms_tradingbot.pojo.embedded;

import com.fts.ms_tradingbot.enums.OrderStatusEnum;

public class OrderData {

    private Double quantity;
    private Double price;
    private String binanceClientOrderId;
    private OrderStatusEnum orderStatus;
    private CommonData commonData;

    public OrderData() {
        this.commonData = new CommonData();
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBinanceClientOrderId() {
        return binanceClientOrderId;
    }

    public void setBinanceClientOrderId(String binanceClientOrderId) {
        this.binanceClientOrderId = binanceClientOrderId;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public CommonData getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonData commonData) {
        this.commonData = commonData;
    }
}
