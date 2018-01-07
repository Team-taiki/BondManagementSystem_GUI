package com.springboot.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="TRADE")
@Data
@NoArgsConstructor
public class TradeEntity implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TRADE_ID")
	private Integer id;
	@Column(name="TRADE_CODE")
	private int tradeCode;
	@Column(name="TRADE_ORDER")
	private int tradeOrder;
	@Column(name="DELETED_FLAG")
	private int deletedFlag;	// exist=0, deleted=1
	@Column(name="BOND_CODE")
	private String bondCode;
	@Column(name="BOND_NAME")
	private String bondName;
	@Column(name="BUY_OR_SELL")
	private String buyOrSell;
	@Column(name="EXECUTED_AMOUNT")
	private BigDecimal executedAmount;
	@Column(name="EXECUTED_VALUE")
	private BigDecimal executedValue;
	@Column(name="EXECUTED_DATETIME")
	private LocalDateTime executedDateTime;
	@Column(name="CREATE_USER")
	private String createUser;
	@Column(name="MODIFY_USER")
	private String modifyUser;
	@Column(name="CREATED_TIME")
	private LocalDateTime createdDateTime;
	@Column(name="MODIFIED_TIME")
	private LocalDateTime modifiedDateTime;
}
