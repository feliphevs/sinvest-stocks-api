package com.stocks.sinveststocksapi.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {

    private Long marketCap;
    private String stockSymbol;
    private String stockName;
    private BigDecimal askMin;
    private BigDecimal askMax;
    private BigDecimal bidMin;
    private BigDecimal bidMax;

}
