package com.springboot.web.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.web.dto.TradeDto;
import com.springboot.web.entity.TradeEntity;
import com.springboot.web.repository.BondEntityRepository;
import com.springboot.web.repository.TradeEntityRepository;

@Service
public class TradeService {

	@Autowired
	private TradeEntityRepository tradeEntityRepository;
	@Autowired
	private BondEntityRepository bondEntityRepository;

	private final String USER = "root";

	/** 変更可能な取引の一覧を取得する
	 *   RepositoryからList<TradeEntity>を取得し、List<TradeDto>に変換しreturn
	 * @return　List<TradeDto>
	 */
	public List<TradeDto> getTradeList() {
		return tradeEntityRepository.findAll().stream()	// DBよりtradeEntityのリストを取得する
				.filter(entity -> entity.getDeletedFlag() == 0)	// DeletedFlag==0(exist)のみを取得
				.map(entity -> new TradeDto(	// TradeEntity -> TradeDtoに変換
						entity.getBondCode(),
						entity.getBondName(),
						entity.getTradeCode(),
						entity.getTradeOrder(),
						entity.getBuyOrSell(),
						entity.getExecutedAmount(),
						entity.getExecutedValue(),
						entity.getExecutedDateTime()))
				.collect(Collectors.toList());
	}

	@Transactional
	public void insertnewTrade(TradeDto tradeDto) {
		// TradeDtoからTradeEntityを作成する
		TradeEntity tradeEntity = this.transferTradeEntityfromTradeDto(tradeDto);
		// tradeEntityをinsert
		tradeEntityRepository.saveAndFlush(tradeEntity);
	}
	/** TradeDtoよりTradeEntityへ変換する(新規登録のとき)
	 *
	 * @param tradeDto
	 * @return
	 */
	private TradeEntity transferTradeEntityfromTradeDto(TradeDto tradeDto) {
		int newTradeCode = this.calcNewTradeCode();
		return TradeEntity.builder()
				.tradeCode(newTradeCode)
				.tradeOrder(newTradeCode)
				.deletedFlag(0)	// exist
				.bondCode(tradeDto.getBondCode())
				.bondName(this.findBondName(tradeDto.getBondCode()))
				.buyOrSell(tradeDto.getBuyOrSell())
				.executedAmount(tradeDto.getExecutedAmount())
				.executedValue(tradeDto.getExecutedValue())
				.executedDateTime(tradeDto.getExecutedDateTime())
				.createUser(USER)
				.modifyUser(USER)
				.createdDateTime(LocalDateTime.now())
				.modifiedDateTime(LocalDateTime.now())
				.build();
	}

	/** TradeCodeの最大値+1を返す
	 *
	 * @return int
	 */
	private int calcNewTradeCode() {
		List<TradeDto> tradeDtoList = this.getTradeList();
		if(tradeDtoList.size()==0) {
			return 0;
		}
		else {
			// 最大値を取得し最大値+1をリターン
			return tradeDtoList.stream()
					.map(dto -> dto.getTradeCode())
					.max(Comparator.naturalOrder()).get() + 1;
		}
	}

	/** 銘柄コードで銘柄名称を検索し、銘柄名称を返すメソッド
	 *
	 * @param bondcode
	 * @return
	 */
	private String findBondName(String bondcode) {
		return bondEntityRepository.findByBondCode(bondcode).stream()
				.filter(entity -> entity.getDeletedFlag()==1)
				.collect(Collectors.toList()).get(0).getBondName();
	}

	/** 取引時刻を取引日時に変換する
	 * TODO:取引日をシステム日付に変更する
	 * @param executedtime HH:mm:SSS
	 * @return LocalDateTime
	 */
	public LocalDateTime createDatetime(String executedtime) {
		String[] splitedtime = executedtime.split(":");
		String[] splitedsecond = splitedtime[2].split("\\.");
		return LocalDateTime.now()
				.withHour(Integer.parseInt(splitedtime[0]))
				.withMinute(Integer.parseInt(splitedtime[1]))
				.withSecond(Integer.parseInt(splitedsecond[0]))
				.withNano(Integer.parseInt(splitedsecond[1]));
	}

}
