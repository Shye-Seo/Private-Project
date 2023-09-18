package com.voucher.movie.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

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
import com.voucher.movie.WebService;
import com.voucher.movie.aws.AwsS3Service;
import com.voucher.movie.board.EduFileVo;
import com.voucher.movie.board.EduVO;
import com.voucher.movie.board.EventFileVo;
import com.voucher.movie.board.EventVO;
import com.voucher.movie.board.NewsFileVo;
import com.voucher.movie.board.NewsVO;
import com.voucher.movie.board.NoticeFileVo;
import com.voucher.movie.board.NoticeVO;
import com.voucher.movie.board.PartnerFileVo;
import com.voucher.movie.board.PartnerVO;
import com.voucher.movie.config.PagingVO;
import com.voucher.movie.reservation.ClosedVO;
import com.voucher.movie.reservation.FacilitiesVO;
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
	
	@Inject
	WebService webService;
	
	List<GroupVO> group_list;
	List<ClosedVO> closed_list;
	List<FacilitiesVO> facility_list;
	List<NewsVO> news_list;
	List<EventVO> event_list;
	List<NoticeVO> notice_list;
	List<PartnerVO> partner_list;
	List<EduVO> edu_list;
	List<PopupVO> popup_list;
	
	String bucketName = "busanbom";
	String folderName_news = "news-folder/";
	String folderName_event = "event-folder/";
	String folderName_notice = "notice-folder/";
	String folderName_partner = "partner-folder/";
	String folderName_edu = "edu-folder/";
	
	//Q&A 관리
	List<QuestionVO> question_list;
	List<AnswerVO> answer_list;

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
	
	//관리자 - 예약현황 리스트(단체)
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
	
	//관리자 - 예약현황 리스트(대관)
	@RequestMapping(value="/admin_facilityList", method=RequestMethod.GET)
	public String admin_facilityList(@ModelAttribute FacilitiesVO facilityVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
			
		// 총 게시물 수 
	    int totalListCnt = adminService.findAllFacilityCnt();

	    // 생성인자로  총 게시물 수, 현재 페이지를 전달
	    PagingVO pagination = new PagingVO(totalListCnt, page);

	    // DB select start index
	    int startIndex = pagination.getStartIndex();
	    // 페이지 당 보여지는 게시글의 최대 개수
	    int pageSize = pagination.getPageSize();

	    facility_list = adminService.findFacilityListPaging(startIndex, pageSize);
	    
		model.addAttribute("facility_list", facility_list);
		model.addAttribute("nowpage", page);
			 
		return "/admin_facilityList";
	}
	
	//관리자 - 예약확정(대관)
	@RequestMapping(value="/facility_confirm", method=RequestMethod.GET)
	public String admin_facConfirm(@RequestParam("id") int id, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
			
	    adminService.setFacilityStatus(id, 1);
	    
	    // 총 게시물 수 
	    int totalListCnt = adminService.findAllFacilityCnt();

	    // 생성인자로  총 게시물 수, 현재 페이지를 전달
	    PagingVO pagination = new PagingVO(totalListCnt, page);

	    // DB select start index
	    int startIndex = pagination.getStartIndex();
	    // 페이지 당 보여지는 게시글의 최대 개수
	    int pageSize = pagination.getPageSize();

	    facility_list = adminService.findFacilityListPaging(startIndex, pageSize);
		    
	    model.addAttribute("facility_list", facility_list);
		model.addAttribute("nowpage", page);
			 
		return "/admin_facilityList";
	}
	
	//대관신청서 파일 다운로드
	@RequestMapping({"/application_download"})
	@ResponseBody
	public ResponseEntity<byte[]> application_download(@RequestParam String filename) throws IOException {
		return s3Service.getObject_application(filename);
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
		model.addAttribute("pagination", pagination);
			 
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
	public String admin_news_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute NewsVO newsvo, ModelMap model,@RequestAttribute List<MultipartFile> news_file) throws Exception {
		
		System.out.println(news_file);
		
		MultipartFile newsPoster = multipartRequest.getFile("thumbnail_file");
		System.out.println(newsPoster);
		
		if(newsPoster.getOriginalFilename() != "") {
			String filename = s3Service.upload_Newsposter(newsPoster);
			System.out.println("s3 insert ok => "+filename);
			newsvo.setNews_poster(filename);
		}
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
		public ModelAndView newsDetail(@RequestParam("id") int news_id) throws Exception{
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
				
			String oldFilename = adminService.getOldNewsPoster(newsVo.getId());
			//지울 파일 리스트
			String[] deleteFileNameList = multipartRequest.getParameterValues("deleteFileNameList");
			String[] deleteFileNameList_thumbnail = multipartRequest.getParameterValues("deleteFileNameList_thumbnail");
			System.out.println("deletefile = "+deleteFileNameList_thumbnail);
			
			//수정 시 지운파일 삭제
			if(deleteFileNameList != null) {
				for( String name : deleteFileNameList ) {
					s3Service.delete_s3News(name);
					adminService.deleteFile(newsVo.getId(), name);
				}
			}
			
			if(deleteFileNameList_thumbnail != null) {
				for( String name : deleteFileNameList_thumbnail ) {
					s3Service.delete_s3News(name);
				}
			}
			
			MultipartFile newsPoster = multipartRequest.getFile("thumbnail_file");
			if(newsPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Newsposter(newsPoster);
				System.out.println("s3 insert ok => "+filename);
				newsVo.setNews_poster(filename);
			}else {
				newsVo.setNews_poster(oldFilename);
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
		
		//관리자 - 팝업설정 리스트
		@RequestMapping(value="/admin_popupList", method=RequestMethod.GET)
		public String admin_popupList(@ModelAttribute PopupVO popupVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
			// 총 게시물 수 
		    int totalListCnt = adminService.findAllPopup();

		    // 생성인자로  총 게시물 수, 현재 페이지를 전달
		    PagingVO pagination = new PagingVO(totalListCnt, page);

		    // DB select start index
		    int startIndex = pagination.getStartIndex();
		    // 페이지 당 보여지는 게시글의 최대 개수
		    int pageSize = pagination.getPageSize();

		    popup_list = adminService.findPopupPaging(startIndex, pageSize);
		    
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
			model.addAttribute("popup_list", popup_list);
			model.addAttribute("nowpage", page);
			model.addAttribute("pagination", pagination);
				 
			return "/admin_popupList";
		}
		
		//팝업 조회_관리자
		@RequestMapping(value="/admin_popupDetail", method=RequestMethod.GET)
		public ModelAndView popupDetail(@RequestParam("id") int popup_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			PopupVO detailVo = adminService.viewPopupDetail(popup_id);
			
	    	if(detailVo.getClick_link() != null) {
	    		mav.addObject("click_link", detailVo.getClick_link());
	    	}
	    	
			mav.addObject("popup", detailVo);
			mav.setViewName("admin_popupDetail");
			return mav;
		}
		
		//관리자 - 팝업 등록 페이지
		@RequestMapping(value="/admin_popupInsert", method=RequestMethod.GET)
		public String admin_popupInsert(ModelMap model) throws Exception {
			
			int popup_cnt = webService.get_popup_cnt();
			ArrayList<String> dates = new ArrayList<String>();
			//팝업 설정 최대 2개 -> 더 이상 설정못하도록 setting
			if(popup_cnt != 0) {
				popup_list = webService.getAllPopup();
				
				for(PopupVO vo : popup_list) { 
					String start_date = vo.getStart_date();
			    	String end_date = vo.getEnd_date();
			    	
			    	// 포맷터
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			        // 문자열 -> Date
			        Date StartDate = formatter.parse(start_date);
			        Date EndDate = formatter.parse(end_date);
			        
			 		Date currentDate = StartDate;
			 		//팝업 설정된 기간 dates 배열에 담기
			 		while (currentDate.compareTo(EndDate) <= 0) {
			 			dates.add(formatter.format(currentDate));
			 			Calendar c = Calendar.getInstance();
			 			c.setTime(currentDate);
			 			c.add(Calendar.DAY_OF_MONTH, 1);
			 			currentDate = c.getTime();
			 		}
			     }
			}
			//stream으로 중복요소 찾아서 datepicker에 해당일자 popup 설정 불가 반영
			List<String> list = dates;
			List<String> distinctList = list.stream()
			                            .distinct()
			                            .collect(Collectors.toList());

			for (String el : distinctList) {
			  list.remove(el);
			}
			
			model.addAttribute("disable_dates", list);
				
			return "/admin_popupInsert";
		}
		
		//팝업 등록(post)
		@ResponseBody
		@RequestMapping(value="/popupAdd", method=RequestMethod.POST)
		public String admin_popup_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute PopupVO popupVo, ModelMap model) throws Exception {
			
			MultipartFile popup_img = multipartRequest.getFile("thumbnail_file");
			System.out.println(popup_img);
			
			if(popup_img.getOriginalFilename() != "") {
				String filename = s3Service.upload_PopupImg(popup_img);
				System.out.println("s3 insert ok => "+filename);
				popupVo.setFile_name(filename);
			}
			adminService.insertPopup(popupVo);
			
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		    String time = dateFormat.format(cal.getTime());
		    
			return "/admin_popupList";
		}	
		
		//팝업 삭제
		@ResponseBody
		@RequestMapping(value = "popup_delete", method = RequestMethod.POST)
		public String popup_delete(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				String file_name = adminService.get_popup_FileName(c);
				s3Service.delete_s3Popup(file_name);
				adminService.popup_delete(c);
			}
			return String.valueOf(cnt);
		}
		
		//팝업 수정 페이지
		@RequestMapping(value="/admin_popup_update", method=RequestMethod.GET)
		public ModelAndView admin_popup_update(@RequestParam("id") int popup_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			PopupVO detailVo = adminService.viewPopupDetail(popup_id);
			
			int popup_cnt = webService.get_popup_cnt();
			ArrayList<String> dates = new ArrayList<String>();
			//팝업 설정 최대 2개 -> 더 이상 설정못하도록 setting
			if(popup_cnt != 0) {
				popup_list = webService.getAllPopup();
				
				for(PopupVO vo : popup_list) { 
					String start_date = vo.getStart_date();
			    	String end_date = vo.getEnd_date();
			    	
			    	// 포맷터
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			        // 문자열 -> Date
			        Date StartDate = formatter.parse(start_date);
			        Date EndDate = formatter.parse(end_date);
			        
			 		Date currentDate = StartDate;
			 		//팝업 설정된 기간 dates 배열에 담기
			 		while (currentDate.compareTo(EndDate) <= 0) {
			 			dates.add(formatter.format(currentDate));
			 			Calendar c = Calendar.getInstance();
			 			c.setTime(currentDate);
			 			c.add(Calendar.DAY_OF_MONTH, 1);
			 			currentDate = c.getTime();
			 		}
			     }
			}
			//stream으로 중복요소 찾아서 datepicker에 해당일자 popup 설정 불가 반영
			List<String> list = dates;
			List<String> distinctList = list.stream()
			                            .distinct()
			                            .collect(Collectors.toList());

			for (String el : distinctList) {
			  list.remove(el);
			}
			
			mav.addObject("disable_dates", list);
			mav.addObject("popup", detailVo);

			mav.setViewName("admin_popupUpdate");
			return mav;
		}
		
		//팝업 소식 수정
		@ResponseBody
		@RequestMapping(value="/popupUpdate", method=RequestMethod.POST)
		public String popupUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute PopupVO popupVo) throws Exception{
				
			String oldFilename = adminService.getOldPopupImage(popupVo.getId());
			//지울 파일 리스트
			String[] deleteFileNameList_thumbnail = multipartRequest.getParameterValues("deleteFileNameList_thumbnail");
			System.out.println("deletefile = "+deleteFileNameList_thumbnail);
			
			//수정 시 지운파일 삭제
			if(deleteFileNameList_thumbnail != null) {
				for( String name : deleteFileNameList_thumbnail ) {
					s3Service.delete_s3Popup(name);
				}
			}
			
			MultipartFile PopupImage = multipartRequest.getFile("thumbnail_file");
			if(PopupImage.getOriginalFilename() != "") {
				String filename = s3Service.upload_PopupImg(PopupImage);
				System.out.println("s3 insert ok => "+filename);
				popupVo.setFile_name(filename);
			}else {
				popupVo.setFile_name(oldFilename);
			}
			
			
			//팝업 수정
			adminService.updatePopup(popupVo);
				
			return "admin_popupDetail?id="+popupVo.getId();
		}
		
		//-------------------------------이벤트--------------------------
		//관리자 - 이벤트 리스트
		@RequestMapping(value="/admin_eventList", method=RequestMethod.GET)
		public String admin_eventList(@ModelAttribute EventVO eventVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
			// 총 게시물 수 
		    int totalListCnt = adminService.findAllEvents();

		    // 생성인자로  총 게시물 수, 현재 페이지를 전달
		    PagingVO pagination = new PagingVO(totalListCnt, page);

		    // DB select start index
		    int startIndex = pagination.getStartIndex();
		    // 페이지 당 보여지는 게시글의 최대 개수
		    int pageSize = pagination.getPageSize();

		    event_list = adminService.findEventPaging(startIndex, pageSize);
		    
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		    for(EventVO event : event_list) {
		    	String event_date = event.getCreate_date();
		    	event_date = event_date.substring(0, 4) + "." + event_date.substring(5, 7) +"." + event_date.substring(8, 10);
		    	event.setCreate_date(event_date);
		    }
			    
			model.addAttribute("event_list", event_list);
			model.addAttribute("nowpage", page);
			model.addAttribute("pagination", pagination);
				 
			return "/admin_eventList";
		}
		
		//관리자 - 이벤트 등록 페이지
		@RequestMapping(value="/admin_eventInsert", method=RequestMethod.GET)
		public String admin_eventInsert(ModelMap model) throws Exception {
				
			return "/admin_eventInsert";
		}
		
		//이벤트 등록(post)
		@ResponseBody
		@RequestMapping(value="/eventAdd", method=RequestMethod.POST)
		public String admin_event_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute EventVO eventvo, ModelMap model,@RequestAttribute List<MultipartFile> event_file) throws Exception {
			
			MultipartFile eventPoster = multipartRequest.getFile("thumbnail_file");
			System.out.println(eventPoster);
			
			if(eventPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Eventposter(eventPoster);
				System.out.println("s3 insert ok => "+filename);
				eventvo.setEvent_poster(filename);
			}
			adminService.insertEvent(eventvo);
			
			int event_id = adminService.get_event_Id(eventvo.getId());
		    
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		    String time = dateFormat.format(cal.getTime());
		    
		    if(event_file != null) {
		    	List<String> filenames = s3Service.upload_event(event_file);
				for(String name : filenames) {
					EventFileVo eventFileVo = new EventFileVo();
					eventFileVo.setEvent_id(event_id);
					eventFileVo.setFile_name(name);
					adminService.insertEventFile(eventFileVo);
				}
			}
		    
			return "/admin_eventList";
		}	
		
		//박물관 이벤트 조회_관리자
		@RequestMapping(value="/admin_eventDetail", method=RequestMethod.GET)
		public ModelAndView eventDetail(@RequestParam("id") int event_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			EventVO detailVo = adminService.viewEventDetail(event_id);
				
			List<EventFileVo> eventFileList = adminService.viewEventFileDetail(event_id);
			String event_date = detailVo.getCreate_date();
		   	event_date = event_date.substring(0, 4) + "." + event_date.substring(5, 7) +"." + event_date.substring(8, 10);
		   	detailVo.setCreate_date(event_date);
		    	
		   	if(detailVo.getEvent_link1() != null) {
		   		mav.addObject("event_link1", detailVo.getEvent_link1());
		   	}
		   	if(detailVo.getEvent_link2() != null) {
		   		mav.addObject("event_link2", detailVo.getEvent_link2());
		   	}
		    	
			mav.addObject("event", detailVo);
			mav.addObject("eventFileList", eventFileList);
			mav.setViewName("admin_eventDetail");
			return mav;
		}
			
		//박물관 이벤트 파일 다운로드
		@RequestMapping({"/event_download"})
		@ResponseBody
		public ResponseEntity<byte[]> event_download(@RequestParam String filename) throws IOException {
			return s3Service.getObject_event(filename);
		}
			
		//박물관 이벤트 수정 페이지
		@RequestMapping(value="/admin_event_update", method=RequestMethod.GET)
		public ModelAndView admin_event_update(@RequestParam("id") int event_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			EventVO detailVo = adminService.viewEventDetail(event_id);
			
			List<EventFileVo> eventFileList = adminService.viewEventFileDetail(event_id);
			
			mav.addObject("event", detailVo);
			mav.addObject("eventFileList", eventFileList);

			mav.setViewName("admin_eventUpdate");
			return mav;
		}
			
		//박물관 이벤트 수정
		@ResponseBody
		@RequestMapping(value="/eventUpdate", method=RequestMethod.POST)
		public String eventUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute EventVO eventVo, @RequestAttribute("event_file") List<MultipartFile> event_file) throws Exception{
				
			String oldFilename = adminService.getOldEventPoster(eventVo.getId());
			//지울 파일 리스트
			String[] deleteFileNameList = multipartRequest.getParameterValues("deleteFileNameList");
			String[] deleteFileNameList_thumbnail = multipartRequest.getParameterValues("deleteFileNameList_thumbnail");
			System.out.println("deletefile = "+deleteFileNameList);
				
			//수정 시 지운파일 삭제
			if(deleteFileNameList != null) {
				for( String name : deleteFileNameList) {
					s3Service.delete_s3Event(name);
					adminService.deleteEventFile(eventVo.getId(), name);
					System.out.println("deletefile = "+name);
				}
			}
				
			if(deleteFileNameList_thumbnail != null) {
				for( String name : deleteFileNameList_thumbnail ) {
					s3Service.delete_s3Event(name);
				}
			}
				
			MultipartFile eventPoster = multipartRequest.getFile("thumbnail_file");
			if(eventPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Eventposter(eventPoster);
				System.out.println("s3 insert ok => "+filename);
				eventVo.setEvent_poster(filename);
			}else {
				eventVo.setEvent_poster(oldFilename);
			}
				
			//행사 내용 수정
			adminService.updateEvent(eventVo);
					
			//수정 시 추가한 파일 추가
			if(event_file != null) {
				List<String> filenames = s3Service.upload_event(event_file);
				for(String name : filenames) {
					EventFileVo eventFileVo = new EventFileVo();
					eventFileVo.setEvent_id(eventVo.getId());
					eventFileVo.setFile_name(name);
					adminService.insertEventFile(eventFileVo);
				}
			}
					
			return "admin_eventDetail?id="+eventVo.getId();
		}
			
		//박물관 이벤트 삭제
		@ResponseBody
		@RequestMapping(value = "event_delete", method = RequestMethod.POST)
		public String event_delete(HttpServletRequest request, ModelMap modelMap,
									@RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				adminService.event_delete(c);
				String[] deleteFileNameList = adminService.getEventFile(c);
					
				if(deleteFileNameList != null) {
					for( String name : deleteFileNameList ) {
						s3Service.delete_s3Event(name);
					}
					adminService.eventFile_delete(c);
				}
			}
			return String.valueOf(cnt);
		}
		
		//-------------------------------제휴안내--------------------------
		//관리자 - 제휴 리스트
		@RequestMapping(value="/admin_partnerList", method=RequestMethod.GET)
		public String admin_partnerList(@ModelAttribute PartnerVO partnerVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
			// 총 게시물 수 
		    int totalListCnt = adminService.findAllPartners();

		    // 생성인자로  총 게시물 수, 현재 페이지를 전달
		    PagingVO pagination = new PagingVO(totalListCnt, page);

		    // DB select start index
		    int startIndex = pagination.getStartIndex();
		    // 페이지 당 보여지는 게시글의 최대 개수
		    int pageSize = pagination.getPageSize();

		    partner_list = adminService.findPartnerPaging(startIndex, pageSize);
		    
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		    for(PartnerVO partner : partner_list) {
		    	String partner_date = partner.getCreate_date();
		    	partner_date = partner_date.substring(0, 4) + "." + partner_date.substring(5, 7) +"." + partner_date.substring(8, 10);
		    	partner.setCreate_date(partner_date);
		    }
			    
			model.addAttribute("partner_list", partner_list);
			model.addAttribute("nowpage", page);
			model.addAttribute("pagination", pagination);
				 
			return "/admin_partnerList";
		}
		
		//관리자 - 제휴안내 등록 페이지
		@RequestMapping(value="/admin_partnerInsert", method=RequestMethod.GET)
		public String admin_partnerInsert(ModelMap model) throws Exception {
				
			return "/admin_partnerInsert";
		}
		
		//제휴안내 등록(post)
		@ResponseBody
		@RequestMapping(value="/partnerAdd", method=RequestMethod.POST)
		public String admin_partner_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute PartnerVO partnervo, ModelMap model,@RequestAttribute List<MultipartFile> partner_file) throws Exception {
			
			MultipartFile partnerPoster = multipartRequest.getFile("thumbnail_file");
			System.out.println(partnerPoster);
			
			if(partnerPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Partnerposter(partnerPoster);
				System.out.println("s3 insert ok => "+filename);
				partnervo.setPartner_poster(filename);
			}
			adminService.insertPartner(partnervo);
			
			int partner_id = adminService.get_partner_Id(partnervo.getId());
		    
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		    String time = dateFormat.format(cal.getTime());
		    
		    if(partner_file != null) {
		    	List<String> filenames = s3Service.upload_partner(partner_file);
				for(String name : filenames) {
					PartnerFileVo partnerFileVo = new PartnerFileVo();
					partnerFileVo.setPartner_id(partner_id);
					partnerFileVo.setFile_name(name);
					adminService.insertPartnerFile(partnerFileVo);
				}
			}
		    
			return "/admin_partnerList";
		}	
		
		//제휴안내 조회_관리자
		@RequestMapping(value="/admin_partnerDetail", method=RequestMethod.GET)
		public ModelAndView partnerDetail(@RequestParam("id") int partner_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			PartnerVO detailVo = adminService.viewPartnerDetail(partner_id);
				
			List<PartnerFileVo> partnerFileList = adminService.viewPartnerFileDetail(partner_id);
			String partner_date = detailVo.getCreate_date();
			partner_date = partner_date.substring(0, 4) + "." + partner_date.substring(5, 7) +"." + partner_date.substring(8, 10);
		   	detailVo.setCreate_date(partner_date);
		    	
			mav.addObject("partner", detailVo);
			mav.addObject("partnerFileList", partnerFileList);
			mav.setViewName("admin_partnerDetail");
			return mav;
		}
			
		//제휴안내 파일 다운로드
		@RequestMapping({"/partner_download"})
		@ResponseBody
		public ResponseEntity<byte[]> partner_download(@RequestParam String filename) throws IOException {
			return s3Service.getObject_partner(filename);
		}
			
		//제휴안내 수정 페이지
		@RequestMapping(value="/admin_partner_update", method=RequestMethod.GET)
		public ModelAndView admin_partner_update(@RequestParam("id") int partner_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			PartnerVO detailVo = adminService.viewPartnerDetail(partner_id);
			
			List<PartnerFileVo> partnerFileList = adminService.viewPartnerFileDetail(partner_id);
			
			mav.addObject("partner", detailVo);
			mav.addObject("partnerFileList", partnerFileList);

			mav.setViewName("admin_partnerUpdate");
			return mav;
		}
			
		//제휴안내 수정
		@ResponseBody
		@RequestMapping(value="/partnerUpdate", method=RequestMethod.POST)
		public String partnerUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute PartnerVO partnerVo, @RequestAttribute("partner_file") List<MultipartFile> partner_file) throws Exception{
				
			String oldFilename = adminService.getOldPartnerPoster(partnerVo.getId());
			//지울 파일 리스트
			String[] deleteFileNameList = multipartRequest.getParameterValues("deleteFileNameList");
			String[] deleteFileNameList_thumbnail = multipartRequest.getParameterValues("deleteFileNameList_thumbnail");
			System.out.println("deletefile = "+deleteFileNameList);
				
			//수정 시 지운파일 삭제
			if(deleteFileNameList != null) {
				for( String name : deleteFileNameList) {
					s3Service.delete_s3Partner(name);
					adminService.deletePartnerFile(partnerVo.getId(), name);
					System.out.println("deletefile = "+name);
				}
			}
				
			if(deleteFileNameList_thumbnail != null) {
				for( String name : deleteFileNameList_thumbnail ) {
					s3Service.delete_s3Partner(name);
				}
			}
				
			MultipartFile partnerPoster = multipartRequest.getFile("thumbnail_file");
			if(partnerPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Partnerposter(partnerPoster);
				System.out.println("s3 insert ok => "+filename);
				partnerVo.setPartner_poster(filename);
			}else {
				partnerVo.setPartner_poster(oldFilename);
			}
				
			//행사 내용 수정
			adminService.updatePartner(partnerVo);
					
			//수정 시 추가한 파일 추가
			if(partner_file != null) {
				List<String> filenames = s3Service.upload_partner(partner_file);
				for(String name : filenames) {
					PartnerFileVo partnerFileVo = new PartnerFileVo();
					partnerFileVo.setPartner_id(partnerVo.getId());
					partnerFileVo.setFile_name(name);
					adminService.insertPartnerFile(partnerFileVo);
				}
			}
					
			return "admin_partnerDetail?id="+partnerVo.getId();
		}
			
		//제휴안내 삭제
		@ResponseBody
		@RequestMapping(value = "partner_delete", method = RequestMethod.POST)
		public String partner_delete(HttpServletRequest request, ModelMap modelMap,
									@RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				adminService.partner_delete(c);
				String[] deleteFileNameList = adminService.getPartnerFile(c);
					
				if(deleteFileNameList != null) {
					for( String name : deleteFileNameList ) {
						s3Service.delete_s3Partner(name);
					}
					adminService.partnerFile_delete(c);
				}
			}
			return String.valueOf(cnt);
		}
		
		//-------------------------------지난교육--------------------------
		//관리자 - 지난교육 리스트
		@RequestMapping(value="/admin_eduList", method=RequestMethod.GET)
		public String admin_eduList(@ModelAttribute EduVO eduVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
			// 총 게시물 수 
		    int totalListCnt = adminService.findAllEdu();

		    // 생성인자로  총 게시물 수, 현재 페이지를 전달
		    PagingVO pagination = new PagingVO(totalListCnt, page);

		    // DB select start index
		    int startIndex = pagination.getStartIndex();
		    // 페이지 당 보여지는 게시글의 최대 개수
		    int pageSize = pagination.getPageSize();

		    edu_list = adminService.findEduPaging(startIndex, pageSize);
		    
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		    for(EduVO edu : edu_list) {
		    	String edu_date = edu.getCreate_date();
		    	edu_date = edu_date.substring(0, 4) + "." + edu_date.substring(5, 7) +"." + edu_date.substring(8, 10);
		    	edu.setCreate_date(edu_date);
		    }
			    
			model.addAttribute("edu_list", edu_list);
			model.addAttribute("nowpage", page);
			model.addAttribute("pagination", pagination);
				 
			return "/admin_eduList";
		}
		
		//관리자 - 지난교육 등록 페이지
		@RequestMapping(value="/admin_eduInsert", method=RequestMethod.GET)
		public String admin_eduInsert(ModelMap model) throws Exception {
				
			return "/admin_eduInsert";
		}
		
		//지난교육 등록(post)
		@ResponseBody
		@RequestMapping(value="/eduAdd", method=RequestMethod.POST)
		public String admin_edu_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute EduVO eduvo, ModelMap model,@RequestAttribute List<MultipartFile> edu_file) throws Exception {
			
			MultipartFile eduPoster = multipartRequest.getFile("thumbnail_file");
			System.out.println(eduPoster);
			
			if(eduPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Eduposter(eduPoster);
				System.out.println("s3 insert ok => "+filename);
				eduvo.setEdu_poster(filename);
			}
			adminService.insertEdu(eduvo);
			
			int edu_id = adminService.get_edu_Id(eduvo.getId());
		    
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		    String time = dateFormat.format(cal.getTime());
		    
		    if(edu_file != null) {
		    	List<String> filenames = s3Service.upload_edu(edu_file);
				for(String name : filenames) {
					EduFileVo eduFileVo = new EduFileVo();
					eduFileVo.setEdu_id(edu_id);
					eduFileVo.setFile_name(name);
					adminService.insertEduFile(eduFileVo);
				}
			}
		    
			return "/admin_eduList";
		}	
		
		//지난교육 조회_관리자
		@RequestMapping(value="/admin_eduDetail", method=RequestMethod.GET)
		public ModelAndView eduDetail(@RequestParam("id") int edu_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			EduVO detailVo = adminService.viewEduDetail(edu_id);
				
			List<EduFileVo> eduFileList = adminService.viewEduFileDetail(edu_id);
			String edu_date = detailVo.getCreate_date();
			edu_date = edu_date.substring(0, 4) + "." + edu_date.substring(5, 7) +"." + edu_date.substring(8, 10);
		   	detailVo.setCreate_date(edu_date);
		    	
			mav.addObject("edu", detailVo);
			mav.addObject("eduFileList", eduFileList);
			mav.setViewName("admin_eduDetail");
			return mav;
		}
			
		//지난교육 파일 다운로드
		@RequestMapping({"/edu_download"})
		@ResponseBody
		public ResponseEntity<byte[]> edu_download(@RequestParam String filename) throws IOException {
			return s3Service.getObject_edu(filename);
		}
			
		//지난교육 수정 페이지
		@RequestMapping(value="/admin_edu_update", method=RequestMethod.GET)
		public ModelAndView admin_edu_update(@RequestParam("id") int edu_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			EduVO detailVo = adminService.viewEduDetail(edu_id);
			
			List<EduFileVo> eduFileList = adminService.viewEduFileDetail(edu_id);
			
			mav.addObject("edu", detailVo);
			mav.addObject("eduFileList", eduFileList);

			mav.setViewName("admin_eduUpdate");
			return mav;
		}
			
		//지난교육 수정
		@ResponseBody
		@RequestMapping(value="/eduUpdate", method=RequestMethod.POST)
		public String eduUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute EduVO eduVo, @RequestAttribute("edu_file") List<MultipartFile> edu_file) throws Exception{
				
			String oldFilename = adminService.getOldEduPoster(eduVo.getId());
			//지울 파일 리스트
			String[] deleteFileNameList = multipartRequest.getParameterValues("deleteFileNameList");
			String[] deleteFileNameList_thumbnail = multipartRequest.getParameterValues("deleteFileNameList_thumbnail");
			System.out.println("deletefile = "+deleteFileNameList);
				
			//수정 시 지운파일 삭제
			if(deleteFileNameList != null) {
				for( String name : deleteFileNameList) {
					s3Service.delete_s3Edu(name);
					adminService.deleteEduFile(eduVo.getId(), name);
					System.out.println("deletefile = "+name);
				}
			}
				
			if(deleteFileNameList_thumbnail != null) {
				for( String name : deleteFileNameList_thumbnail ) {
					s3Service.delete_s3Edu(name);
				}
			}
				
			MultipartFile eduPoster = multipartRequest.getFile("thumbnail_file");
			if(eduPoster.getOriginalFilename() != "") {
				String filename = s3Service.upload_Eduposter(eduPoster);
				System.out.println("s3 insert ok => "+filename);
				eduVo.setEdu_poster(filename);
			}else {
				eduVo.setEdu_poster(oldFilename);
			}
				
			//행사 내용 수정
			adminService.updateEdu(eduVo);
					
			//수정 시 추가한 파일 추가
			if(edu_file != null) {
				List<String> filenames = s3Service.upload_edu(edu_file);
				for(String name : filenames) {
					EduFileVo eduFileVo = new EduFileVo();
					eduFileVo.setEdu_id(eduVo.getId());
					eduFileVo.setFile_name(name);
					adminService.insertEduFile(eduFileVo);
				}
			}
					
			return "admin_eduDetail?id="+eduVo.getId();
		}
			
		//지난교육 삭제
		@ResponseBody
		@RequestMapping(value = "edu_delete", method = RequestMethod.POST)
		public String edu_delete(HttpServletRequest request, ModelMap modelMap,
									@RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				adminService.edu_delete(c);
				String[] deleteFileNameList = adminService.getEduFile(c);
					
				if(deleteFileNameList != null) {
					for( String name : deleteFileNameList ) {
						s3Service.delete_s3Edu(name);
					}
					adminService.eduFile_delete(c);
				}
			}
			return String.valueOf(cnt);
		}
		
		//-------------------------------공고--------------------------
		//관리자 - 공고 리스트
		@RequestMapping(value="/admin_noticeList", method=RequestMethod.GET)
		public String admin_noticeList(@ModelAttribute NoticeVO noticeVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
			// 총 게시물 수 
		    int totalListCnt = adminService.findAllNotice();

		    // 생성인자로  총 게시물 수, 현재 페이지를 전달
		    PagingVO pagination = new PagingVO(totalListCnt, page);

		    // DB select start index
		    int startIndex = pagination.getStartIndex();
		    // 페이지 당 보여지는 게시글의 최대 개수
		    int pageSize = pagination.getPageSize();

		    notice_list = adminService.findNoticePaging(startIndex, pageSize);
		    
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		    for(NoticeVO notice : notice_list) {
		    	String notice_date = notice.getCreate_date();
		    	notice_date = notice_date.substring(0, 4) + "." + notice_date.substring(5, 7) +"." + notice_date.substring(8, 10);
		    	notice.setCreate_date(notice_date);
		    }
			    
			model.addAttribute("notice_list", notice_list);
			model.addAttribute("nowpage", page);
			model.addAttribute("pagination", pagination);
				 
			return "/admin_noticeList";
		}
		
		//관리자 - 공고 등록 페이지
		@RequestMapping(value="/admin_noticeInsert", method=RequestMethod.GET)
		public String admin_noticeInsert(ModelMap model) throws Exception {
				
			return "/admin_noticeInsert";
		}
		
		//공고 등록(post)
		@ResponseBody
		@RequestMapping(value="/noticeAdd", method=RequestMethod.POST)
		public String admin_notice_register(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, @ModelAttribute NoticeVO noticevo, ModelMap model, @RequestAttribute List<MultipartFile> notice_file) throws Exception {
			
			// 오늘 날짜
		    LocalDate now = LocalDate.now();
		    Calendar time = Calendar.getInstance();
		    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		    Calendar cal = Calendar.getInstance();
		    String today = dateFormat.format(cal.getTime());
		    
	    	noticevo.setNotice_date(today);
	    	
			adminService.insertNotice(noticevo);
			
			int notice_id = adminService.get_notice_Id(noticevo.getId());
		    
		    if(notice_file != null) {
		    	List<String> filenames = s3Service.upload_notice(notice_file);
				for(String name : filenames) {
					NoticeFileVo noticeFileVo = new NoticeFileVo();
					noticeFileVo.setNotice_id(notice_id);
					noticeFileVo.setFile_name(name);
					adminService.insertNoticeFile(noticeFileVo);
				}
			}
		    
			return "/admin_noticeList";
		}	
		
		//공고 조회_관리자
		@RequestMapping(value="/admin_noticeDetail", method=RequestMethod.GET)
		public ModelAndView noticeDetail(@RequestParam("id") int notice_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			NoticeVO detailVo = adminService.viewNoticeDetail(notice_id);
				
			List<NoticeFileVo> noticeFileList = adminService.viewNoticeFileDetail(notice_id);
//			String notice_date = detailVo.getCreate_date();
//			notice_date = notice_date.substring(0, 4) + "." + notice_date.substring(5, 7) +"." + notice_date.substring(8, 10);
//		   	detailVo.setCreate_date(notice_date);
		    	
			mav.addObject("notice", detailVo);
			mav.addObject("noticeFileList", noticeFileList);
			mav.setViewName("admin_noticeDetail");
			return mav;
		}
			
		//공고 파일 다운로드
		@RequestMapping({"/notice_download"})
		@ResponseBody
		public ResponseEntity<byte[]> notice_download(@RequestParam String filename) throws IOException {
			return s3Service.getObject_notice(filename);
		}
			
		//공고 수정 페이지
		@RequestMapping(value="/admin_notice_update", method=RequestMethod.GET)
		public ModelAndView admin_notice_update(@RequestParam("id") int notice_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			NoticeVO detailVo = adminService.viewNoticeDetail(notice_id);
			
			List<NoticeFileVo> noticeFileList = adminService.viewNoticeFileDetail(notice_id);
			
			mav.addObject("notice", detailVo);
			mav.addObject("noticeFileList", noticeFileList);

			mav.setViewName("admin_noticeUpdate");
			return mav;
		}
			
		//공고 수정
		@ResponseBody
		@RequestMapping(value="/noticeUpdate", method=RequestMethod.POST)
		public String noticeUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute NoticeVO noticeVo, @RequestAttribute("notice_file") List<MultipartFile> notice_file) throws Exception{
				
			//지울 파일 리스트
			String[] deleteFileNameList = multipartRequest.getParameterValues("deleteFileNameList");
			//수정 시 지운파일 삭제
			if(deleteFileNameList != null) {
				for( String name : deleteFileNameList) {
					s3Service.delete_s3Notice(name);
					adminService.deleteNoticeFile(noticeVo.getId(), name);
					System.out.println("deletefile = "+name);
				}
			}
				
			//행사 내용 수정
			adminService.updateNotice(noticeVo);
					
			//수정 시 추가한 파일 추가
			if(notice_file != null) {
				List<String> filenames = s3Service.upload_notice(notice_file);
				for(String name : filenames) {
					NoticeFileVo noticeFileVo = new NoticeFileVo();
					noticeFileVo.setNotice_id(noticeVo.getId());
					noticeFileVo.setFile_name(name);
					adminService.insertNoticeFile(noticeFileVo);
				}
			}
					
			return "admin_noticeDetail?id="+noticeVo.getId();
		}
			
		//공고 삭제
		@ResponseBody
		@RequestMapping(value = "notice_delete", method = RequestMethod.POST)
		public String notice_delete(HttpServletRequest request, ModelMap modelMap,
									@RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				adminService.notice_delete(c);
				String[] deleteFileNameList = adminService.getNoticeFile(c);
					
				if(deleteFileNameList != null) {
					for( String name : deleteFileNameList ) {
						s3Service.delete_s3Notice(name);
					}
					adminService.noticeFile_delete(c);
				}
			}
			return String.valueOf(cnt);
		}
		
		//Q&A 관리-------------------------------------------
		//관리자 - Q&A 리스트
		@RequestMapping(value="/admin_qnaList", method=RequestMethod.GET)
		public String admin_qnaList(@ModelAttribute QuestionVO questionVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
			// 총 게시물 수 
		    int totalListCnt = adminService.findAllQuestions();

		    // 생성인자로  총 게시물 수, 현재 페이지를 전달
		    PagingVO pagination = new PagingVO(totalListCnt, page);

		    // DB select start index
		    int startIndex = pagination.getStartIndex();
		    // 페이지 당 보여지는 게시글의 최대 개수
		    int pageSize = pagination.getPageSize();

		    question_list = adminService.findQuestionPaging(startIndex, pageSize);
		    
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		    for(QuestionVO question : question_list) {
		    	String question_date = question.getCreate_date();
		    	question_date = question_date.substring(0, 4) + "." + question_date.substring(5, 7) +"." + question_date.substring(8, 10);
		    	question.setCreate_date(question_date);
		    }
			    
			model.addAttribute("question_list", question_list);
			model.addAttribute("nowpage", page);
			model.addAttribute("pagination", pagination);
				 
			return "/admin_qnaList";
		}
		
		//Q&A 조회_관리자
		@RequestMapping(value="/admin_qnaDetail", method=RequestMethod.GET)
		public ModelAndView qnaDetail(@RequestParam("id") int question_id) throws Exception{
			ModelAndView mav = new ModelAndView();
			QuestionVO detailVo = adminService.viewQuestionDetail(question_id);
			AnswerVO answerVo = adminService.viewAnswerDetail(question_id);
				
			String question_date = detailVo.getCreate_date();
	    	question_date = question_date.substring(0, 4) + "." + question_date.substring(5, 7) +"." + question_date.substring(8, 10);
	    	detailVo.setCreate_date(question_date);
	    	
			if(answerVo != null) {
				String answer_date = answerVo.getCreate_date();
				answer_date = answer_date.substring(0, 4) + "." + answer_date.substring(5, 7) +"." + answer_date.substring(8, 10);
		    	answerVo.setCreate_date(answer_date);
				mav.addObject("answer", answerVo);
			}
			mav.addObject("question", detailVo);
			mav.setViewName("admin_qnaDetail");
			return mav;
		}
		
		//Q&A 삭제
		@ResponseBody
		@RequestMapping(value = "question_delete", method = RequestMethod.POST)
		public String question_delete(HttpServletRequest request, ModelMap modelMap,
									@RequestParam(value = "check[]", defaultValue = "") List<String> check) {

			int cnt = 0;

			for (String c : check) {
				adminService.question_delete(c);
				adminService.answer_delete_all(c);
			}
			return String.valueOf(cnt);
		}
		
		
		//Q&A 각 상세글에서 삭제
		@ResponseBody
		@RequestMapping(value = "question_delete_one", method = RequestMethod.POST)
		public String question_delete_one(HttpServletRequest request, ModelMap modelMap, @RequestParam("id") int question_id) {

			AnswerVO answerVo = adminService.viewAnswerDetail(question_id);
			
			adminService.question_delete_one(question_id);
			if(answerVo != null) {
				adminService.answer_delete_one(question_id);
			}
				
			return String.valueOf(question_id);
		}
		
		//문의글 답변 등록(post)
		@ResponseBody
		@RequestMapping(value="/answerAdd", method=RequestMethod.POST)
		public String admin_answer_register(HttpServletRequest request, @ModelAttribute AnswerVO answervo, ModelMap model) throws Exception {
			
			adminService.insertAnswer(answervo);
			
			int question_id = answervo.getQuestion_id();
			adminService.change_question_status(question_id);
		    
			return "/admin_qnaDetail?id="+question_id;
		}
		
		//문의글 답변 삭제
		@ResponseBody
		@RequestMapping(value = "answer_delete", method = RequestMethod.POST)
		public String answer_delete(HttpServletRequest request, ModelMap modelMap, @RequestParam("id") int answer_id) {

			int question_id = adminService.get_answer_questionId(answer_id);
			
			adminService.answer_delete(answer_id);
			adminService.revert_question_status(question_id);
				
			return String.valueOf(answer_id);
		}
		
		//문의글 답변 수정
		@ResponseBody
		@RequestMapping(value="/answerUpdate", method=RequestMethod.POST)
		public String answerUpdate(MultipartHttpServletRequest multipartRequest, @ModelAttribute AnswerVO answerVo) throws Exception{
				
			//답변 내용 수정
			adminService.updateAnswer(answerVo);
					
			return "admin_qnaDetail?id="+answerVo.getQuestion_id();
		}
}
