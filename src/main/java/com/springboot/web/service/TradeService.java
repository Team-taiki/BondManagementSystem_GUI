package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.TradeDto;
import com.springboot.web.repository.TradeEntityRepository;

@Service
public class TradeService {

	@Autowired
	private TradeEntityRepository tradeEntityRrepository;



	/** 変更可能な取引の一覧を取得する
	 *   RepositoryからList<TradeEntity>を取得し、List<TradeDto>に変換しreturn
	 * @return　List<TradeDto>
	 */
	public List<TradeDto> getTradeList() {
		return tradeEntityRrepository.findAll().stream()	// DBよりtradeEntityのリストを取得する
				.filter(entity -> entity.getDeletedflag() == 0)	// DeletedFlag==0(exist)のみを取得
				.map(entity -> new TradeDto(	// TradeEntity -> TradeDtoに変換
						entity.getBondcode(),
						entity.getBondname(),
						entity.getTradecode(),
						entity.getTradeorder(),
						entity.getBuyorsell(),
						entity.getExecutedamount(),
						entity.getExecutedvalue(),
						entity.getExecuteddatetime()))
				.collect(Collectors.toList());
	}

}
