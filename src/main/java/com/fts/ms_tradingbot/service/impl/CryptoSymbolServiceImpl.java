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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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

        // Aggregation classique : on construit une requête d'agrégation MongoDB plus ou moins complexe
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("symbol").first("symbol").as("symbol"), // Groupement par symbole
                Aggregation.sort(Sort.by(Sort.Order.asc("symbol"))) // Tri par ordre alphabétique
        );

        AggregationResults<CryptoSymbol> results = mongoTemplate.aggregate(aggregation, CryptoSymbol.class, CryptoSymbol.class);
        return results.getMappedResults().stream()
                .map(CryptoSymbol::getSymbol)
                .collect(Collectors.toList());
    }

    /**
     * Version 2 de la méthode getUniqueSymbolsInAlphabeticalOrder
     * Utilisation de findDistinct pour récupérer les symboles uniques
     * @return - Liste des symboles uniques
     */
    public List<String> getUniqueSymbolsInAlphabeticalOrderVersion2() {
        // Utilisation de findDistinct pour récupérer directement les symboles uniques
        return mongoTemplate
                .findDistinct(new Query(), "symbol", CryptoSymbol.class, String.class)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * getCryptoSymbolsWithCustomConditions - Récupère les symboles de crypto-monnaies
     * qui respectent certaines conditions personnalisées.
     *
     * @return - Liste des symboles de crypto-monnaies
     */
    public List<CryptoSymbol> getCryptoSymbolsWithCustomConditions() {
        // On crée une instance de Calendar pour manipuler les dates
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7); // On soustrait 7 jours à la date actuelle
        Date date7DaysAgo = cal.getTime(); // On obtient la date 7 jours avant aujourd'hui

        // On crée les critères de recherche
        Criteria criteria1 = Criteria.where("symbol").exists(true); // On s'assure que le champ "symbol" existe
        Criteria criteria2 = Criteria.where("is_active").is(true); // On s'assure que le champ "is_active" est vrai
        Criteria criteria3 = Criteria.where("start_price").gt(0); // On s'assure que le champ "start_price" est supérieur à 0
        Criteria criteria4 = Criteria.where("current_price").gt(0); // On s'assure que le champ "current_price" est supérieur à 0
        Criteria criteria5 = Criteria.where("date").gte(date7DaysAgo); // On s'assure que le champ "date" est supérieur ou égal à la date 7 jours avant aujourd'hui
        Criteria criteria3Or4 = new Criteria().orOperator(criteria3, criteria4); // On combine les critères 3 et 4 avec un "ou" logique

        // On combine les critères à passer dans la requête
        Criteria combinedCriteria = new Criteria();
        combinedCriteria.andOperator(criteria1, criteria2, criteria3Or4, criteria5);

        // On crée la requête avec les critères combinés
        Query query = new Query(combinedCriteria);

        // On exécute la requête
        return mongoTemplate.find(query, CryptoSymbol.class);
    }

    @Override
    public CryptoSymbol save(CryptoSymbol cryptoSymbol) {
        CryptoSymbol savedCryptoSymbol = cryptoSymbolRepository.save(cryptoSymbol);
        LOGGER.debug("save CryptoSymbol : {}", savedCryptoSymbol.getId());
        return savedCryptoSymbol;
    }

}
