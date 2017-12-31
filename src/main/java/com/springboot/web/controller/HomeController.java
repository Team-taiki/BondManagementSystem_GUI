package com.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	/** ホーム画面を表示
	 *
	 * @return home.html
	 */
	@RequestMapping(value="/home")
	public String home() {
		return "/home";
	}

	/** 取引管理画面に遷移する
	 *
	 * @return trade.html
	 */
	@RequestMapping(value="/trade", params="tradebtn", method=RequestMethod.POST)
	public String showTradeTop() {
		return "/trade";
	}

	@RequestMapping(value="/position", params="positionbtn", method=RequestMethod.POST)
	public String showPositionTop() {
		return "/position";
	}

	@RequestMapping(value="/marketvalue", params="marketvbtn", method=RequestMethod.POST)
	public String showMarketvalueTop() {
		return "/marketvalue";
	}

	@RequestMapping(value="/closing", params="closingbtn", method=RequestMethod.POST)
	public String showClosingTop() {
		return "/closing";
	}

	@RequestMapping(value="/bond", params="bondbtn", method=RequestMethod.POST)
	public String showBondTop() {
		return "/bond";
	}
}
