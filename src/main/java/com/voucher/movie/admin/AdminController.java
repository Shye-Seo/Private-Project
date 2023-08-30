package com.voucher.movie.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.voucher.movie.ScriptUtils;
import com.voucher.movie.config.PagingVO;
import com.voucher.movie.reservation.GroupService;
import com.voucher.movie.reservation.GroupVO;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	@Inject
	AdminService adminService;
	
	@Inject
	GroupService groupService;
	
	List<GroupVO> group_list;

	//관리자 로그인
	@RequestMapping(value = "admin_login")
	public ModelAndView loginCheck(HttpServletResponse response, @ModelAttribute AdminVO adminVo, HttpSession session) throws Exception {
        int result =adminService.loginCheck(adminVo, session);
        ModelAndView mav = new ModelAndView();
        System.out.println(result);

        if(result==1) { // 로그인 성공
            //관리자 페이지 이동
        	mav.setViewName("redirect:/admin_reservationList");
        }
        else {
            // 로그인 실패
            mav.setViewName("/admin");
            mav.addObject("msg", "failure");
        }
        return mav;
    }
	
	//관리자 로그아웃
	@RequestMapping(value = "admin_logout")
	public String Logout (HttpSession session) throws Exception{
    	session.invalidate();
    	return "/admin";
	}
	
	//관리자 - 예약현황 리스트
	@RequestMapping(value="/admin_reservationList", method=RequestMethod.GET)
	public String admin_resList(@ModelAttribute GroupVO groupVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
		
		// 총 게시물 수 
	    int totalListCnt = adminService.findAllCnt();

	    // 생성인자로  총 게시물 수, 현재 페이지를 전달
	    PagingVO pagination = new PagingVO(totalListCnt, page);

	    // DB select start index
	    int startIndex = pagination.getStartIndex();
	    // 페이지 당 보여지는 게시글의 최대 개수
	    int pageSize = pagination.getPageSize();

	    group_list = adminService.findListPaging(startIndex, pageSize);
	    
		model.addAttribute("group_list", group_list);
		model.addAttribute("nowpage", page);
		 
		return "/admin_reservationList";
	}
	
	//관리자 - 예약확정
	@RequestMapping(value="/reservation_confirm", method=RequestMethod.GET)
	public String admin_resConfirm(@RequestParam("id") int id, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
			
	    adminService.setStatus(id, 1);
	    
	    // 총 게시물 수 
	    int totalListCnt = adminService.findAllCnt();

	    // 생성인자로  총 게시물 수, 현재 페이지를 전달
	    PagingVO pagination = new PagingVO(totalListCnt, page);

	    // DB select start index
	    int startIndex = pagination.getStartIndex();
	    // 페이지 당 보여지는 게시글의 최대 개수
	    int pageSize = pagination.getPageSize();

	    group_list = adminService.findListPaging(startIndex, pageSize);
		    
		model.addAttribute("group_list", group_list);
		model.addAttribute("nowpage", page);
			 
		return "/admin_reservationList";
	}
	
	//관리자 - 운영관리
	@RequestMapping(value="/admin_reservationSetting", method=RequestMethod.GET)
	public String admin_resSetting(ModelMap model) throws Exception {
		
		// 오늘 날짜
	    LocalDate now = LocalDate.now();
	    Calendar time = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    String nowTime = format.format(time.getTime());
	    
	    Calendar cal = Calendar.getInstance();
	    String today = dateFormat.format(cal.getTime());
	    
	    model.addAttribute("today", today);
	    
		return "/admin_reservationSetting";
	}
}
