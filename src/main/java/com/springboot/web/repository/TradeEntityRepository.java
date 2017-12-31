package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.web.entity.TradeEntity;

@Repository
public interface TradeEntityRepository extends JpaRepository<TradeEntity, Integer> {

}
