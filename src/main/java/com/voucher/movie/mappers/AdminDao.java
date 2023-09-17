package com.voucher.movie.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.voucher.movie.admin.AdminVO;
import com.voucher.movie.admin.AnswerVO;
import com.voucher.movie.board.EduFileVo;
import com.voucher.movie.admin.PopupVO;
import com.voucher.movie.admin.QuestionVO;
import com.voucher.movie.board.EduVO;
import com.voucher.movie.board.EventFileVo;
import com.voucher.movie.board.EventVO;
import com.voucher.movie.board.NewsFileVo;
import com.voucher.movie.board.NewsVO;
import com.voucher.movie.board.NoticeFileVo;
import com.voucher.movie.board.NoticeVO;
import com.voucher.movie.board.PartnerFileVo;
import com.voucher.movie.board.PartnerVO;
import com.voucher.movie.reservation.ClosedVO;
import com.voucher.movie.reservation.FacilitiesVO;
import com.voucher.movie.reservation.GroupVO;
import com.voucher.movie.reservation.TimeVO;

@Mapper
@Repository
public interface AdminDao {
	
	@Select("select count(*) from admin where login_id = #{login_id} and login_pw = #{login_pw}")
    int loginCheck(AdminVO adminVo); // 관리자 로그인(세션등록)

	@Select("select * from admin where login_id = #{login_id}")
	AdminVO viewInfo(AdminVO adminVo);  // 관리자 정보 get
	
	/*--------- 페이징 및 검색 ---------*/
	@Select("select count(*) from reservation_group")
	int findAllCnt();
	
	@Select("select * from reservation_group order by res_status asc, id desc limit #{startIndex}, #{pageSize}")
	List<GroupVO> findListPaging(int startIndex, int pageSize);
	
	// ----------------- 예약 관련 -------------/
	
	@Update("update reservation_group set res_status = #{i} where id = #{id}")
	boolean setStatus(int id, int i); // 예약상태 set

	@Insert("insert into reservation_closed (closed_date, setting_status, create_date) values (#{setting_date}, 1, sysdate())")
	boolean setClosed(String setting_date); //휴관일 설정 (0:설정취소-비활성, 1:설정-활성)

	@Select("select count(*) from reservation_closed where closed_date = #{setting_date} and setting_status = 1")
	int compareStatus(String setting_date); //휴관일 check

	@Update("update reservation_closed set setting_status = 0, update_date = sysdate() where closed_date = #{setting_date}")
	boolean setUnclosed(String setting_date); //휴관일 재설정(휴관->오픈)

	@Insert("insert into reservation_time (res_date, res_num, res_time, res_limited, time_status, create_date)"
			+ "values (#{setting_date}, #{time_num}, #{setting_time}, #{limited_num}, #{time_status}, sysdate())")
	boolean setting_resTime(String setting_date, int time_num, String setting_time, int limited_num, int time_status); //회차정보변경

	@Insert("insert into reservation_time (res_date, res_num, res_time, res_limited, time_status, create_date)"
			+ "values (#{setting_date}, #{time_num}, '0', 0, #{time_status}, sysdate())")
	boolean setting_timeUnable(String setting_date, int time_num, int time_status); //회차 비활성화

	@Select("select count(*) from reservation_time where res_date = #{setting_date} and res_num = #{time_num} and time_status = 1")
	int checkTime(String setting_date, int time_num); //이미 입력된 회차정보 있는지 확인

	@Update("update reservation_time set res_time = #{setting_time}, res_limited = #{limited_num}, time_status = 1, update_date = sysdate() where res_date = #{setting_date} and res_num = #{time_num}")
	boolean update_resTime(String setting_date, int time_num, String setting_time, int limited_num, int time_status);

	@Select("select * from reservation_closed where setting_status = 1")
	List<ClosedVO> getClosed(); //휴관일 정보 get

	@Select("select * from reservation_time")
	List<TimeVO> getTime(); //회차정보 get

	@Select("select * from reservation_group where res_visitDate = #{date} and res_status = 1")
	List<GroupVO> getReservation_list(String date);//해당 날짜의 예약확정 리스트 get

	// ----------------- 박물관 소식 관련 -------------
	@Select("select count(*) from museum_news")
	int fintAllNews(); //박물관 소식 전체 count
	
	@Select("select * from museum_news order by id desc limit #{startIndex}, #{pageSize}")
	List<NewsVO> findNewsPaging(int startIndex, int pageSize);

	@Insert("insert into museum_news (news_title, news_date, news_place, news_method, news_contact, "
			+ "news_content, news_screeningCheck, news_poster, news_link1, news_link2,  create_date)"
			+ "values (#{news_title}, #{news_date}, #{news_place}, #{news_method}, #{news_contact}, "
			+ "#{news_content}, #{news_screeningCheck}, #{news_poster}, #{news_link1}, #{news_link2}, sysdate())")
	boolean insertNews(NewsVO newsvo); //박물관 소식 등록

	@Select("select id from museum_news order by id desc limit 1")
	int get_news_Id(int id);

	@Insert("insert into news_files (news_id, file_name, create_date) value (#{news_id}, #{file_name}, sysdate())")
	boolean insertNewsFile(NewsFileVo newsFileVo); //박물관 소식 파일 추가

	@Select("select * from museum_news where id = #{news_id}")
	NewsVO viewNewsDetail(int news_id); //박물관 소식 상세-관리자

	@Select("select * from news_files where news_id = #{news_id}")
	List<NewsFileVo> viewNewsFileDetail(int news_id);

	@Update("update museum_news set news_title = #{news_title}, news_date = #{news_date}, news_place = #{news_place},"
			+ " news_method = #{news_method}, news_contact = #{news_contact}, news_content = #{news_content}, news_screeningCheck = #{news_screeningCheck},"
			+ " news_poster = #{news_poster}, news_link1 = #{news_link1}, news_link2 = #{news_link2}, update_date = sysdate() where id = #{id}")
	boolean updateNews(NewsVO newsVo);

	@Delete("delete from news_files where news_id = #{news_id} and file_name = #{file_name}")
	boolean deleteFile(int news_id, String file_name);

	@Delete("delete from museum_news where id = #{c}")
	boolean news_delete(String c); //박물관 소식 삭제

	@Delete("delete from news_files where news_id = #{c}")
	boolean newsFile_delete(String c);

	@Select("select * from news_files where news_id = #{c}")
	String[] getNewsFile(String c);

	@Select("select news_poster from museum_news where id = #{id}")
	String getOldNewsPoster(int id);

	// ----------------- 팝업 설정 -------------
	@Select("select count(*) from popup")
	int findAllPopup();

	@Select("select * from popup order by id desc limit #{startIndex}, #{pageSize}")
	List<PopupVO> findPopupPaging(int startIndex, int pageSize);

	@Insert("insert into popup (popup_title, start_date, end_date, status, file_name, click_link, create_date)"
			+ " value (#{popup_title}, #{start_date}, #{end_date}, 0, #{file_name}, #{click_link}, sysdate())")
	boolean insertPopup(PopupVO popupVo);

	@Delete("delete from popup where id = #{c}")
	boolean popup_delete(String c);

	@Select("select file_name from popup where id = #{c}")
	String get_popup_FileName(String c);

	@Select("select * from popup where id = #{popup_id}")
	PopupVO viewPopupDetail(int popup_id); //팝업 상세

	@Update("update popup set popup_title = #{popup_title}, start_date = #{start_date}, end_date = #{end_date},"
			+ " status = #{status}, file_name = #{file_name}, click_link = #{click_link}, update_date = sysdate() where id = #{id}")
	boolean updatePopup(PopupVO popupVo); //팝업 수정

	@Select("select file_name from popup where id = #{id}")
	String getOldPopupImage(int id);

	@Select("select * from reservation_facilities where res_visitDate = #{date} and res_status = 1")
	List<FacilitiesVO> getFacility_list(String date); //해당 날짜의 예약확정 리스트 get

	// --------------- 대관예약 --------------
	@Select("select count(*) from reservation_facilities")
	int findAllFacilityCnt();

	@Select("select * from reservation_facilities order by res_status asc, id desc limit #{startIndex}, #{pageSize}")
	List<FacilitiesVO> findFacilityListPaging(int startIndex, int pageSize);

	@Update("update reservation_facilities set res_status = #{i} where id = #{id}")
	boolean setFacilityStatus(int id, int i);

	@Select("select * from reservation_facilities where res_status = 1 order by res_visitDate desc, res_visitNum asc")
	List<FacilitiesVO> getFacility_Reslist();

	@Insert("insert into reservation_closed (closed_date, setting_status, create_date) value (#{date_str}, 1, sysdate())")
	boolean closed_scheduler(String date_str); //월요일 휴관설정

	// --------------- 박물관 이벤트 관련 --------------
	@Select("select count(*) from museum_event")
	int findAllEvents(); //박물관 이벤트 전체 count
	
	@Select("select * from museum_event order by id desc limit #{startIndex}, #{pageSize}")
	List<EventVO> findEventPaging(int startIndex, int pageSize);

	@Insert("insert into museum_event (event_title, event_date, event_place, event_method, event_contact, "
			+ "event_content, event_screeningCheck, event_poster, event_link1, event_link2,  create_date)"
			+ "values (#{event_title}, #{event_date}, #{event_place}, #{event_method}, #{event_contact}, "
			+ "#{event_content}, #{event_screeningCheck}, #{event_poster}, #{event_link1}, #{event_link2}, sysdate())")
	boolean insertEvent(EventVO eventvo); //박물관 이벤트 등록

	@Select("select id from museum_event order by id desc limit 1")
	int get_event_Id(int id);

	@Insert("insert into event_files (event_id, file_name, create_date) value (#{event_id}, #{file_name}, sysdate())")
	boolean insertEventFile(EventFileVo eventFileVo); //박물관 이벤트 파일 추가

	@Select("select * from museum_event where id = #{event_id}")
	EventVO viewEventDetail(int event_id); //박물관 이벤트 상세-관리자

	@Select("select * from event_files where event_id = #{event_id}")
	List<EventFileVo> viewEventFileDetail(int event_id);

	@Update("update museum_event set event_title = #{event_title}, event_date = #{event_date}, event_place = #{event_place},"
			+ " event_method = #{event_method}, event_contact = #{event_contact}, event_content = #{event_content}, event_screeningCheck = #{event_screeningCheck},"
			+ " event_poster = #{event_poster}, event_link1 = #{event_link1}, event_link2 = #{event_link2}, update_date = sysdate() where id = #{id}")
	boolean updateEvent(EventVO eventVo);

	@Delete("delete from event_files where event_id = #{event_id} and file_name = #{file_name}")
	boolean deleteEventFile(int event_id, String file_name);

	@Delete("delete from museum_event where id = #{c}")
	boolean event_delete(String c); //박물관 이벤트 삭제

	@Delete("delete from event_files where event_id = #{c}")
	boolean eventFile_delete(String c);

	@Select("select * from event_files where event_id = #{c}")
	String[] getEventFile(String c);

	@Select("select event_poster from museum_event where id = #{id}")
	String getOldEventPoster(int id);
	
	// --------------- 제휴안내 관련 --------------
	@Select("select count(*) from museum_partner")
	int findAllPartners(); //제휴안내 전체 count
	
	@Select("select * from museum_partner order by id desc limit #{startIndex}, #{pageSize}")
	List<PartnerVO> findPartnerPaging(int startIndex, int pageSize);

	@Insert("insert into museum_partner (partner_title, partner_date, partner_sale, partner_content, partner_poster, create_date)"
			+ "values (#{partner_title}, #{partner_date}, #{partner_sale},  #{partner_content}, #{partner_poster}, sysdate())")
	boolean insertPartner(PartnerVO partnervo); //제휴안내 등록

	@Select("select id from museum_partner order by id desc limit 1")
	int get_partner_Id(int id);

	@Insert("insert into partner_files (partner_id, file_name, create_date) value (#{partner_id}, #{file_name}, sysdate())")
	boolean insertPartnerFile(PartnerFileVo partnerFileVo); //제휴안내 파일 추가

	@Select("select * from museum_partner where id = #{partner_id}")
	PartnerVO viewPartnerDetail(int partner_id); //제휴안내 상세-관리자

	@Select("select * from partner_files where partner_id = #{partner_id}")
	List<PartnerFileVo> viewPartnerFileDetail(int partner_id);

	@Update("update museum_partner set partner_title = #{partner_title}, partner_date = #{partner_date},"
			+ " partner_sale = #{partner_sale}, partner_content = #{partner_content},"
			+ " partner_poster = #{partner_poster}, update_date = sysdate() where id = #{id}")
	boolean updatePartner(PartnerVO partnerVo);

	@Delete("delete from partner_files where partner_id = #{partner_id} and file_name = #{file_name}")
	boolean deletePartnerFile(int partner_id, String file_name);

	@Delete("delete from museum_partner where id = #{c}")
	boolean partner_delete(String c); //제휴안내 삭제

	@Delete("delete from partner_files where partner_id = #{c}")
	boolean partnerFile_delete(String c);

	@Select("select * from partner_files where partner_id = #{c}")
	String[] getPartnerFile(String c);

	@Select("select partner_poster from museum_partner where id = #{id}")
	String getOldPartnerPoster(int id);
	
	// --------------- 지난교육 관련 --------------
	@Select("select count(*) from museum_edu")
	int findAllEdu(); //지난교육 전체 count
	
	@Select("select * from museum_edu order by id desc limit #{startIndex}, #{pageSize}")
	List<EduVO> findEduPaging(int startIndex, int pageSize);

	@Insert("insert into museum_edu (edu_title, edu_date, edu_place, edu_target,"
			+ " edu_explain, edu_price, edu_deadline, edu_method, edu_content,"
			+ " edu_poster, edu_link1, edu_link2, create_date)"
			+ "values (#{edu_title}, #{edu_date}, #{edu_place},  #{edu_target},"
			+ " #{edu_explain}, #{edu_price}, #{edu_deadline}, #{edu_method}, #{edu_content},"
			+ "#{edu_poster}, #{edu_link1}, #{edu_link2}, sysdate())")
	boolean insertEdu(EduVO eduvo); //지난교육 등록

	@Select("select id from museum_edu order by id desc limit 1")
	int get_edu_Id(int id);

	@Insert("insert into edu_files (edu_id, file_name, create_date) value (#{edu_id}, #{file_name}, sysdate())")
	boolean insertEduFile(EduFileVo eduFileVo); //지난교육 파일 추가

	@Select("select * from museum_edu where id = #{edu_id}")
	EduVO viewEduDetail(int edu_id); //지난교육 상세-관리자

	@Select("select * from edu_files where edu_id = #{edu_id}")
	List<EduFileVo> viewEduFileDetail(int edu_id);

	@Update("update museum_edu set edu_title = #{edu_title}, edu_date = #{edu_date}, edu_place = #{edu_place}, edu_target = #{edu_target},"
			+ " edu_explain = #{edu_explain}, edu_price = #{edu_price}, edu_deadline = #{edu_deadline}, edu_method = #{edu_method},"
			+ " edu_content = #{edu_content}, edu_poster = #{edu_poster}, edu_link1 = #{edu_link1}, edu_link2 = #{edu_link2},"
			+ " update_date = sysdate() where id = #{id}")
	boolean updateEdu(EduVO eduVo);

	@Delete("delete from edu_files where edu_id = #{edu_id} and file_name = #{file_name}")
	boolean deleteEduFile(int edu_id, String file_name);

	@Delete("delete from museum_edu where id = #{c}")
	boolean edu_delete(String c); //제휴안내 삭제

	@Delete("delete from edu_files where edu_id = #{c}")
	boolean eduFile_delete(String c);

	@Select("select * from edu_files where edu_id = #{c}")
	String[] getEduFile(String c);

	@Select("select edu_poster from museum_edu where id = #{id}")
	String getOldEduPoster(int id);
	
	// --------------- 공고 관련 --------------
	@Select("select count(*) from museum_notice")
	int findAllNotice(); //공고 전체 count
	
	@Select("select * from museum_notice order by id desc limit #{startIndex}, #{pageSize}")
	List<NoticeVO> findNoticePaging(int startIndex, int pageSize);

	@Insert("insert into museum_notice (notice_title, notice_date, notice_content, create_date)"
			+ "values (#{notice_title}, #{notice_date}, #{notice_content}, sysdate())")
	boolean insertNotice(NoticeVO noticevo); //공고 등록

	@Select("select id from museum_notice order by id desc limit 1")
	int get_notice_Id(int id);

	@Insert("insert into notice_files (notice_id, file_name, create_date) value (#{notice_id}, #{file_name}, sysdate())")
	boolean insertNoticeFile(NoticeFileVo noticeFileVo); //공고 파일 추가

	@Select("select * from museum_notice where id = #{notice_id}")
	NoticeVO viewNoticeDetail(int notice_id); //공고 상세-관리자

	@Select("select * from notice_files where notice_id = #{notice_id}")
	List<NoticeFileVo> viewNoticeFileDetail(int notice_id);

	@Update("update museum_notice set notice_title = #{notice_title}, notice_date = #{notice_date},"
			+ " notice_content = #{notice_content}, update_date = sysdate() where id = #{id}")
	boolean updateNotice(NoticeVO noticeVo);

	@Delete("delete from notice_files where notice_id = #{notice_id} and file_name = #{file_name}")
	boolean deleteNoticeFile(int notice_id, String file_name);

	@Delete("delete from museum_notice where id = #{c}")
	boolean notice_delete(String c); //공고 삭제

	@Delete("delete from notice_files where notice_id = #{c}")
	boolean noticeFile_delete(String c);

	@Select("select * from notice_files where notice_id = #{c}")
	String[] getNoticeFile(String c);

	// --------------- Q&A 관련 --------------
	@Select("select count(*) from qna_question")
	int findAllQuestions(); //Q&A 전체 count
	
	@Select("select * from qna_question order by id desc limit #{startIndex}, #{pageSize}")
	List<QuestionVO> findQuestionPaging(int startIndex, int pageSize);
	
	@Select("select * from qna_question where id = #{question_id}")
	QuestionVO viewQuestionDetail(int question_id); //문의글 상세-문의글 가져오기
	
	@Select("select * from qna_answer where question_id = #{question_id}")
	AnswerVO viewAnswerDetail(int question_id); //문의글 상세-문의글에 대한 답변 가져오기

	@Delete("delete from qna_question where id = #{c}")
	boolean question_delete(String c); //문의글 삭제
	
	@Delete("delete from qna_answer where question_id = #{c}")
	boolean answer_delete_all(String c); //문의글 삭제 시, 답변 같이 삭제

	@Delete("delete from qna_question where id = #{question_id}")
	boolean question_delete_one(int question_id);
	
	@Delete("delete from qna_answer where question_id = #{question_id}")
	boolean answer_delete_one(int question_id); //문의글 삭제 시, 답변 같이 삭제

	@Insert("insert into qna_answer (question_id, answer_title, answer_content, create_date) "
			+ "values (#{question_id}, #{answer_title}, #{answer_content}, sysdate())")
	boolean insertAnswer(AnswerVO answervo); //문의글에 답변 등록

	@Delete("delete from qna_answer where id = #{answer_id}")
	boolean answer_delete(int answer_id);

	@Update("update qna_answer set answer_title = #{answer_title}, answer_content = #{answer_content},"
			+ " update_date = sysdate() where question_id = #{question_id}")
	boolean updateAnswer(AnswerVO answerVo);
}
