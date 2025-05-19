package com.fts.ms_tradingbot.pojo;

import com.fts.ms_tradingbot.pojo.embedded.CommonData;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "CryptoSymbols")
public class CryptoSymbol {

    @Id
    private String id;

    /**
     * Le symbole de la crypto-monnaie.
     * Il doit être unique en BDD, il faut donc créer un index unique sur ce champ.
     * Dans Mongo : db.CryptoSymbols.createIndex({ symbol: 1 }, { unique: true })
     */
    @Indexed(unique = true)
    private String symbol;

    private String name;
    private boolean is_active;
    private Double start_price;
    private Double current_price;
    private Double percentage_change;
    private CommonData commonData;
    private Date created_at;
    private Date updated_at;

    /**
     * Constructeur par défaut pour MongoDB
     * Ce constructeur est utilisé par Spring Data MongoDB pour créer des instances de la classe.
     * Il est important de le laisser public et sans paramètres.
     */
    public CryptoSymbol() {
    }

    /**
     * Constructeur avec paramètres
     * @param symbol - le symbole de la crypto-monnaie
     * @param name - le nom de la crypto-monnaie
     * @param is_active - indique si la crypto-monnaie est active
     * @param start_price - le prix de départ de la crypto-monnaie
     * @param current_price - le prix actuel de la crypto-monnaie
     * @param percentage_change - le pourcentage de changement de la crypto-monnaie
     */
    public CryptoSymbol(String symbol, String name, boolean is_active, Double start_price, Double current_price,
                        Double percentage_change) {
        this.symbol = symbol;
        this.name = name;
        this.is_active = is_active;
        this.start_price = start_price;
        this.current_price = current_price;
        this.percentage_change = percentage_change;
        this.commonData = new CommonData();
        this.created_at = new Date();
        this.updated_at = new Date();
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

    public CommonData getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonData commonData) {
        this.commonData = commonData;
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
