package com.stocks.sinveststocksapi.integration;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import com.stocks.sinveststocksapi.domain.model.Stock;
import com.stocks.sinveststocksapi.domain.repository.StockRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class StockTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testGetStock() {

        Stock atual = stockRepository.findById(1L).get();
        Stock esperada = new Stock();
        esperada.setId(1L);
        esperada.setMarketCap(12345L);
        esperada.setStockSymbol("BEEF");
        esperada.setStockName("MINERVA");

        Assertions.assertEquals(esperada, atual);

    }

    @Test
    public void testDeleteStock() {
        stockRepository.deleteById(3L);
        Stock atual = stockRepository.findById(3L).orElse(null);

        assertNull(atual);
    }

    @Test
    public void testListStocks() {
        List<Stock> listaVazia = new ArrayList<>();
        List<Stock> listaStocks = stockRepository.findAll();

        System.out.println(listaStocks);

        assertNotEquals(listaVazia, listaStocks);
    }

}
