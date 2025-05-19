package com.fts.ms_tradingbot.ws;

import com.fts.ms_tradingbot.TestUtils;
import com.fts.ms_tradingbot.pojo.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.fts.ms_tradingbot.mock.OrderMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles(profiles = { "test" })
public class OrderApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private MongoOperations mongoOps;

    @Autowired
    private TestUtils testUtils;

    @AfterEach
    public void tearDown() {
        // Suppression des données de test
        mongoOps.dropCollection(Order.class);
    }

    @BeforeEach
    public void setUp() {
        // Création des données de test
        List<Order> mockOrders = getOrdersMock();

        // Enregistrement des données de test dans la base de données
        mongoOps.insert(mockOrders, Order.class);
    }

    @Test
    public void testSetUp() {
        // Vérification de la création des données de test
        List<Order> mockOrders = getOrdersMock();
        List<Order> orders = mongoOps.findAll(Order.class);

        // Vérification que les données de test ont été créées correctement
        assertNotNull(orders, "La liste des commandes récupérée ne doit pas être null");
        assertEquals(mockOrders.size(), orders.size(), "Le nombre de commandes dans la base de données doit correspondre au nombre de commandes mockées");

        // Vérification que chaque commande mockée correspond à la commande dans la base de données
        for (int i = 0; i < mockOrders.size(); i++) {
            Order mockOrder = mockOrders.get(i);
            Order order = orders.get(i);

            assertEquals(mockOrder.getCryptoSymbolId(), order.getCryptoSymbolId(), "L'ID de la crypto-monnaie doit correspondre");
            assertEquals(mockOrder.getPurchaseData().getBinanceClientOrderId(), order.getPurchaseData().getBinanceClientOrderId(), "L'ID de commande Binance d'achat doit correspondre");
            assertEquals(mockOrder.getSaleData().getBinanceClientOrderId(), order.getSaleData().getBinanceClientOrderId(), "L'ID de commande Binance de vente doit correspondre");
        }

    }

}
