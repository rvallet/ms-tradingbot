package com.fts.ms_tradingbot.service;

import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CryptoSymbolService {

    List<CryptoSymbol> getAllCryptoSymbols();
    CryptoSymbol getCryptoSymbolById(String id);
    CryptoSymbol getCryptoSymbolBySymbol(String symbol);

}
