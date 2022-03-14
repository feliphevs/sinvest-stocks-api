package com.stocks.sinveststocksapi.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.sinveststocksapi.api.dto.StockDto;
import com.stocks.sinveststocksapi.api.dto.StockRespostaDto;
import com.stocks.sinveststocksapi.core.ModelMapperUtils;
import com.stocks.sinveststocksapi.domain.model.Stock;
import com.stocks.sinveststocksapi.domain.repository.StockRepository;
import com.stocks.sinveststocksapi.domain.service.StockService;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
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

    @PatchMapping("/{stockId}")
    public StockRespostaDto atualizarParcial(@PathVariable Long stockId,
            @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Stock stockAtual = stockService.buscarOuFalhar(stockId);

        merge(campos, stockAtual, request);

        StockDto stockConvertida = ModelMapperUtils.map(stockAtual, StockDto.class);

        return atualizar(stockId, stockConvertida);
    }

    private void merge(Map<String, Object> dadosOrigem, Stock stockDestino,
            HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Stock stockOrigem = objectMapper.convertValue(dadosOrigem, Stock.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Stock.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, stockOrigem);

                ReflectionUtils.setField(field, stockDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @DeleteMapping("/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long stockId) {
        stockService.excluir(stockId);
    }
}
