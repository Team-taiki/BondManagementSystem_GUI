package com.springboot.web.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="bond")
@Data
@Builder
public class BondEntity {


	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  @Column(name="id")
	  private Integer id;
	  @Column(name="deleted_flag")
	  private int deletedFlag;
	  @Column(name="bond_code")
	  private String bondCode;
	  @Column(name="bond_name")
	  private String bondName;
	  @Column(name="buy_sell_unit")
	  private BigDecimal buySellUnit;
	  @Column(name="issue_date")
	  private LocalDate issueDate;
	  @Column(name="maturity")
	  private LocalDate maturity;
	  @Column(name="coupon_rate")
	  private BigDecimal couponRate;
	  @Column(name="coupon_count")
	  private int couponCount;
	  @Column(name="country_code")
	  private Integer countryId;
	  @Column(name="create_user")
	  private String createUser;
	  @Column(name="modify_user")
	  private String modifyUser;
	  @Column(name="created_time")
	  private LocalDateTime createdDateTime;
	  @Column(name="modified_time")
	  private LocalDateTime modifiedDateTime;
}
