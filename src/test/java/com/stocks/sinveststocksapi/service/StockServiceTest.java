package com.stocks.sinveststocksapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.stocks.sinveststocksapi.domain.model.Stock;
import com.stocks.sinveststocksapi.domain.repository.StockRepository;
import com.stocks.sinveststocksapi.domain.service.StockService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    Stock stock1;
    Stock stock2;
    List<Stock> stockList;

    @BeforeEach
    public void setUp() {
        stockList = new ArrayList<>();
        stock1 = new Stock();
        stock2 = new Stock();

        stock1.setId(1L);
        stock1.setMarketCap(1234L);
        stock1.setStockSymbol("ABCD");
        stock1.setStockName("STOCK1");

        stock2.setId(2L);
        stock2.setMarketCap(5678L);
        stock2.setStockSymbol("EFGH");
        stock2.setStockName("STOCK2");

        stockList.add(stock1);
        stockList.add(stock2);
    }

    @AfterEach
    public void tearDown() {
        stock1 = null;
        stock2 = null;
        stockList = null;
    }

    @Test
    public void deveListarStocks() {

        when(stockService.listar()).thenReturn(stockList);

        List<Stock> listaSalva = stockService.listar();

        assertFalse(listaSalva.isEmpty());
    }

    @Test
    public void deveBuscarStock() {

        when(stockRepository.findById(1L)).thenReturn(Optional.ofNullable(stock1));

        Stock stockTest = stockService.buscarOuFalhar(1L);

        assertNotNull(stockTest);
        assertEquals("STOCK1", stockTest.getStockName());
    }

    @Test
    public void deveAdicionarStock() {

        when(stockService.adicionar(any(Stock.class))).thenReturn(stock1);

        Stock stockSalva = stockService.adicionar(stock1);

        assertNotNull(stockSalva.getStockName());

    }

}
