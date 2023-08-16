package com.voucher.movie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	// 최초 진입 시 실행 페이지
	@GetMapping(value="/")
	public String home() { return "main"; }
	
	// 관리자 로그인 페이지
	@GetMapping(value="/admin")
	public String admin() { return "admin"; }

	// 관람정보-관람안내 페이지
	@GetMapping(value="/viewing_guidance")
	public String guidance() {
		return "viewing_guidance";
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