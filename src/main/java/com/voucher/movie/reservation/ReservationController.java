package com.voucher.movie.reservation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
public class ReservationController {
	
	@Inject
    private TemporaryService tempService;
	
	@Inject
    private GroupService groupService;
	
	//날짜 및 회차선택-전시관1
	@RequestMapping(value="/reservation_group_date_1", method=RequestMethod.GET)
	public String reservation_group_date(ModelMap model) throws Exception {
		
		// 오늘 날짜
	    LocalDate now = LocalDate.now();
	    Calendar time = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    String nowTime = format.format(time.getTime());
	    
	    Calendar cal = Calendar.getInstance();
	    String today = dateFormat.format(cal.getTime());
	    
	    model.addAttribute("today", today);
//	    model.addAttribute("check", check);
//	    System.out.println(check);
	      
		return "reservation_group_date_1";
	}
	
	//날짜 및 회차선택-전시관2
		@RequestMapping(value="/reservation_group_date_2", method=RequestMethod.GET)
		public String reservation_group_date_2(ModelMap model) throws Exception {
			
			// 오늘 날짜
		    LocalDate now = LocalDate.now();
		    Calendar time = Calendar.getInstance();
		    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    String nowTime = format.format(time.getTime());
		    
		    Calendar cal = Calendar.getInstance();
		    String today = dateFormat.format(cal.getTime());
		    
		    model.addAttribute("today", today);
//		    model.addAttribute("check", check);
//		    System.out.println(check);
		      
			return "reservation_group_date_2";
		}
	
		//예약날짜 및 회차선택 넘기기
		@ResponseBody
		@RequestMapping(value="/reservation_group_date_register", method=RequestMethod.POST)
		public String reservation_date_register(@RequestParam("res_theaterCheck") int res_theaterCheck, @RequestParam("res_visitDate") String res_visitDate, 
				 								@RequestParam("res_visitNum") int res_visitNum, TemporaryVO tempvo, ModelMap model) throws Exception {
			
			String res_visitTime = "";
			
			if(res_visitNum == 1) {
				res_visitTime = "10:00";
			}else if(res_visitNum == 2) {
				res_visitTime = "10:30";
			}else if(res_visitNum == 3) {
				res_visitTime = "11:00";
			}else if(res_visitNum == 4) {
				res_visitTime = "11:30";
			}else if(res_visitNum == 5) {
				res_visitTime = "12:00";
			}else if(res_visitNum == 6) {
				res_visitTime = "12:30";
			}else if(res_visitNum == 7) {
				res_visitTime = "13:00";
			}else if(res_visitNum == 8) {
				res_visitTime = "13:30";
			}else if(res_visitNum == 9) {
				res_visitTime = "14:00";
			}else if(res_visitNum == 10) {
				res_visitTime = "14:30";
			}else if(res_visitNum == 11) {
				res_visitTime = "15:00";
			}else if(res_visitNum == 12) {
				res_visitTime = "15:30";
			}else if(res_visitNum == 13) {
				res_visitTime = "16:00";
			}else if(res_visitNum == 14) {
				res_visitTime = "16:30";
			}
			
			//전시관, 날짜 및 시간 저장해서 다음 페이지 전달 시, 키값이 될 임시랜덤키
			int random_idx = (int)(Math.random() * 8999) + 1000;
//			System.out.println(random_idx);
			tempvo.setRandom_idx(random_idx);
			tempvo.setRes_theaterCheck(res_theaterCheck);
			tempvo.setRes_visitDate(res_visitDate);
			tempvo.setRes_visitNum(res_visitNum);
			tempvo.setRes_visitTime(res_visitTime);
			
			System.out.println(tempvo);
			int id = tempService.insertTemp(tempvo);
			System.out.println(id);
			
			model.addAttribute("res_theaterCheck", res_theaterCheck);
			model.addAttribute("res_visitDate", res_visitDate);
			model.addAttribute("res_visitNum", res_visitNum);
			
//			System.out.println("res_theaterCheck : " + res_theaterCheck);
//			System.out.println("res_visitNum : " + res_visitNum);
//			System.out.println(res_visitDate);
			
			return "reservation_group_complete?id="+id;
		}
		
		//예약정보 기입
		@RequestMapping(value="/reservation_group_complete", method=RequestMethod.GET)
		public ModelAndView reservation_group_complete(@RequestParam("id") int id, ModelMap model) throws Exception {
			ModelAndView mav = new ModelAndView();
			TemporaryVO tempVo = tempService.getTemp(id);
			
//			System.out.println("출력 : "+tempVo);
			mav.addObject("tempVo", tempVo);
			mav.setViewName("reservation_group_info");
			
			return mav;
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
}