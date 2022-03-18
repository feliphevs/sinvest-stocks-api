package com.stocks.sinveststocksapi.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockAskBidDto {

    private BigDecimal askMin;
    private BigDecimal askMax;
    private BigDecimal bidMin;
    private BigDecimal bidMax;

}
