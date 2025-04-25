package com.fts.ms_tradingbot.ws;
import com.fts.ms_tradingbot.ApiRegistration;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.impl.CryptoSymbolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ApiRegistration.REST_PREFIX)
public class CryptoSymbolApi {

    @Autowired
    private CryptoSymbolServiceImpl cryptoSymbolService;

    @GetMapping(ApiRegistration.REST_CRYPTO_SYMBOLS)
    public List<CryptoSymbol> getAllCryptoSymbols() {
        return cryptoSymbolService.getAllCryptoSymbols();
    }

    @GetMapping(ApiRegistration.REST_CRYPTO_SYMBOLS + "/{symbol}")
    public CryptoSymbol getCryptoSymbolBySymbol(@PathVariable(value = "symbol") String symbol) {
        return cryptoSymbolService.getCryptoSymbolBySymbol(symbol);
    }

}
