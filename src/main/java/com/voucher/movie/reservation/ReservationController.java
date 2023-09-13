package com.voucher.movie.reservation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.voucher.movie.admin.AdminService;
import com.voucher.movie.aws.AwsS3Service;

import jakarta.servlet.http.HttpServletRequest;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
public class ReservationController {
	
	@Inject
    private TemporaryService tempService;
	
	@Inject
    private GroupService groupService;
	
	@Inject
	AdminService adminService;
	
	@Autowired
	AwsS3Service s3Service;
	
	List<ClosedVO> closed_list;
	List<TimeVO> time_list;
	List<GroupVO> res_list; //단체예약
	List<FacilitiesVO> res_f_list; //대관예약
	
	// 예약-개인(안내페이지)
	@GetMapping(value="/reservation_personal")
	public String reservation_personal() {
		return "reservation_personal";
	}
		
	// 예약-단체(안내페이지)
	@GetMapping(value="/reservation_group")
	public String reservation_group() {
		return "reservation_group";
	}
	
	// 대관안내-예약안내 페이지
	@GetMapping(value="/reservation_facilities")
	public String reservation_facilities(ModelMap model) {
		// 오늘 날짜
	    LocalDate now = LocalDate.now();
	    Calendar time = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    String nowTime = format.format(time.getTime());
	    
	    Calendar cal = Calendar.getInstance();
	    String today = dateFormat.format(cal.getTime());
	    
	    //휴관일 정보 가져오기
	    closed_list = adminService.getClosed();
	    
	    for(ClosedVO vo : closed_list) {
	    	 String closed_date = vo.getClosed_date();
	    	 String year = closed_date.substring(0, 4);
	    	 String month = closed_date.substring(5, 7);
	    	 String day = closed_date.substring(8, 10);
	    	 String date_str = year+month+day;
	    	 
	    	 //해당 월에 휴관일 있으면 setting
	     }
	    // 예약확정 정보 가져오기 --- 날짜순, 회차순
	    res_f_list = adminService.getFacility_Reslist();
	    
	    model.addAttribute("facility_list", res_f_list);
	    model.addAttribute("today", today);
	    
		return "reservation_facilities";
	}
	
	// 단체예약-날짜 및 회차선택
	@RequestMapping(value="/reservation_group_date", method=RequestMethod.GET)
	public String reservation_group_date(ModelMap model, @RequestParam(defaultValue="") String choice_date) throws Exception {
		
		// 오늘 날짜
	    LocalDate now = LocalDate.now();
	    Calendar time = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    String nowTime = format.format(time.getTime());
	    
	    Calendar cal = Calendar.getInstance();
	    String today = dateFormat.format(cal.getTime());
	    
	    //휴관일 정보 가져오기
	    closed_list = adminService.getClosed();
	    
	    for(ClosedVO vo : closed_list) {
	    	 String closed_date = vo.getClosed_date();
	    	 String year = closed_date.substring(0, 4);
	    	 String month = closed_date.substring(5, 7);
	    	 String day = closed_date.substring(8, 10);
	    	 String date_str = year+month+day;
	    	 
	    	 //해당 월에 휴관일 있으면 setting
	     }
	    
	    //확정된 예약정보 가져오기 -> 잔여인원 차감 목적
	    int total = 0;
	    //기본값 설정 -> total값 0
	    model.addAttribute("total_1", total);
	    model.addAttribute("total_2", total);
	    model.addAttribute("total_3", total);
	    model.addAttribute("total_4", total);
	    model.addAttribute("total_5", total);
	    model.addAttribute("total_6", total);
	    model.addAttribute("total_7", total);
	    model.addAttribute("total_8", total);
	    model.addAttribute("total_9", total);
	    model.addAttribute("total_10", total);
	    model.addAttribute("total_11", total);
	    model.addAttribute("total_12", total);
	    model.addAttribute("total_13", total);
	    model.addAttribute("total_14", total);
	    
	    if(choice_date == "") {
	    	res_list = adminService.getReservation_list(today);
	    	for(GroupVO vo : res_list) {
	    		if(vo.getRes_visitNum() == 1) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_1", total);
	    		}else if(vo.getRes_visitNum() == 2) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_2", total);
	    		}else if(vo.getRes_visitNum() == 3) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_3", total);
	    		}else if(vo.getRes_visitNum() == 4) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_4", total);
	    		}else if(vo.getRes_visitNum() == 5) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_5", total);
	    		}else if(vo.getRes_visitNum() == 6) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_6", total);
	    		}else if(vo.getRes_visitNum() == 7) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_7", total);
	    		}else if(vo.getRes_visitNum() == 8) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_8", total);
	    		}else if(vo.getRes_visitNum() == 9) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_9", total);
	    		}else if(vo.getRes_visitNum() == 10) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_10", total);
	    		}else if(vo.getRes_visitNum() == 11) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_11", total);
	    		}else if(vo.getRes_visitNum() == 12) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_12", total);
	    		}else if(vo.getRes_visitNum() == 13) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_13", total);
	    		}else if(vo.getRes_visitNum() == 14) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_14", total);
	    		}
	    	}
	    }else if(choice_date != "") {
	    	res_list = adminService.getReservation_list(choice_date);
	    	for(GroupVO vo : res_list) {
	    		if(vo.getRes_visitNum() == 1) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_1", total);
	    		}else if(vo.getRes_visitNum() == 2) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_2", total);
	    		}else if(vo.getRes_visitNum() == 3) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_3", total);
	    		}else if(vo.getRes_visitNum() == 4) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_4", total);
	    		}else if(vo.getRes_visitNum() == 5) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_5", total);
	    		}else if(vo.getRes_visitNum() == 6) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_6", total);
	    		}else if(vo.getRes_visitNum() == 7) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_7", total);
	    		}else if(vo.getRes_visitNum() == 8) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_8", total);
	    		}else if(vo.getRes_visitNum() == 9) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_9", total);
	    		}else if(vo.getRes_visitNum() == 10) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_10", total);
	    		}else if(vo.getRes_visitNum() == 11) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_11", total);
	    		}else if(vo.getRes_visitNum() == 12) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_12", total);
	    		}else if(vo.getRes_visitNum() == 13) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_13", total);
	    		}else if(vo.getRes_visitNum() == 14) {
	    			total = total + vo.getRes_totalNum();
	    			model.addAttribute("total_14", total);
	    		}
	    	}
	    }
	    
	    //회차별 설정 정보 가져오기
	    time_list = adminService.getTime();
	    System.out.println("today = "+today);
	    
	    model.addAttribute("time_1", "10:00");
	    model.addAttribute("limit_1", 500);
	    model.addAttribute("status_1", 1);
	    
	    model.addAttribute("time_2", "10:30");
	    model.addAttribute("limit_2", 500);
	    model.addAttribute("status_2", 1);
	    
	    model.addAttribute("time_3", "11:00");
	    model.addAttribute("limit_3", 500);
	    model.addAttribute("status_3", 1);
	    
	    model.addAttribute("time_4", "11:30");
	    model.addAttribute("limit_4", 500);
	    model.addAttribute("status_4", 1);
	    
	    model.addAttribute("time_5", "12:00");
	    model.addAttribute("limit_5", 500);
	    model.addAttribute("status_5", 1);
	    
	    model.addAttribute("time_6", "12:30");
	    model.addAttribute("limit_6", 500);
	    model.addAttribute("status_6", 1);
	    
	    model.addAttribute("time_7", "13:00");
	    model.addAttribute("limit_7", 500);
	    model.addAttribute("status_7", 1);
	    
	    model.addAttribute("time_8", "13:30");
	    model.addAttribute("limit_8", 500);
	    model.addAttribute("status_8", 1);
	    
	    model.addAttribute("time_9", "14:00");
	    model.addAttribute("limit_9", 500);
	    model.addAttribute("status_9", 1);
	    
	    model.addAttribute("time_10", "14:30");
	    model.addAttribute("limit_10", 500);
	    model.addAttribute("status_10", 1);
	    
	    model.addAttribute("time_11", "15:00");
	    model.addAttribute("limit_11", 500);
	    model.addAttribute("status_11", 1);
	    
	    model.addAttribute("time_12", "15:30");
	    model.addAttribute("limit_12", 500);
	    model.addAttribute("status_12", 1);
	    
	    model.addAttribute("time_13", "16:00");
	    model.addAttribute("limit_13", 500);
	    model.addAttribute("status_13", 1);
	    
	    model.addAttribute("time_14", "16:30");
	    model.addAttribute("limit_14", 500);
	    model.addAttribute("status_14", 1);
	    
	    if(choice_date == "") {
	    	for(TimeVO vo : time_list) {
		    	 String setting_date = vo.getRes_date();
		    	 if(today.equals(setting_date)) {
		    		 if(vo.getRes_num() == 1) { //잔여인원 추가!!!!!
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_1", vo.getRes_time());
		    				 model.addAttribute("limit_1", vo.getRes_limited());
		    				 model.addAttribute("status_1", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_1", 0);
		    				 model.addAttribute("limit_1", 0);
		    				 model.addAttribute("status_1", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 2) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_2", vo.getRes_time());
		    				 model.addAttribute("limit_2", vo.getRes_limited());
		    				 model.addAttribute("status_2", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_2", 0);
		    				 model.addAttribute("limit_2", 0);
		    				 model.addAttribute("status_2", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 3) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_3", vo.getRes_time());
		    				 model.addAttribute("limit_3", vo.getRes_limited());
		    				 model.addAttribute("status_3", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_3", 0);
		    				 model.addAttribute("limit_3", 0);
		    				 model.addAttribute("status_3", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 4) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_4", vo.getRes_time());
		    				 model.addAttribute("limit_4", vo.getRes_limited());
		    				 model.addAttribute("status_4", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_4", 0);
		    				 model.addAttribute("limit_4", 0);
		    				 model.addAttribute("status_4", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 5) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_5", vo.getRes_time());
		    				 model.addAttribute("limit_5", vo.getRes_limited());
		    				 model.addAttribute("status_5", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_5", 0);
		    				 model.addAttribute("limit_5", 0);
		    				 model.addAttribute("status_5", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 6) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_6", vo.getRes_time());
		    				 model.addAttribute("limit_6", vo.getRes_limited());
		    				 model.addAttribute("status_6", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_6", 0);
		    				 model.addAttribute("limit_6", 0);
		    				 model.addAttribute("status_6", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 7) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_7", vo.getRes_time());
		    				 model.addAttribute("limit_7", vo.getRes_limited());
		    				 model.addAttribute("status_7", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_7", 0);
		    				 model.addAttribute("limit_7", 0);
		    				 model.addAttribute("status_7", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 8) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_8", vo.getRes_time());
		    				 model.addAttribute("limit_8", vo.getRes_limited());
		    				 model.addAttribute("status_8", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_8", 0);
		    				 model.addAttribute("limit_8", 0);
		    				 model.addAttribute("status_8", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 9) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_9", vo.getRes_time());
		    				 model.addAttribute("limit_9", vo.getRes_limited());
		    				 model.addAttribute("status_9", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_9", 0);
		    				 model.addAttribute("limit_9", 0);
		    				 model.addAttribute("status_9", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 10) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_10", vo.getRes_time());
		    				 model.addAttribute("limit_10", vo.getRes_limited());
		    				 model.addAttribute("status_10", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_10", 0);
		    				 model.addAttribute("limit_10", 0);
		    				 model.addAttribute("status_10", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 11) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_11", vo.getRes_time());
		    				 model.addAttribute("limit_11", vo.getRes_limited());
		    				 model.addAttribute("status_11", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_11", 0);
		    				 model.addAttribute("limit_11", 0);
		    				 model.addAttribute("status_11", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 12) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_12", vo.getRes_time());
		    				 model.addAttribute("limit_12", vo.getRes_limited());
		    				 model.addAttribute("status_12", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_12", 0);
		    				 model.addAttribute("limit_12", 0);
		    				 model.addAttribute("status_12", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 13) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_13", vo.getRes_time());
		    				 model.addAttribute("limit_13", vo.getRes_limited());
		    				 model.addAttribute("status_13", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_13", 0);
		    				 model.addAttribute("limit_13", 0);
		    				 model.addAttribute("status_13", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 14) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_14", vo.getRes_time());
		    				 model.addAttribute("limit_14", vo.getRes_limited());
		    				 model.addAttribute("status_14", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_14", 0);
		    				 model.addAttribute("limit_14", 0);
		    				 model.addAttribute("status_14", vo.getTime_status());
		    			 }
		    		 }
		    	 }
	    	}	 
	    }else {
	    	for(TimeVO vo : time_list) {
		    	 String setting_date = vo.getRes_date();
		    	 System.out.println(vo);
		    	 
		    	 if(choice_date.equals(setting_date)) { //해당 일자에 설정된 회차정보 있으면 setting
		    		 if(vo.getRes_num() == 1) { //잔여인원 추가!!!!!
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_1", vo.getRes_time());
		    				 model.addAttribute("limit_1", vo.getRes_limited());
		    				 model.addAttribute("status_1", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_1", 0);
		    				 model.addAttribute("limit_1", 0);
		    				 model.addAttribute("status_1", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 2) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_2", vo.getRes_time());
		    				 model.addAttribute("limit_2", vo.getRes_limited());
		    				 model.addAttribute("status_2", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_2", 0);
		    				 model.addAttribute("limit_2", 0);
		    				 model.addAttribute("status_2", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 3) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_3", vo.getRes_time());
		    				 model.addAttribute("limit_3", vo.getRes_limited());
		    				 model.addAttribute("status_3", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_3", 0);
		    				 model.addAttribute("limit_3", 0);
		    				 model.addAttribute("status_3", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 4) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_4", vo.getRes_time());
		    				 model.addAttribute("limit_4", vo.getRes_limited());
		    				 model.addAttribute("status_4", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_4", 0);
		    				 model.addAttribute("limit_4", 0);
		    				 model.addAttribute("status_4", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 5) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_5", vo.getRes_time());
		    				 model.addAttribute("limit_5", vo.getRes_limited());
		    				 model.addAttribute("status_5", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_5", 0);
		    				 model.addAttribute("limit_5", 0);
		    				 model.addAttribute("status_5", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 6) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_6", vo.getRes_time());
		    				 model.addAttribute("limit_6", vo.getRes_limited());
		    				 model.addAttribute("status_6", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_6", 0);
		    				 model.addAttribute("limit_6", 0);
		    				 model.addAttribute("status_6", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 7) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_7", vo.getRes_time());
		    				 model.addAttribute("limit_7", vo.getRes_limited());
		    				 model.addAttribute("status_7", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_7", 0);
		    				 model.addAttribute("limit_7", 0);
		    				 model.addAttribute("status_7", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 8) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_8", vo.getRes_time());
		    				 model.addAttribute("limit_8", vo.getRes_limited());
		    				 model.addAttribute("status_8", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_8", 0);
		    				 model.addAttribute("limit_8", 0);
		    				 model.addAttribute("status_8", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 9) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_9", vo.getRes_time());
		    				 model.addAttribute("limit_9", vo.getRes_limited());
		    				 model.addAttribute("status_9", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_9", 0);
		    				 model.addAttribute("limit_9", 0);
		    				 model.addAttribute("status_9", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 10) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_10", vo.getRes_time());
		    				 model.addAttribute("limit_10", vo.getRes_limited());
		    				 model.addAttribute("status_10", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_10", 0);
		    				 model.addAttribute("limit_10", 0);
		    				 model.addAttribute("status_10", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 11) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_11", vo.getRes_time());
		    				 model.addAttribute("limit_11", vo.getRes_limited());
		    				 model.addAttribute("status_11", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_11", 0);
		    				 model.addAttribute("limit_11", 0);
		    				 model.addAttribute("status_11", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 12) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_12", vo.getRes_time());
		    				 model.addAttribute("limit_12", vo.getRes_limited());
		    				 model.addAttribute("status_12", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_12", 0);
		    				 model.addAttribute("limit_12", 0);
		    				 model.addAttribute("status_12", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 13) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_13", vo.getRes_time());
		    				 model.addAttribute("limit_13", vo.getRes_limited());
		    				 model.addAttribute("status_13", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_13", 0);
		    				 model.addAttribute("limit_13", 0);
		    				 model.addAttribute("status_13", vo.getTime_status());
		    			 }
		    		 }else if(vo.getRes_num() == 14) {
		    			 if(vo.getTime_status() == 1) {
		    				 model.addAttribute("time_14", vo.getRes_time());
		    				 model.addAttribute("limit_14", vo.getRes_limited());
		    				 model.addAttribute("status_14", vo.getTime_status());
		    			 }else if(vo.getTime_status() == 0) {
		    				 model.addAttribute("time_14", 0);
		    				 model.addAttribute("limit_14", 0);
		    				 model.addAttribute("status_14", vo.getTime_status());
		    			 }
		    		 }
		    	 }
	    	}
	    }
	    
	    model.addAttribute("today", today);
	    model.addAttribute("closed_list", closed_list);
	    model.addAttribute("time_list", time_list);
	    model.addAttribute("choice_date", choice_date);
	      
		return "reservation_group_date";
	}
	
		//예약신청
		@ResponseBody
		@RequestMapping(value="/reservation_ok", method=RequestMethod.POST)
		public String reservation_group_register(@ModelAttribute GroupVO groupvo, ModelMap model) throws Exception {
			
			System.out.println(groupvo);
			groupService.insertReservation(groupvo);
			
			return "/";
		}
		
		// 휴대폰인증
	    @RequestMapping(value = "phoneCheck", method = RequestMethod.GET)
	    @ResponseBody
	    public String sendSMS(@RequestParam("user_phone") String userPhoneNumber) throws CoolsmsException { // 휴대폰 문자보내기
	        int randomNumber = (int)((Math.random() * (9999 - 1000 +1)) + 1000); // 난수 생성

	        groupService.sendSms(userPhoneNumber, randomNumber);
	        System.out.println(randomNumber);
	        return Integer.toString(randomNumber);
	    }
	    
		// 대관예약-날짜 및 회차선택
		@RequestMapping(value="/reservation_facilities_date", method=RequestMethod.GET)
		public String reservation_facilities_date(ModelMap model, @RequestParam(defaultValue="") String choice_date) throws Exception {
			
			// 오늘 날짜
		    LocalDate now = LocalDate.now();
		    Calendar time = Calendar.getInstance();
		    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    String nowTime = format.format(time.getTime());
		    
		    Calendar cal = Calendar.getInstance();
		    String today = dateFormat.format(cal.getTime());
		    
		    //휴관일 정보 가져오기
		    closed_list = adminService.getClosed();
		    
		    for(ClosedVO vo : closed_list) {
		    	 String closed_date = vo.getClosed_date();
		    	 String year = closed_date.substring(0, 4);
		    	 String month = closed_date.substring(5, 7);
		    	 String day = closed_date.substring(8, 10);
		    	 String date_str = year+month+day;
		    	 
		    	 //해당 월에 휴관일 있으면 setting
		     }
		    
		    //확정된 예약정보 가져오기 -> 해당회차 예약되어있으면 '매진' 처리
		    //대관 : 같은 일자에 회차별로 1팀만 예약 가능
		    int total = 0;
		    //기본값 설정 -> total값 0
		    model.addAttribute("total_1", total);
		    model.addAttribute("total_2", total);
		    model.addAttribute("total_3", total);
		    
		    if(choice_date == "") {
		    	res_f_list = adminService.getFacility_list(today);
		    	for(FacilitiesVO vo : res_f_list) {
		    		if(vo.getRes_visitNum() == 1) {
		    			model.addAttribute("total_1", 1);
		    		}else if(vo.getRes_visitNum() == 2) {
		    			model.addAttribute("total_2", 1);
		    		}else if(vo.getRes_visitNum() == 3) {
		    			model.addAttribute("total_3", 1);
		    		}
		    	}
		    }else if(choice_date != "") {
		    	res_f_list = adminService.getFacility_list(choice_date);
		    	for(FacilitiesVO vo : res_f_list) {
		    		if(vo.getRes_visitNum() == 1) {
		    			model.addAttribute("total_1", 1);
		    		}else if(vo.getRes_visitNum() == 2) {
		    			model.addAttribute("total_2", 1);
		    		}else if(vo.getRes_visitNum() == 3) {
		    			model.addAttribute("total_3", 1);
		    		}
		    	}
		    }
		    
		    model.addAttribute("today", today);
		    model.addAttribute("closed_list", closed_list);
		    model.addAttribute("choice_date", choice_date);
		      
			return "reservation_facilities_date";
		}
		
		//대관예약신청(post)
		@ResponseBody
		@RequestMapping(value="/reservation_facilities_ok", method=RequestMethod.POST)
		public String reservation_facilities_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute FacilitiesVO vo, ModelMap model) throws Exception {
					
			MultipartFile application = multipartRequest.getFile("application_file");
			System.out.println(application);
			
			if(application.getOriginalFilename() != "") {
				String filename = s3Service.upload_applicationFile(application);
				System.out.println("s3 insert ok => "+filename);
				vo.setRes_file(filename);
			}
			groupService.insertFacilityReservation(vo);
					
			return "/";
		}
		
		//대관신청서 양식파일 다운로드
		@RequestMapping({"/download_facility_document"})
		@ResponseBody
		public ResponseEntity<byte[]> application_form_download() throws IOException {
			String filename = "[별지제1호]대관신청서.hwp";
			return s3Service.getObject_application(filename);
		}
}
