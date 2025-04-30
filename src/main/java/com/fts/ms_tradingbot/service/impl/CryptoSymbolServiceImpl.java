package com.fts.ms_tradingbot.service.impl;

import com.fts.ms_tradingbot.dao.CryptoSymbolRepository;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.CryptoSymbolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoSymbolServiceImpl implements CryptoSymbolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoSymbolServiceImpl.class);

    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;

    @Override
    public List<CryptoSymbol> getAllCryptoSymbols() {
        LOGGER.debug("getAllCryptoSymbols");
        return cryptoSymbolRepository.findAll();
    }

    @Override
    public CryptoSymbol getCryptoSymbolById(String id) {
        LOGGER.debug("getCryptoSymbolById : {}", id);
        return cryptoSymbolRepository.findById(id).orElse(null);
    }

    public CryptoSymbol getCryptoSymbolBySymbol(String symbol) {
        LOGGER.debug("getCryptoSymbolBySymbol : {}", symbol);
        return cryptoSymbolRepository.findAll().stream()
                .filter(cryptoSymbol -> cryptoSymbol.getSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElse(null);
    }

}
