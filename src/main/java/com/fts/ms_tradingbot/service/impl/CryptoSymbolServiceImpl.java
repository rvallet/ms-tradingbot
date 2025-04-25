package com.fts.ms_tradingbot.service.impl;

import com.fts.ms_tradingbot.dao.CryptoSymbolRepository;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.CryptoSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoSymbolServiceImpl implements CryptoSymbolService {

    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;

    @Override
    public List<CryptoSymbol> getAllCryptoSymbols() {
        return cryptoSymbolRepository.findAll();
    }

}
