package com.fts.ms_tradingbot.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fts.ms_tradingbot.ApiRegistration;
import com.fts.ms_tradingbot.TestUtils;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles(profiles = { "test" })
public class CryptoSymbolApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private MongoOperations mongoOps;

    @Autowired
    private TestUtils testUtils;

    @AfterEach
    public void tearDown() {
        // Suppression des données de test
        mongoOps.dropCollection(CryptoSymbol.class);
    }

    @BeforeEach
    public void setUp() {
        // Création des données de test
        List<CryptoSymbol> mockSymbols = Arrays.asList(
        new CryptoSymbol("BTCUSDC", "Bitcoin", true, 1.0, 2.0, 5.0, null, null),
        new CryptoSymbol("ETHUSDC", "Ethereum", true, 3.0, 4.0, 10.0, null, null)
        );
        // Enregistrement des données de test dans la base de données
        mongoOps.insert(mockSymbols, CryptoSymbol.class);
    }

    @Test
    public void testGetAllCryptoSymbols() throws Exception {

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<CryptoSymbol> responseSymbols = testUtils.convertJsonToObjectList(contentAsString, CryptoSymbol.class);

        assertNotNull(responseSymbols);
        assertFalse(responseSymbols.isEmpty());
        assertEquals(2, responseSymbols.size());
        assertEquals("BTCUSDC", responseSymbols.get(0).getSymbol());
        assertEquals("Bitcoin", responseSymbols.get(0).getName());
    }

    @Test
    public void testGetCryptoSymbolBySymbol() throws Exception {

        String symbol = "BTCUSDC";

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + "/{symbol}", symbol)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value(symbol))
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CryptoSymbol responseSymbol = testUtils.convertStringToObject(contentAsString, CryptoSymbol.class);

        assertNotNull(responseSymbol);
        assertEquals(symbol, responseSymbol.getSymbol());
        assertEquals("Bitcoin", responseSymbol.getName());

    }

}