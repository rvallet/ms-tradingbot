package com.fts.ms_tradingbot.dao;

import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoSymbolRepository extends MongoRepository<CryptoSymbol, String> {

    Optional<CryptoSymbol> findBySymbolIgnoreCase(String symbol);

    @Query(value = "{}",
            fields = "{ 'symbol' : 1 }")
    List<CryptoSymbol> findUniqueSymbols();

}
