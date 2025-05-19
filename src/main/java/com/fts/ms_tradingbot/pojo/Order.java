package com.fts.ms_tradingbot.pojo;

import com.fts.ms_tradingbot.pojo.embedded.PurchaseData;
import com.fts.ms_tradingbot.pojo.embedded.SaleData;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Orders")
public class Order {

    @Id
    private String id;

    private String cryptoSymbolId;

    private PurchaseData purchaseData;

    private SaleData saleData;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCryptoSymbolId() {
        return cryptoSymbolId;
    }

    public void setCryptoSymbolId(String cryptoSymbolId) {
        this.cryptoSymbolId = cryptoSymbolId;
    }

    public PurchaseData getPurchaseData() {
        return purchaseData;
    }

    public void setPurchaseData(PurchaseData purchaseData) {
        this.purchaseData = purchaseData;
    }

    public SaleData getSaleData() {
        return saleData;
    }

    public void setSaleData(SaleData saleData) {
        this.saleData = saleData;
    }

}
