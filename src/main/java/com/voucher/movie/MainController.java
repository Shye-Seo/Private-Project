package com.voucher.movie;

import java.io.IOException;
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

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// 소통-FAQ
	@GetMapping(value="/faq")
	public String faq() {
		return "faq";
	}
	
	// 전시-상설전시-체험존(3층)
	@GetMapping(value="/experience_zone_3rdFloor")
	public String experience_zone_3rdFloor() {
		return "experience_zone_3rdFloor";
	}

	// 전시-상설전시-체험존(4층)
	@GetMapping(value="/experience_zone_4thFloor")
	public String experience_zone_4thFloor() {
		return "experience_zone_4thFloor";
	}
	
	// 전시-상설전시-트릭아이뮤지엄(2층)
	@GetMapping(value="/trickEye_2ndFloor")
	public String trickEye_2ndFloor() {
		return "trickEye_2ndFloor";
	}
	
	// 전시-기획전시-홍영철전시관(1층)
	@GetMapping(value="/exhibition_1stFloor")
	public String exhibition_1stFloor() {
		return "exhibition_1stFloor";
	}
	
	// 전시-기획전시-명화극장(4층)
	@GetMapping(value="/exhibition_4thFloor")
	public String exhibition_4thFloor() {
		return "exhibition_4thFloor";
	}
	
	// 전시-특별전시-한국배우200사진전(2층)
	@GetMapping(value="/exhibition_actor")
	public String exhibition_actor() {
		return "exhibition_actor";
	}
	
	// 전시-온라인전시관-홍영철 온라인 전시관
	@GetMapping(value="/online_exhibition")
	public String online_exhibition() {
		return "online_exhibition";
	}
	
	// 전시-온라인전시관-공모전 전시관
	@GetMapping(value="/contest_exhibition")
	public String contest_exhibition() {
		return "contest_exhibition";
	}
	
	// 전시-교육-박물관 활동지
	@GetMapping(value="/activity_document")
	public String activity_document() {
		return "activity_document";
	}

	// 박물관 소개-인사말
	@GetMapping(value="/greetings")
	public String greetings() {
		return "greetings";
	}
	
	// 박물관 소개-조직도
	@GetMapping(value="/organization_chart")
	public String organization_chart() {
		return "organization_chart";
	}
	
	// 박물관 소개-연혁
	@GetMapping(value="/history")
	public String history() {
		return "history";
	}
	
	// 박물관 소개-시설현황
	@GetMapping(value="/facility_info")
	public String facility_info() {
		return "facility_info";
	}
	
	// 박물관 소개-시그니처
	@GetMapping(value="/signature")
	public String signature() {
		return "signature";
	}
	
	// 박물관 소개-기증안내
	@GetMapping(value="/donation_info")
	public String donation_info() {
		return "donation_info";
	}
	
}