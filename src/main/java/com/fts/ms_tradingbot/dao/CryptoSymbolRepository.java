package com.fts.ms_tradingbot.dao;

import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoSymbolRepository extends MongoRepository<CryptoSymbol, String> {

    /**
     * Récupère un CryptoSymbol par son symbole.
     * @param symbol - le symbole de la crypto-monnaie
     * @return - CryptoSymbol
     */
    Optional<CryptoSymbol> findBySymbolIgnoreCase(String symbol);

}
