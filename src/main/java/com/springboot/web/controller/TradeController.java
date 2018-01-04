package com.springboot.web.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.web.dto.TradeDto;
import com.springboot.web.service.TradeService;

@Controller
@RequestMapping(value="/trade")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	/** 取引一覧を表示する
	 *
	 * @param model
	 * @return trade.html
	 */
	@RequestMapping(value="/list", params="tradelist", method=RequestMethod.POST )
	public String showTradeList(Model model) {
		// 取引一覧のfragmentを取得
		model.addAttribute("trademenu", "tradelist");
		// 取引のリストを取得し、取引のリストをモデルにセットし、取引のリストを一覧表示
		model.addAttribute("tradeDtoList", tradeService.getTradeList());
		return "trade";
	}
	/** 取引の新規登録画面を表示する
	 *
	 * @param model
	 * @return trade.html
	 */
	@RequestMapping(value="/input", params="inputtrade", method=RequestMethod.POST)
	public String showInputTrade(Model model) {
		model.addAttribute("trademenu", "inputtrade");
		return "trade";
	}

	/** 取引の新規登録を行う
	 *
	 * @param model
	 * @param bondCode
	 * @param buySell
	 * @param executedAmount
	 * @param executedValue
	 * @param executedTime
	 */
	@RequestMapping(value="/input", params="inputnewtrade", method=RequestMethod.POST)
	public void insertNewTrade(Model model,
			@RequestParam("bondcode")String bondCode,
			@RequestParam("buysell")String buySell,
			@RequestParam("tradeamount")int executedAmount,
			@RequestParam("tradevalue")double executedValue,
			@RequestParam("tradetime")String executedTime) {
		// TradeDtoに変換する
		TradeDto tradeDto = TradeDto.builder()
				.bondCode(bondCode)
				.buyOrSell(buySell)
				.executedAmount(new BigDecimal(executedAmount))
				.executedValue(new BigDecimal(executedValue))
				.executedDateTime(tradeService.createDatetime(executedTime))
				.build();
		// DBにinsertする
		tradeService.insertnewTrade(tradeDto);
	}

	/** 取引の訂正画面を表示する
	 *
	 * @param model
	 * @return trade.html
	 */
	@RequestMapping(value="/update", params="updatetrade", method=RequestMethod.POST)
	public String showUpdateTrade(Model model) {
		model.addAttribute("trademenu", "updatetrade");
		return "trade";
	}

	/** 取引の削除画面を表示する
	 *
	 * @param model
	 * @return trade.html
	 */
	@RequestMapping(value="/delete", params="deletetrade", method=RequestMethod.POST)
	public String showDeleteTrade(Model model) {
		model.addAttribute("trademenu", "deletetrade");
		return "trade";
	}
}
