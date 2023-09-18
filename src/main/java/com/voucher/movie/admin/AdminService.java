package com.voucher.movie.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public int findAllFacilityCnt() {
		return adminDao.findAllFacilityCnt();
	}
	
	// 예약상태 set(단체)
	public boolean setStatus(int id, int i) throws Exception {
		return adminDao.setStatus(id, i);
	}
	
	// 예약상태 set(대관)
	public boolean setFacilityStatus(int id, int i) throws Exception {
		return adminDao.setFacilityStatus(id, i);
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
	
	
	/*-------------대관--------*/
	//대관예약 리스트 get
	public List<FacilitiesVO> getFacility_list(String date) {
		return adminDao.getFacility_list(date);
	}

	public List<FacilitiesVO> findFacilityListPaging(int startIndex, int pageSize) {
		return adminDao.findFacilityListPaging(startIndex, pageSize);
	}

	//대관예약 확정리스트 get
	public List<FacilitiesVO> getFacility_Reslist() {
		return adminDao.getFacility_Reslist();
	}

	/*-------------스케쥴러--------*/
	//월요일 휴관일 지정(매년 1월1일 00:00 작동)
	public boolean closed_scheduler(String date_str) {
		return adminDao.closed_scheduler(date_str);
	}
	
	/*-------------박물관 이벤트--------*/
	public int findAllEvents() {
		return adminDao.findAllEvents();
	}

	public List<EventVO> findEventPaging(int startIndex, int pageSize) {
		return adminDao.findEventPaging(startIndex, pageSize);
	}

	public boolean insertEvent(EventVO eventvo) {
		return adminDao.insertEvent(eventvo);
	}

	public int get_event_Id(int id) {
		return adminDao.get_event_Id(id);
	}

	public boolean insertEventFile(EventFileVo eventFileVo) {
		return adminDao.insertEventFile(eventFileVo);
	}

	public EventVO viewEventDetail(int event_id) {
		return adminDao.viewEventDetail(event_id);
	}

	public List<EventFileVo> viewEventFileDetail(int event_id) {
		return adminDao.viewEventFileDetail(event_id);
	}

	public String getOldEventPoster(int id) {
		return adminDao.getOldEventPoster(id);
	}

	public boolean deleteEventFile(int id, String name) {
		return adminDao.deleteEventFile(id, name);
	}

	public boolean updateEvent(EventVO eventVo) {
		return adminDao.updateEvent(eventVo);
	}

	public boolean event_delete(String c) {
		return adminDao.event_delete(c);
	}

	public String[] getEventFile(String c) {
		return adminDao.getEventFile(c);
	}

	public boolean eventFile_delete(String c) {
		return adminDao.eventFile_delete(c);
	}

	/*-------------제휴안내--------*/
	public int findAllPartners() {
		return adminDao.findAllPartners();
	}

	public List<PartnerVO> findPartnerPaging(int startIndex, int pageSize) {
		return adminDao.findPartnerPaging(startIndex, pageSize);
	}

	public boolean insertPartner(PartnerVO partnervo) {
		return adminDao.insertPartner(partnervo);
	}

	public int get_partner_Id(int id) {
		return adminDao.get_partner_Id(id);
	}

	public boolean insertPartnerFile(PartnerFileVo partnerFileVo) {
		return adminDao.insertPartnerFile(partnerFileVo);
	}

	public PartnerVO viewPartnerDetail(int partner_id) {
		return adminDao.viewPartnerDetail(partner_id);
	}

	public List<PartnerFileVo> viewPartnerFileDetail(int partner_id) {
		return adminDao.viewPartnerFileDetail(partner_id);
	}

	public String getOldPartnerPoster(int id) {
		return adminDao.getOldPartnerPoster(id);
	}

	public boolean deletePartnerFile(int id, String name) {
		return adminDao.deletePartnerFile(id, name);
	}

	public boolean updatePartner(PartnerVO partnerVo) {
		return adminDao.updatePartner(partnerVo);
	}

	public boolean partner_delete(String c) {
		return adminDao.partner_delete(c);
	}

	public String[] getPartnerFile(String c) {
		return adminDao.getPartnerFile(c);
	}

	public boolean partnerFile_delete(String c) {
		return adminDao.partnerFile_delete(c);
	}	
	
	/*-------------지난교육--------*/
	public int findAllEdu() {
		return adminDao.findAllEdu();
	}

	public List<EduVO> findEduPaging(int startIndex, int pageSize) {
		return adminDao.findEduPaging(startIndex, pageSize);
	}

	public boolean insertEdu(EduVO eduvo) {
		return adminDao.insertEdu(eduvo);
	}

	public int get_edu_Id(int id) {
		return adminDao.get_edu_Id(id);
	}

	public boolean insertEduFile(EduFileVo eduFileVo) {
		return adminDao.insertEduFile(eduFileVo);
	}

	public EduVO viewEduDetail(int edu_id) {
		return adminDao.viewEduDetail(edu_id);
	}

	public List<EduFileVo> viewEduFileDetail(int edu_id) {
		return adminDao.viewEduFileDetail(edu_id);
	}

	public String getOldEduPoster(int id) {
		return adminDao.getOldEduPoster(id);
	}

	public boolean deleteEduFile(int id, String name) {
		return adminDao.deleteEduFile(id, name);
	}

	public boolean updateEdu(EduVO eduVo) {
		return adminDao.updateEdu(eduVo);
	}

	public boolean edu_delete(String c) {
		return adminDao.edu_delete(c);
	}

	public String[] getEduFile(String c) {
		return adminDao.getEduFile(c);
	}

	public boolean eduFile_delete(String c) {
		return adminDao.eduFile_delete(c);
	}
	
	/*-------------공고--------*/
	public int findAllNotice() {
		return adminDao.findAllNotice();
	}

	public List<NoticeVO> findNoticePaging(int startIndex, int pageSize) {
		return adminDao.findNoticePaging(startIndex, pageSize);
	}

	public boolean insertNotice(NoticeVO noticevo) {
		return adminDao.insertNotice(noticevo);
	}

	public int get_notice_Id(int id) {
		return adminDao.get_notice_Id(id);
	}

	public boolean insertNoticeFile(NoticeFileVo noticeFileVo) {
		return adminDao.insertNoticeFile(noticeFileVo);
	}

	public NoticeVO viewNoticeDetail(int notice_id) {
		return adminDao.viewNoticeDetail(notice_id);
	}

	public List<NoticeFileVo> viewNoticeFileDetail(int notice_id) {
		return adminDao.viewNoticeFileDetail(notice_id);
	}

	public boolean deleteNoticeFile(int id, String name) {
		return adminDao.deleteNoticeFile(id, name);
	}

	public boolean updateNotice(NoticeVO noticeVo) {
		return adminDao.updateNotice(noticeVo);
	}

	public boolean notice_delete(String c) {
		return adminDao.notice_delete(c);
	}

	public String[] getNoticeFile(String c) {
		return adminDao.getNoticeFile(c);
	}

	public boolean noticeFile_delete(String c) {
		return adminDao.noticeFile_delete(c);
	}

	/*-------------Q&A--------*/
	public int findAllQuestions() {
		return adminDao.findAllQuestions();
	}

	public List<QuestionVO> findQuestionPaging(int startIndex, int pageSize) {
		return adminDao.findQuestionPaging(startIndex, pageSize);
	}
	
	public QuestionVO viewQuestionDetail(int question_id) {
		return adminDao.viewQuestionDetail(question_id);
	}
	
	public AnswerVO viewAnswerDetail(int question_id) {
		return adminDao.viewAnswerDetail(question_id);
	}
	
	public boolean question_delete(String c) {
		return adminDao.question_delete(c);
	}

	public boolean answer_delete_all(String c) {
		return adminDao.answer_delete_all(c);
	}

	public boolean question_delete_one(int question_id) {
		return adminDao.question_delete_one(question_id);
	}
	
	public boolean answer_delete_one(int question_id) {
		return adminDao.answer_delete_one(question_id);
	}

	public boolean insertAnswer(AnswerVO answervo) {
		return adminDao.insertAnswer(answervo);
	}

	public boolean answer_delete(int answer_id) {
		return adminDao.answer_delete(answer_id);
	}

	public boolean updateAnswer(AnswerVO answerVo) {
		return adminDao.updateAnswer(answerVo);
	}

	public boolean change_question_status(int question_id) {
		return adminDao.change_question_status(question_id);
	}
	
	public boolean revert_question_status(int question_id) {
		return adminDao.revert_question_status(question_id);
	}

	public int get_answer_questionId(int answer_id) {
		return adminDao.get_answer_questionId(answer_id);
	}
}
