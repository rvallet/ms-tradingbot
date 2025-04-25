package com.fts.ms_tradingbot.ws;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fts.ms_tradingbot.ApiRegistration;
import com.fts.ms_tradingbot.TestUtils;
import com.fts.ms_tradingbot.dao.CryptoSymbolRepository;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@AutoConfigureMockMvc
public class CryptoSymbolApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected TestUtils testUtils;

    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;

    @Before
    public void setUp() {
        // Suppression de toutes les données de test
        cryptoSymbolRepository.deleteAll();
        // Création des données de test
        List<CryptoSymbol> mockSymbols = Arrays.asList(
        new CryptoSymbol("BTCUSDC", "Bitcoin", true, null, null, null, null, null),
        new CryptoSymbol("ETHUSDC", "Ethereum", true, null, null, null, null, null)
        );
        // Enregistrement des données de test dans la base de données
        cryptoSymbolRepository.saveAll(mockSymbols);
    }

    @Test
    public void testGetAllCryptoSymbols() throws Exception {

        // @formatter:off
        MvcResult mvcResult = mockMvc.perform(get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        List<CryptoSymbol> responseSymbols = testUtils.convertJsonToObjectList(contentAsString, CryptoSymbol.class);

        Assert.assertNotNull(responseSymbols);
        Assert.assertFalse(responseSymbols.isEmpty());
        Assert.assertEquals(2, responseSymbols.size());
        Assert.assertEquals("BTCUSDC", responseSymbols.getFirst().getSymbol());
        Assert.assertEquals("Bitcoin", responseSymbols.getFirst().getName());
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

        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        CryptoSymbol responseSymbol = testUtils.convertStringToObject(contentAsString, CryptoSymbol.class);

        Assert.assertNotNull(responseSymbol);
        Assert.assertEquals(symbol, responseSymbol.getSymbol());
        Assert.assertEquals("Bitcoin", responseSymbol.getName());

    }

}