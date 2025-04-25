package com.fts.ms_tradingbot.dao;

import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CryptoSymbolRepository extends MongoRepository<CryptoSymbol, String> {

    List<CryptoSymbol> findAll();

}
