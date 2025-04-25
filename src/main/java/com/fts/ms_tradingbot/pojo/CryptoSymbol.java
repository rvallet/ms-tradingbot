package com.fts.ms_tradingbot.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "CryptoSymbols")
public class CryptoSymbol {

    @Id
    private String id;
    private String symbol;
    private String name;
    private boolean is_active;
    private Double start_price;
    private Double current_price;
    private Double percentage_change;
    private Date created_at;
    private Date updated_at;

    public CryptoSymbol() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Double getStart_price() {
        return start_price;
    }

    public void setStart_price(Double start_price) {
        this.start_price = start_price;
    }

    public Double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
    }

    public Double getPercentage_change() {
        return percentage_change;
    }

    public void setPercentage_change(Double percentage_change) {
        this.percentage_change = percentage_change;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
