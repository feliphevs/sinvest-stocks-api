package com.stocks.sinveststocksapi.api.controller;

import java.util.List;

import com.stocks.sinveststocksapi.api.dto.StockAskBidDto;
import com.stocks.sinveststocksapi.api.dto.StockDto;
import com.stocks.sinveststocksapi.api.dto.StockRespostaDto;
import com.stocks.sinveststocksapi.core.ModelMapperUtils;
import com.stocks.sinveststocksapi.domain.model.Stock;
import com.stocks.sinveststocksapi.domain.repository.StockRepository;
import com.stocks.sinveststocksapi.domain.service.StockService;

import org.springframework.http.HttpStatus;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<StockRespostaDto> listar() {
        return ModelMapperUtils.mapAll(stockRepository.findAllByOrderByIdAsc(), StockRespostaDto.class);
    }

    @GetMapping("/{stockId}")
    public StockRespostaDto buscar(@PathVariable Long stockId) {
        return ModelMapperUtils.map(stockService.buscarOuFalhar(stockId), StockRespostaDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockRespostaDto adicionar(@RequestBody StockDto stockDto) {
        Stock stockSalva = stockService.adicionar(ModelMapperUtils.map(stockDto, Stock.class));
        return ModelMapperUtils.map(stockSalva, StockRespostaDto.class);
    }

    @PutMapping("/{stockId}")
    public StockRespostaDto atualizar(@PathVariable Long stockId,
            @RequestBody StockDto stockDto) {

        Stock stockAtual = stockService.buscarOuFalhar(stockId);

        BeanUtils.copyProperties(stockDto, stockAtual);

        return ModelMapperUtils.map(stockService.adicionar(stockAtual), StockRespostaDto.class);
    }

    @PutMapping("/updateaskbid/{stockId}")
    public StockRespostaDto atualizar(@PathVariable Long stockId,
            @RequestBody StockAskBidDto stockAskBidDto) {

        Stock stockAtual = stockService.buscarOuFalhar(stockId);

        BeanUtils.copyProperties(stockAskBidDto, stockAtual);

        return ModelMapperUtils.map(stockService.adicionar(stockAtual), StockRespostaDto.class);
    }

    @DeleteMapping("/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long stockId) {
        stockService.excluir(stockId);
    }
}
