package com.fts.ms_tradingbot.exception;

public class CryptoSymbolNotFoundException extends RuntimeException {

    public CryptoSymbolNotFoundException(String parameter) {
        super("CryptoSymbol not found with this parameter: " + parameter);
    }

}
