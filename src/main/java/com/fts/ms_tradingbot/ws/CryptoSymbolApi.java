package com.fts.ms_tradingbot.ws;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.impl.CryptoSymbolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crypto-symbols")
public class CryptoSymbolApi {

    @Autowired
    private CryptoSymbolServiceImpl cryptoSymbolService;

    @GetMapping
    public List<CryptoSymbol> getAllCryptoSymbols() {
        return cryptoSymbolService.getAllCryptoSymbols();
    }

}
