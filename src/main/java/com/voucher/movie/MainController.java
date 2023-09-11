package com.voucher.movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.voucher.movie.admin.AdminService;
import com.voucher.movie.admin.PopupVO;
import com.voucher.movie.reservation.ClosedVO;

@Controller
public class MainController {
	
	@Inject
	WebService webService;
	
	List<PopupVO> popup_list;

	// 최초 진입 시 실행 페이지
	@GetMapping(value="/")
	public String home(ModelMap model) throws Exception {
		
		// 오늘 날짜
	    LocalDate now = LocalDate.now();
	    Calendar time = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    
	    Calendar cal = Calendar.getInstance();
	    String today = dateFormat.format(cal.getTime());
	    System.out.println("today = "+today);
	    
		int popup_cnt = webService.today_popup_cnt(today);
		if(popup_cnt != 0) {
			popup_list = webService.get_today_popup(today);
//			for(PopupVO vo : popup_list) {
//				vo.setNo();
//			}
			model.addAttribute("popup_list", popup_list);
		}
		
		return "main"; 
	}
	
	// 관리자 로그인 페이지
	@GetMapping(value="/admin")
	public String admin() { return "admin"; }

	// 이용안내-관람안내
	@GetMapping(value="/viewing_guidance")
	public String guidance() {
		return "viewing_guidance";
	}
	
	// 이용안내-찾아오시는 길
	@GetMapping(value="/map")
	public String map() {
		return "map";
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