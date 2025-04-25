package com.fts.ms_tradingbot.ws;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fts.ms_tradingbot.ApiRegistration;
import com.fts.ms_tradingbot.TestUtils;
import com.fts.ms_tradingbot.pojo.CryptoSymbol;
import com.fts.ms_tradingbot.service.impl.CryptoSymbolServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@AutoConfigureMockMvc
public class CryptoSymbolApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected TestUtils testUtils;

    @Mock
    private CryptoSymbolServiceImpl cryptoSymbolService;

    @InjectMocks
    private CryptoSymbolApi cryptoSymbolApi;

    @Before
    public void setUp() {
    }

    @Test
    public void testGetAllCryptoSymbols() throws Exception {
        mockMvc.perform(get(ApiRegistration.REST_PREFIX + ApiRegistration.REST_CRYPTO_SYMBOLS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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