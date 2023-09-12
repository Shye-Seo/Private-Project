package com.voucher.movie.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.board.NewsFileVo;
import com.voucher.movie.board.NewsVO;
import com.voucher.movie.mappers.AdminDao;
import com.voucher.movie.mappers.GroupDao;
import com.voucher.movie.reservation.ClosedVO;
import com.voucher.movie.reservation.FacilitiesVO;
import com.voucher.movie.reservation.GroupVO;
import com.voucher.movie.reservation.TimeVO;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private GroupDao groupDao;
	
	// 관리자 로그인
    public int loginCheck(AdminVO adminVo, HttpSession session) {
        int result = adminDao.loginCheck(adminVo);
        System.out.println(result);

        if(result==1) { // true일 경우 세션에 등록
            AdminVO adminVo1 = viewInfo(adminVo);
            
            int id = adminVo1.getId();
            
            session.setAttribute("user_id", adminVo1.getLogin_id());
            session.setAttribute("user_name", "관리자");
            session.setAttribute("id",id);
            session.setAttribute("authority", adminVo1.getAuthority());

        }
        return result;
    }
    
    // 로그인할때 관리자정보 가져올때
    public AdminVO viewInfo(AdminVO adminVo) {
        return adminDao.viewInfo(adminVo);
    }
    
    /* ---페이징처리--- */
	public int findAllCnt() {
		return adminDao.findAllCnt();
	}
	
	public List<GroupVO> findListPaging(int startIndex, int pageSize) {
		return adminDao.findListPaging(startIndex, pageSize);
	}
	
	// 예약상태 set
	public boolean setStatus(int id, int i) throws Exception {
		return adminDao.setStatus(id, i);
	}

	// 휴관일 설정
	public boolean setClosed(String setting_date) {
		return adminDao.setClosed(setting_date);
		
	}
	
	public boolean setUnclosed(String setting_date) {
		return adminDao.setUnclosed(setting_date);
		
	}

	// 휴관일 상태 check
	public int compareStatus(String setting_date) {
		return adminDao.compareStatus(setting_date);
	}

	//회차설정-정보변경
	public boolean setting_resTime(String setting_date, int time_num, String setting_time, int limited_num, int time_status) {
		return adminDao.setting_resTime(setting_date, time_num, setting_time, limited_num, time_status);
	}
	
	//회차설정-비활성화
	public boolean setting_timeUnable(String setting_date, int time_num, int time_status) {
		return adminDao.setting_timeUnable(setting_date, time_num, time_status);
	}

	public int checkTime(String setting_date, int time_num) {
		return adminDao.checkTime(setting_date, time_num);
	}

	public boolean update_resTime(String setting_date, int time_num, String setting_time, int limited_num, int time_status) {
		return adminDao.update_resTime(setting_date, time_num, setting_time, limited_num, time_status);
	}

	public List<ClosedVO> getClosed() {
		return adminDao.getClosed();
	}

	public List<TimeVO> getTime() {
		return adminDao.getTime();
	}

	public List<GroupVO> getReservation_list(String date) {
		return adminDao.getReservation_list(date);
	}

	public int findAllNews() {
		return adminDao.fintAllNews();
	}

	public List<NewsVO> findNewsPaging(int startIndex, int pageSize) {
		return adminDao.findNewsPaging(startIndex, pageSize);
	}

	public boolean insertNews(NewsVO newsvo) {
		return adminDao.insertNews(newsvo);
	}

	public int get_news_Id(int id) {
		return adminDao.get_news_Id(id);
	}

	public boolean insertNewsFile(NewsFileVo newsFileVo) {
		return adminDao.insertNewsFile(newsFileVo);
	}

	//박물관 소식 상세-관리자
	public NewsVO viewNewsDetail(int news_id) {
		return adminDao.viewNewsDetail(news_id);
	}

	public List<NewsFileVo> viewNewsFileDetail(int news_id) {
		return adminDao.viewNewsFileDetail(news_id);
	}
	
	//박물관 소식 수정
	public boolean updateNews (NewsVO newsVo) throws Exception {
		return adminDao.updateNews(newsVo);
	}
			
	//박물관 소식 파일 삭제
	public boolean deleteFile (int news_id, String file_name) throws Exception {
		return adminDao.deleteFile(news_id, file_name);
	}

	//박물관 소식 삭제
	public boolean news_delete(String c) {
		return adminDao.news_delete(c);
	}
	
	//박물관 소식 삭제 시, 해당 게시글의 모든 첨부파일 삭제
	public boolean newsFile_delete(String c) {
		return adminDao.newsFile_delete(c);
	}

	//박물관 소식 삭제 시, s3에서 해당 게시글의 모든 첨부파일 삭제를 위해 파일리스트 get
	public String[] getNewsFile(String c) {
		return adminDao.getNewsFile(c);
	}

	public String getOldNewsPoster(int id) {
		return adminDao.getOldNewsPoster(id);
	}

	//---------- 팝업설정 ------------
	public int findAllPopup() {
		return adminDao.findAllPopup();
	}

	public List<PopupVO> findPopupPaging(int startIndex, int pageSize) {
		return adminDao.findPopupPaging(startIndex, pageSize);
	}

	public boolean insertPopup(PopupVO popupVo) {
		return adminDao.insertPopup(popupVo);
	}

	public boolean popup_delete(String c) {
		return adminDao.popup_delete(c);
	}

	public String get_popup_FileName(String c) {
		return adminDao.get_popup_FileName(c);
	}

	//팝업 상세
	public PopupVO viewPopupDetail(int popup_id) {
		return adminDao.viewPopupDetail(popup_id);
	}

	//팝업 수정
	public boolean updatePopup (PopupVO popupVo) throws Exception {
		return adminDao.updatePopup(popupVo);
	}

	public String getOldPopupImage(int id) {
		return adminDao.getOldPopupImage(id);
	}

	//대관예약 리스트 get
	public List<FacilitiesVO> getFacility_list(String date) {
		return adminDao.getFacility_list(date);
	}	

}
