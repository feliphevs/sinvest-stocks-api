package com.stocks.sinveststocksapi.domain.service;

import java.util.ArrayList;
import java.util.List;

import com.stocks.sinveststocksapi.domain.exception.StockNaoEncontradaException;
import com.stocks.sinveststocksapi.domain.model.Stock;
import com.stocks.sinveststocksapi.domain.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> listar() {
        return stockRepository.findAllByOrderByIdAsc();
    }

    public Stock adicionar(Stock stock) {
        return stockRepository.save(stock);
    }

    public void excluir(Long stockId) {
        try {
            stockRepository.deleteById(stockId);
        } catch (EmptyResultDataAccessException e) {
            throw new StockNaoEncontradaException(stockId);
        }
    }

    public Stock buscarOuFalhar(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNaoEncontradaException(stockId));
    }
}
