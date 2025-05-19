package com.fts.ms_tradingbot.mock;

import com.fts.ms_tradingbot.enums.OrderStatusEnum;
import com.fts.ms_tradingbot.pojo.Order;
import com.fts.ms_tradingbot.pojo.embedded.OrderData;
import com.fts.ms_tradingbot.pojo.embedded.PurchaseData;
import com.fts.ms_tradingbot.pojo.embedded.SaleData;

import java.util.List;

public class OrderMock {

    /**
     * Crée une instance de PurchaseData
     */
    public static PurchaseData getPurchaseDataMock() {
        PurchaseData purchaseData = new PurchaseData();
        purchaseData.setQuantity(100.0);
        purchaseData.setPrice(50.0);
        purchaseData.setBinanceClientOrderId("binanceClientOrderIdTest_purchase");
        purchaseData.setOrderStatus(OrderStatusEnum.NEW);
        return purchaseData;
    }

    /**
     * Crée une instance de SaleData
     */
    public static SaleData getSaleDataMock() {
        SaleData saleData = new SaleData();
        saleData.setQuantity(100.0);
        saleData.setPrice(50.0);
        saleData.setBinanceClientOrderId("binanceClientOrderIdTest_sale");
        saleData.setOrderStatus(OrderStatusEnum.FILLED);
        return saleData;
    }

    /**
     * Crée un Order avec ses données de purchase et sale
     */
    public static Order getOrderMock() {
        Order order = new Order();
        order.setPurchaseData(getPurchaseDataMock());
        order.setSaleData(getSaleDataMock());
        order.setCryptoSymbolId("BTCUSDT");
        return order;
    }

    /**
     * Crée une liste de plusieurs Orders mock
     */
    public static List<Order> getOrdersMock() {
        Order order1 = getOrderMock();
        order1.setCryptoSymbolId("BTCUSDT");
        order1.getPurchaseData().setBinanceClientOrderId("binanceClientOrderIdTest_purchase_1");
        order1.getSaleData().setBinanceClientOrderId("binanceClientOrderIdTest_sale_1");

        Order order2 = getOrderMock();
        order2.setCryptoSymbolId("ETHUSDT");
        order2.getPurchaseData().setBinanceClientOrderId("binanceClientOrderIdTest_purchase_2");
        order2.getSaleData().setBinanceClientOrderId("binanceClientOrderIdTest_sale_2");

        return List.of(order1, order2);
    }
}
