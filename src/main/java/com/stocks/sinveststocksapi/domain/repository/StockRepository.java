package com.stocks.sinveststocksapi.domain.repository;

import java.util.List;

import com.stocks.sinveststocksapi.domain.model.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByOrderByIdAsc();

}
