package com.springboot.web.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.web.dto.TradeDto;
import com.springboot.web.entity.BondEntity;
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
	 * @return List<TradeDto>
	 */
	public List<TradeDto> getTradeList() {
		return tradeEntityRepository.findAll().stream()	// DBよりtradeEntityのリストを取得する
				.filter(entity -> entity.getDeletedFlag() == 0)	// DeletedFlag==0(exist)のみを取得
				.map(entity ->  TradeDto.builder()	// TradeEntity -> TradeDtoに変換
						.bondCode(entity.getBondCode())
						.bondName(entity.getBondName())
						.tradeCode(entity.getTradeCode())
						.tradeOrder(entity.getTradeOrder())
						.buyOrSell(entity.getBuyOrSell())
						.executedAmount(entity.getExecutedAmount())
						.executedValue(entity.getExecutedValue())
						.executedDateTime(entity.getExecutedDateTime())
						.build())
				.collect(Collectors.toList());
	}

	@Transactional
	public void insertnewTrade(TradeDto tradeDto) {
		// TradeDtoからTradeEntityを作成する
		// tradeEntityをinsert
		tradeEntityRepository.saveAndFlush(this.transferTradeEntityfromTradeDto(tradeDto));
	}
	/** TradeDtoよりTradeEntityへ変換する(新規登録のとき)
	 *
	 * @param tradeDto
	 * @return
	 */
	private TradeEntity transferTradeEntityfromTradeDto(TradeDto tradeDto) {
		TradeEntity tradeEntity = new TradeEntity();
		int newTradeCode = this.calcNewTradeCode();
		tradeEntity.setTradeCode(newTradeCode);
		tradeEntity.setTradeOrder(newTradeCode);
		tradeEntity.setDeletedFlag(0);
		tradeEntity.setBondCode(tradeDto.getBondCode());
		tradeEntity.setBondName(this.findBondName(tradeDto.getBondCode()));
		tradeEntity.setBuyOrSell(tradeDto.getBuyOrSell());
		tradeEntity.setExecutedAmount(tradeDto.getExecutedAmount());
		tradeEntity.setExecutedValue(tradeDto.getExecutedValue());
		tradeEntity.setExecutedDate(LocalDate.now());
		tradeEntity.setExecutedDateTime(tradeDto.getExecutedDateTime());
		tradeEntity.setCreateUser(USER);
		tradeEntity.setModifyUser(USER);
		tradeEntity.setCreatedDateTime(LocalDateTime.now());
		tradeEntity.setModifiedDateTime(LocalDateTime.now());
		return tradeEntity;
	}

	/** TradeCodeの最大値+1を返す
	 *
	 * @return int
	 */
	private int calcNewTradeCode() {
		List<TradeDto> tradeDtoList = this.getTradeList();
		if(tradeDtoList.size()==0) {
			return 1;
		}
		else {
			// 最大値を取得し最大値+1をリターン
			return tradeDtoList.stream()
					.map(dto -> dto.getTradeCode())
					.max(Comparator.naturalOrder()).get() + 1;
		}
	}

	/** 銘柄コードで銘柄名称を検索し、銘柄名称を返すメソッド
	 * @param bondCode
	 * @return
	 */
	private String findBondName(String bondCode) {
		List<BondEntity> bondEntityList = bondEntityRepository.findByBondCode(bondCode);
		if(bondEntityList.size()==0) {
			return null;
		}
		return bondEntityList.stream()
				.filter(entity -> entity.getDeletedFlag()==0)
				.collect(Collectors.toList()).get(0)
				.getBondName();
	}

	/** 取引時刻を取引日時に変換する
	 * TODO:取引日をシステム日付に変更する
	 * @param executedTime HH:mm:ss.SSS
	 * @return LocalDateTime
	 */
	public LocalDateTime createDatetime(String executedTime) {
		String[] splitedTime = executedTime.split(":");
		String[] splitedSecond = {"00", "000"};
		if(splitedTime.length==3) {
			splitedSecond = splitedTime[2].split("\\.");
		}
		return LocalDateTime.now()
				.withHour(Integer.parseInt(splitedTime[0]))
				.withMinute(Integer.parseInt(splitedTime[1]))
				.withSecond(Integer.parseInt(splitedSecond[0]))
				.withNano(Integer.parseInt(splitedSecond[1]));
	}

}
