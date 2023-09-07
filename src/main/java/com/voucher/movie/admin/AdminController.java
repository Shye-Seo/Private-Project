package com.voucher.movie.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.voucher.movie.ScriptUtils;
import com.voucher.movie.aws.AwsS3Service;
import com.voucher.movie.board.NewsFileVo;
import com.voucher.movie.board.NewsVO;
import com.voucher.movie.config.PagingVO;
import com.voucher.movie.reservation.ClosedVO;
import com.voucher.movie.reservation.GroupService;
import com.voucher.movie.reservation.GroupVO;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	@Inject
	AdminService adminService;
	
	@Inject
	GroupService groupService;
	
	@Autowired
	AwsS3Service s3Service;
	
	List<GroupVO> group_list;
	
	List<ClosedVO> closed_list;
	
	List<NewsVO> news_list;
	
	String bucketName = "busanbom"; //변경필요
	String folderName_news = "news-folder/";
	String folderName_event = "event-folder/";
	String folderName_notice = "notice-folder/";
	String folderName_partner = "partner-folder/";
	String folderName_edu = "edu-folder/";

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
	    
	    //휴관일 정보 가져오기
	    closed_list = adminService.getClosed();
	    System.out.println(closed_list);
	    
	    for(ClosedVO vo : closed_list) {
	    	 String closed_date = vo.getClosed_date();
	    	 String year = closed_date.substring(0, 4);
	    	 String month = closed_date.substring(5, 7);
	    	 String day = closed_date.substring(8, 10);
	    	 String date_str = year+month+day;
	    	 
	    	 //해당 월에 휴관일 있으면 setting
	    	 
	     }
	    
	    model.addAttribute("today", today);
	    model.addAttribute("closed_list", closed_list);
	    
		return "/admin_reservationSetting";
	}
	
	//관리자 - 운영관리(set)
	@RequestMapping(value="/reservation_setTime", method=RequestMethod.POST)
	public String reservation_setting(@RequestParam("setting_date") String setting_date, @RequestParam("closed_status") int closed_status, @RequestParam("time_num") int time_num,
									  @RequestParam("setting_time") String setting_time, @RequestParam("limited_num") int limited_num, @RequestParam("time_status") int time_status, ModelMap model) throws Exception {
			
		System.out.println("setting_date : "+setting_date);
		System.out.println("time_num : "+time_num);
		System.out.println("setting_time : "+setting_time);
		System.out.println("limited_num : "+limited_num);
		System.out.println("time_status : "+time_status);
		
		if(closed_status == 1) { // 휴관일 설정 O -> reservation_closed 테이블에 insert
			adminService.setClosed(setting_date);
		}else if(closed_status == 0) { // 휴관일 설정 X
			int compare = adminService.compareStatus(setting_date); //원래 휴관일인지 아닌지 비교
			if(compare == 1) { //원래 휴관일이면 휴관일 아닌상태로 set(설정취소)
				adminService.setUnclosed(setting_date);
			}
			
			//회차정보 변경
			if(time_status == 1) { //해당회차 활성화 -> 정보만 변경
				//time table에 데이터 있는지 확인
				int check = adminService.checkTime(setting_date, time_num);
				if(check == 1) { //있으면 update
					adminService.update_resTime(setting_date, time_num, setting_time, limited_num, time_status);
				}else { //없으면 insert
					adminService.setting_resTime(setting_date, time_num, setting_time, limited_num, time_status);
				}
			}else if(time_status == 0) { //해당회차 비활성화 -> 사용자페이지에서 안보이게 set
				adminService.setting_timeUnable(setting_date, time_num, time_status);
			}
		}
		
		return "redirect:/admin_reservationSetting";
	}
	
	//관리자 - 박물관 소식 리스트
	@RequestMapping(value="/admin_newsList", method=RequestMethod.GET)
	public String admin_newsList(@ModelAttribute NewsVO newsVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
			
		// 총 게시물 수 
	    int totalListCnt = adminService.findAllNews();

	    // 생성인자로  총 게시물 수, 현재 페이지를 전달
	    PagingVO pagination = new PagingVO(totalListCnt, page);

	    // DB select start index
	    int startIndex = pagination.getStartIndex();
	    // 페이지 당 보여지는 게시글의 최대 개수
	    int pageSize = pagination.getPageSize();

	    news_list = adminService.findNewsPaging(startIndex, pageSize);
	    
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    
	    for(NewsVO news : news_list) {
	    	String news_date = news.getCreate_date();
	    	news_date = news_date.substring(0, 4) + "." + news_date.substring(5, 7) +"." + news_date.substring(8, 10);
	    	news.setCreate_date(news_date);
	    }
		    
		model.addAttribute("news_list", news_list);
		model.addAttribute("nowpage", page);
			 
		return "/admin_newsList";
	}
	
	//관리자 - 박물관 소식 등록 페이지
	@RequestMapping(value="/admin_newsInsert", method=RequestMethod.GET)
	public String admin_newsInsert(ModelMap model) throws Exception {
			
		return "/admin_newsInsert";
	}
	
	//박물관 소식 등록(post)
	@ResponseBody
	@RequestMapping(value="/newsAdd", method=RequestMethod.POST)
	public String reservation_news_register(HttpServletRequest request, @ModelAttribute NewsVO newsvo, ModelMap model,@RequestAttribute List<MultipartFile> news_file) throws Exception {
		
		System.out.println(news_file);
		
		adminService.insertNews(newsvo);
		int news_id = adminService.get_news_Id(newsvo.getId());
	    
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
	    String time = dateFormat.format(cal.getTime());
	    
	    if(news_file != null) {
	    	List<String> filenames = s3Service.upload_news(news_file);
			for(String name : filenames) {
				NewsFileVo newsFileVo = new NewsFileVo();
				newsFileVo.setNews_id(news_id);
				newsFileVo.setFile_name(name);
				adminService.insertNewsFile(newsFileVo);
			}
		}
	    
		return "/admin_newsList";
	}	
	
	//박물관 소식 조회_관리자
		@RequestMapping(value="/admin_newsDetail", method=RequestMethod.GET)
		public ModelAndView eventDetail(@RequestParam("id") int news_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			NewsVO detailVo = adminService.viewNewsDetail(news_id);
			
			List<NewsFileVo> newsFileList = adminService.viewNewsFileDetail(news_id);
			String news_date = detailVo.getCreate_date();
	    	news_date = news_date.substring(0, 4) + "." + news_date.substring(5, 7) +"." + news_date.substring(8, 10);
	    	detailVo.setCreate_date(news_date);
	    	
	    	if(detailVo.getNews_link1() != null) {
	    		mav.addObject("news_link1", detailVo.getNews_link1());
	    	}
	    	if(detailVo.getNews_link2() != null) {
	    		mav.addObject("news_link2", detailVo.getNews_link2());
	    	}
	    	
			mav.addObject("news", detailVo);
			mav.addObject("newsFileList", newsFileList);
			mav.setViewName("admin_newsDetail");
			return mav;
		}
		
		//박물관 소식 파일 다운로드
		@RequestMapping({"/news_download"})
		@ResponseBody
		public ResponseEntity<byte[]> download(@RequestParam String filename) throws IOException {
			return s3Service.getObject_news(filename);
		}
		
		//박물관 소식 수정 페이지
		@RequestMapping(value="/admin_news_update", method=RequestMethod.GET)
		public ModelAndView admin_news_update(@RequestParam("id") int news_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			NewsVO detailVo = adminService.viewNewsDetail(news_id);
			
			List<NewsFileVo> newsFileList = adminService.viewNewsFileDetail(news_id);
			
			mav.addObject("news", detailVo);
			mav.addObject("newsFileList", newsFileList);

			mav.setViewName("admin_newsUpdate");
			return mav;
		}
		
		//박물관 소식 수정
		@ResponseBody
		@RequestMapping(value="/newsUpdate", method=RequestMethod.POST)
		public String newsUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute NewsVO newsVo, @RequestAttribute("news_file") List<MultipartFile> news_file) throws Exception{
				
			//지울 파일 리스트
			String[] deleteFileNameList = multipartRequest.getParameterValues("deleteFileNameList");
			System.out.println("deletefile = "+deleteFileNameList);
			
			//수정 시 지운파일 삭제
			if(deleteFileNameList != null) {
				for( String name : deleteFileNameList ) {
					s3Service.delete_s3News(name);
					adminService.deleteFile(newsVo.getId(), name);
				}
			}
			//행사 내용 수정
			adminService.updateNews(newsVo);
				
			//수정 시 추가한 파일 추가
			if(news_file != null) {
				List<String> filenames = s3Service.upload_news(news_file);
				for(String name : filenames) {
					NewsFileVo newsFileVo = new NewsFileVo();
					newsFileVo.setNews_id(newsVo.getId());
					newsFileVo.setFile_name(name);
					adminService.insertNewsFile(newsFileVo);
				}
			}
				
			return "admin_newsDetail?id="+newsVo.getId();
		}
		
		//박물관 소식 삭제
		@ResponseBody
		@RequestMapping(value = "news_delete", method = RequestMethod.POST)
		public String news_delete(HttpServletRequest request, ModelMap modelMap,
				@RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				adminService.news_delete(c);
				String[] deleteFileNameList = adminService.getNewsFile(c);
				
				if(deleteFileNameList != null) {
					for( String name : deleteFileNameList ) {
						s3Service.delete_s3News(name);
					}
					adminService.newsFile_delete(c);
				}
			}
			return String.valueOf(cnt);
		}
}
