package com.fts.ms_tradingbot.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.fts.ms_tradingbot.mock.CryptoSymbolMock.*;

import com.fts.ms_tradingbot.ApiRegistration;
import com.fts.ms_tradingbot.TestUtils;
import org.springframework.data.mongodb.core.index.Index;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //TODO Remy
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
        // Création manuelle de l'index d'unicité du champ "symbol" --> @Indexed(unique = true)
        mongoOps.indexOps(CryptoSymbol.class).ensureIndex(
                new Index().on("symbol", Sort.Direction.ASC).unique());

        // Création des données de test
        List<CryptoSymbol> mockSymbols = getCryptoSymbolsMock();

        // Enregistrement des données de test dans la base de données
        mongoOps.insert(mockSymbols, CryptoSymbol.class);
    }

    @Test
    public void testGetAllCryptoSymbols() throws Exception {

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<CryptoSymbol> responseSymbols = testUtils.convertJsonToObjectList(contentAsString, CryptoSymbol.class);

        assertNotNull(responseSymbols);
        assertFalse(responseSymbols.isEmpty());
        assertEquals(3, responseSymbols.size());
        assertEquals(BTCUSDC, responseSymbols.get(0).getSymbol());
        assertEquals("Bitcoin", responseSymbols.get(0).getName());
    }

    @Test
    public void testGetCryptoSymbolBySymbol() throws Exception {

        String symbol = BTCUSDC;

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.SYMBOL + "/{symbol}", symbol)
                        .contentType(MediaType.APPLICATION_JSON)
                )
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

    @Test
    public void testGetCryptoSymbolBySymbolNotFound() throws Exception {

        String symbol = "INVALID_SYMBOL";

        // @formatter:off
        mockMvc.perform(
                get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.SYMBOL + "/{symbol}", symbol)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
        // @formatter:on

    }

    @Test
    public void testgetUniqueSymbolsInAlphabeticalOrder() throws Exception {

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + "/unique")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> responseSymbols = testUtils.convertJsonToObjectList(contentAsString, String.class);

        assertNotNull(responseSymbols);
        assertFalse(responseSymbols.isEmpty());
        assertEquals(3, responseSymbols.size());
        assertEquals(ADAUSDC, responseSymbols.get(0));
        assertEquals(BTCUSDC, responseSymbols.get(1));
        assertEquals(ETHUSDC, responseSymbols.get(2));
    }

    @Test
    public void testSaveCryptoSymbol() throws Exception {

        CryptoSymbol newCryptoSymbol = getCryptoSymbolMock();

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                post(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUtils.convertObjectToJson(newCryptoSymbol))
                )
                .andExpect(status().isCreated())
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CryptoSymbol responseSymbol = testUtils.convertStringToObject(contentAsString, CryptoSymbol.class);

        assertNotNull(responseSymbol);
        assertEquals(newCryptoSymbol.getSymbol(), responseSymbol.getSymbol());
        assertEquals(newCryptoSymbol.getName(), responseSymbol.getName());
    }

    @Test
    public void testSaveCryptoSymbolDuplicate() throws Exception {

        CryptoSymbol newCryptoSymbol = getCryptoSymbolMock();
        newCryptoSymbol.setSymbol(BTCUSDC);

        // @formatter:off
        mockMvc.perform(
                post(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUtils.convertObjectToJson(newCryptoSymbol))
                )
                .andExpect(status().isConflict());
        // @formatter:on

    }

    @Test
    public void testFindCryptoSymbolById() throws Exception {

        String id = Objects.requireNonNull(mongoOps.findOne(
                query(where("symbol").is(BTCUSDC)),
                CryptoSymbol.class)).getId();

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(
                get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.ID + "/{cryptoid}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CryptoSymbol responseSymbol = testUtils.convertStringToObject(contentAsString, CryptoSymbol.class);

        assertNotNull(responseSymbol);
        assertEquals(id, responseSymbol.getId());
    }

    @Test
    public void testFindCryptoSymbolByIdNotFound() throws Exception {

        String id = "INVALID_ID";

        // @formatter:off
        mockMvc.perform(
                get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.ID + "/{cryptoid}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
        // @formatter:on

    }

    @Test
    public void testDeleteById() throws Exception {

        String id = Objects.requireNonNull(mongoOps.findOne(
                query(where("symbol").is(BTCUSDC)),
                CryptoSymbol.class)).getId();

        // @formatter:off
        mockMvc.perform(
                delete(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS + ApiRegistration.ID + "/{cryptoid}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
        // @formatter:on
    }



}