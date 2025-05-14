package com.fts.ms_tradingbot.service.impl;

import com.fts.ms_tradingbot.dao.CryptoSymbolRepository;
import com.fts.ms_tradingbot.exception.CryptoSymbolNotFoundException;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.CryptoSymbolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoSymbolServiceImpl implements CryptoSymbolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoSymbolServiceImpl.class);

    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CryptoSymbol> getAllCryptoSymbols() {
        LOGGER.debug("getAllCryptoSymbols");
        return cryptoSymbolRepository.findAll();
    }

    @Override
    @Deprecated
    public CryptoSymbol getCryptoSymbolBySymbol(String symbol) {
        LOGGER.debug("getCryptoSymbolBySymbol : {}", symbol);
        return cryptoSymbolRepository.findAll().stream()
                .filter(cryptoSymbol -> cryptoSymbol.getSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElse(null);
    }

    @Override
    public CryptoSymbol findBySymbol(String symbol) {
        LOGGER.debug("findBySymbol : {}", symbol);
        return cryptoSymbolRepository.findBySymbolIgnoreCase(symbol)
                .orElseThrow(() -> new CryptoSymbolNotFoundException(symbol));
    }

    @Override
    public Optional<CryptoSymbol> findById(String id) {
        LOGGER.debug("findById : {}", id);
        return cryptoSymbolRepository.findById(id);
    }

    @Override
    public List<String> getUniqueSymbolsInAlphabeticalOrder() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("symbol").first("symbol").as("symbol"), // Groupement par symbole
                Aggregation.sort(Sort.by(Sort.Order.asc("symbol"))) // Tri par ordre alphabétique
        );

        AggregationResults<CryptoSymbol> results = mongoTemplate.aggregate(aggregation, CryptoSymbol.class, CryptoSymbol.class);
        return results.getMappedResults().stream()
                .map(CryptoSymbol::getSymbol)
                .collect(Collectors.toList());
    }

    @Override
    public CryptoSymbol save(CryptoSymbol cryptoSymbol) {
        CryptoSymbol savedCryptoSymbol = cryptoSymbolRepository.save(cryptoSymbol);
        LOGGER.debug("save CryptoSymbol : {}", savedCryptoSymbol.getId());
        return savedCryptoSymbol;
    }

}
