package com.fts.ms_tradingbot.ws;
import com.fts.ms_tradingbot.ApiRegistration;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.CryptoSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ApiRegistration.REST_PREFIX)
public class CryptoSymbolApi {

    @Autowired
    private CryptoSymbolService cryptoSymbolService;

    @GetMapping(ApiRegistration.REST_CRYPTO_SYMBOLS)
    public List<CryptoSymbol> getAllCryptoSymbols() {
        return cryptoSymbolService.getAllCryptoSymbols();
    }

    @GetMapping(ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.SYMBOL + "/{symbol}")
    public CryptoSymbol getCryptoSymbolBySymbol(@PathVariable(value = "symbol") String symbol) {
        return cryptoSymbolService.findBySymbol(symbol);
    }

    @GetMapping(ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.ID + "/{cryptoid}")
    public ResponseEntity<CryptoSymbol> getCryptoSymbolById(@PathVariable(value = "cryptoid") String id) {
        return cryptoSymbolService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiRegistration.REST_CRYPTO_SYMBOLS + "/unique")
    public List<String> getUniqueSymbolsInAlphabeticalOrder() {
        return cryptoSymbolService.getUniqueSymbolsInAlphabeticalOrder();
    }

    @PostMapping(ApiRegistration.REST_CRYPTO_SYMBOLS)
    public ResponseEntity<CryptoSymbol> createCryptoSymbol(@RequestBody CryptoSymbol cryptoSymbol) {
        CryptoSymbol savedSymbol = cryptoSymbolService.save(cryptoSymbol);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSymbol.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedSymbol);
    }

    @DeleteMapping(ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.ID + "/{cryptoid}")
    public ResponseEntity<Void> deleteCryptoSymbol(@PathVariable(value = "cryptoid") String id) {
        if (cryptoSymbolService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
