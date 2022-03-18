package com.stocks.sinveststocksapi.domain.exception;

public class StockNaoEncontradaException extends NegocioException {
    public StockNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public StockNaoEncontradaException(Long userId) {
        this(String.format("Não existe uma stock com código %d", userId));
    }
}
