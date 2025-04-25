package com.fts.ms_tradingbot.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crypto_symbols")
public class CryptoSymbol {

    @Id
    private String id;
    private String symbol;
    private String name;

}
