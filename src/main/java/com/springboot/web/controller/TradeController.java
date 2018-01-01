package com.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
