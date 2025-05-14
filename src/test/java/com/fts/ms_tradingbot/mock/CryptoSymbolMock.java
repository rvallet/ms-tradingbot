package com.fts.ms_tradingbot.mock;

import com.fts.ms_tradingbot.pojo.CryptoSymbol;

import java.util.Arrays;
import java.util.List;

public class CryptoSymbolMock {

    public static final String BTCUSDC = "BTCUSDC";
    public static final String ETHUSDC = "ETHUSDC";
    public static final String ADAUSDC = "ADAUSDC";
    public static final String TESTUSDC = "TESTUSDC";

    /**
     * Mock de test pour une liste de symboles de crypto-monnaies
     * @return - Liste de symboles de crypto-monnaies
     */
    public static List<CryptoSymbol> getCryptoSymbolsMock() {
        return Arrays.asList(
                new CryptoSymbol(BTCUSDC, "Bitcoin", true, 1.0, 2.0, 5.0),
                new CryptoSymbol(ETHUSDC, "Ethereum", true, 3.0, 4.0, 10.0),
                new CryptoSymbol(ADAUSDC, "Cardano", true, 5.0, 6.0, 15.0)
        );
    }

    /**
     * Mock de test pour un symbole de crypto-monnaie
     * @return - Symbole de crypto-monnaie
     */
    public  static CryptoSymbol getCryptoSymbolMock() {
        return new CryptoSymbol(
                TESTUSDC,
                "Test Crypto",
                true,
                0.0,
                0.0,
                0.0
        );
    }

}
