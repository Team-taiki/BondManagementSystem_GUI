package com.springboot.web.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name="TRADE")
@Data
public class TradeEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	@Column(name="TRADE_CODE")
	private int tradecode;
	@Column(name="TRADE_ORDER")
	private int tradeorder;
	@Column(name="DELETED_FLAG")
	private int deletedflag;	// exist=0, deleted=1
	@Column(name="BOND_CODE")
	private String bondcode;
	@Column(name="BOND_NAME")
	private String bondname;
	@Column(name="BUY_OR_SELL")
	private String buyorsell;
	@Column(name="EXECUTED_AMOUNT")
	private BigDecimal executedamount;
	@Column(name="EXECUTED_VALUE")
	private BigDecimal executedvalue;
	@Column(name="EXECUTED_DATETIME")
	private LocalDateTime executeddatetime;
	@Column(name="CREATE_USER")
	private String createuser;
	@Column(name="MODIFY_USER")
	private String modifyuser;
	@Column(name="CREATED_TIME")
	private LocalDateTime createddatetime;
	@Column(name="MODIFIED_TIME")
	private LocalDateTime modifieddatetime;
}
