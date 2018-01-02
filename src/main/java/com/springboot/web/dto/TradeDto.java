package com.springboot.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TradeDto {

	private String bondCode;
	private String bondName;
	private int tradeCode;
	private int tradeOrder;
	private String buyOrSell;
	private BigDecimal executedAmount;
	private BigDecimal executedValue;
	private LocalDateTime executedDateTime;

}
