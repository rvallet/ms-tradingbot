package com.fts.ms_tradingbot.ws;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.impl.CryptoSymbolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crypto-symbols")
public class CryptoSymbolApi {

    @Autowired
    private CryptoSymbolServiceImpl cryptoSymbolService;

    @GetMapping
    public ResponseEntity<List<CryptoSymbol>> getAllCryptoSymbols() {
        List<CryptoSymbol> symbols = cryptoSymbolService.getAllCryptoSymbols();
        return ResponseEntity.ok(symbols);
    }

}
