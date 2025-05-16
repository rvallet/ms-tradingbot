package com.fts.ms_tradingbot.service;

import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface CryptoSymbolService {

    /**
     * getAllCryptoSymbols - Récupérer tous les symboles de crypto-monnaies
     *
     * @return - Liste de CryptoSymbol
     */
    List<CryptoSymbol> getAllCryptoSymbols();

    /**
     * getCryptoSymbolBySymbol - Nommage conventionnel JAVA, mais
     * ne correspond pas à la convention de nommage Spring Data
     * @param symbol - le symbole de la crypto-monnaie
     * @return - CryptoSymbol
     */
    @Deprecated
    CryptoSymbol getCryptoSymbolBySymbol(String symbol);

    /**
     * findBySymbol - Nommage conventionnel Spring Data,
     * la requête est donc comprise et générée automatiquement par Spring Data
     * @param symbol - le symbole de la crypto-monnaie
     * @return - CryptoSymbol
     */
    CryptoSymbol findBySymbol(String symbol);

    /**
     * findById - Nommage conventionnel Spring Data,
     * la requête est donc comprise et générée automatiquement par Spring Data
     * @param id - l'identifiant Mongo du CryptoSymbol
     * @return - CryptoSymbol
     */
    Optional<CryptoSymbol> findById(String id);

    /**
     * Liste des symboles uniques dans l'ordre alphabétique.
     * Aggregation MongoDB custom pour récupérer les symboles uniques
     * @return - Liste des symboles uniques
     */
    List<String> getUniqueSymbolsInAlphabeticalOrder();

    /**
     * Enregistrer un CryptoSymbol
     * Création ou mise à jour d'un CryptoSymbol selon la présence ou non de l'identifiant
     * @param cryptoSymbol - le CryptoSymbol à enregistrer
     * @return - le CryptoSymbol après enregistrement
     */
    CryptoSymbol save(CryptoSymbol cryptoSymbol);

}
