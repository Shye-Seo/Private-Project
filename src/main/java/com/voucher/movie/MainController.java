package com.voucher.movie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	// 최초 진입 시 실행 페이지
	@GetMapping(value="/")
	public String home() { return "main"; }

	// 관람정보 페이지
	@GetMapping(value="/tour_info")
	public String tour_info() {
		return "tour_info";
	}

	// 전시 페이지
	@GetMapping(value="/exhibition")
	public String exhibition() {
		return "exhibition";
	}

	// 소통 페이지
	@GetMapping(value="/community")
	public String community() {
		return "community";
	}

	// 박물관 소개 페이지
	@GetMapping(value="/museum_info")
	public String museum_info() {
		return "museum_info";
	}

}