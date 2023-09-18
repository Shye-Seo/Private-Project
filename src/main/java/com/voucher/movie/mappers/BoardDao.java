package com.voucher.movie.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.voucher.movie.admin.AnswerVO;
import com.voucher.movie.admin.QuestionVO;
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

@Mapper
@Repository
public interface BoardDao {
	
	// --------------- 박물관 소식 관련 --------------
	@Select("select count(*) from museum_news")
	int fintAllNews(); //박물관 소식 전체 count
	
	@Select("select * from museum_news order by id desc limit #{startIndex}, #{pageSize}")
	List<NewsVO> findNewsPaging(int startIndex, int pageSize);
	
	@Select("select * from museum_news where id = #{news_id}")
	NewsVO viewNewsDetail(int news_id); //박물관 소식 상세

	@Select("select * from news_files where news_id = #{news_id}")
	List<NewsFileVo> viewNewsFileDetail(int news_id);

	@Select("select count(*) from museum_news where news_title like concat('%','${searchKeyword}','%')")
	int searchNewsCnt(String searchKeyword); //박물관 소식 검색

	@Select("select * from museum_news where news_title like concat('%','${searchKeyword}','%') order by id desc limit #{startIndex}, #{pageSize}")
	List<NewsVO> news_searchList(String searchKeyword, int startIndex, int pageSize);

	// --------------- 박물관 이벤트 관련 --------------
	@Select("select count(*) from museum_event")
	int findAllEvents(); //박물관 이벤트 전체 count
	
	@Select("select * from museum_event order by id desc limit #{startIndex}, #{pageSize}")
	List<EventVO> findEventPaging(int startIndex, int pageSize);
	
	@Select("select count(*) from museum_event where event_title like concat('%','${searchKeyword}','%')")
	int searchEventCnt(String searchKeyword); //박물관 소식 검색

	@Select("select * from museum_event where event_title like concat('%','${searchKeyword}','%') order by id desc limit #{startIndex}, #{pageSize}")
	List<EventVO> event_searchList(String searchKeyword, int startIndex, int pageSize);
	
	@Select("select * from museum_event where id = #{event_id}")
	EventVO viewEventDetail(int event_id); //박물관 이벤트 상세-관리자

	@Select("select * from event_files where event_id = #{event_id}")
	List<EventFileVo> viewEventFileDetail(int event_id);
	
	// --------------- 제휴안내 관련 --------------
	@Select("select count(*) from museum_partner")
	int findAllPartners(); //제휴안내 전체 count
	
	@Select("select * from museum_partner order by id desc limit #{startIndex}, #{pageSize}")
	List<PartnerVO> findPartnerPaging(int startIndex, int pageSize);
	
	@Select("select count(*) from museum_partner where partner_title like concat('%','${searchKeyword}','%')")
	int searchPartnerCnt(String searchKeyword); //제휴안내 검색

	@Select("select * from museum_partner where partner_title like concat('%','${searchKeyword}','%') order by id desc limit #{startIndex}, #{pageSize}")
	List<PartnerVO> partner_searchList(String searchKeyword, int startIndex, int pageSize);
	
	@Select("select * from museum_partner where id = #{partner_id}")
	PartnerVO viewPartnerDetail(int partner_id); //제휴안내 상세-관리자

	@Select("select * from partner_files where partner_id = #{partner_id}")
	List<PartnerFileVo> viewPartnerFileDetail(int partner_id);
	
	// --------------- 지난교육 관련 --------------
	@Select("select count(*) from museum_edu")
	int findAllEdu(); //지난교육 전체 count
	
	@Select("select * from museum_edu order by id desc limit #{startIndex}, #{pageSize}")
	List<EduVO> findEduPaging(int startIndex, int pageSize);
	
	@Select("select count(*) from museum_edu where edu_title like concat('%','${searchKeyword}','%')")
	int searchEduCnt(String searchKeyword); //지난교육 검색

	@Select("select * from museum_edu where edu_title like concat('%','${searchKeyword}','%') order by id desc limit #{startIndex}, #{pageSize}")
	List<EduVO> edu_searchList(String searchKeyword, int startIndex, int pageSize);
	
	@Select("select * from museum_edu where id = #{edu_id}")
	EduVO viewEduDetail(int edu_id); //지난교육 상세-관리자

	@Select("select * from edu_files where edu_id = #{edu_id}")
	List<EduFileVo> viewEduFileDetail(int edu_id);
	
	// --------------- 공고 관련 --------------
	@Select("select count(*) from museum_notice")
	int findAllNotice(); //공고 전체 count
	
	@Select("select * from museum_notice order by id desc limit #{startIndex}, #{pageSize}")
	List<NoticeVO> findNoticePaging(int startIndex, int pageSize);
	
	@Select("select count(*) from museum_notice where notice_title like concat('%','${searchKeyword}','%')")
	int searchNoticeCnt(String searchKeyword); //공고 검색

	@Select("select * from museum_notice where notice_title like concat('%','${searchKeyword}','%') order by id desc limit #{startIndex}, #{pageSize}")
	List<NoticeVO> notice_searchList(String searchKeyword, int startIndex, int pageSize);
	
	@Select("select * from museum_notice where id = #{notice_id}")
	NoticeVO viewNoticeDetail(int notice_id); //공고 상세-관리자

	@Select("select * from notice_files where notice_id = #{notice_id}")
	List<NoticeFileVo> viewNoticeFileDetail(int notice_id);
	
	// --------------- Q&A 관련 --------------
	@Select("select count(*) from qna_question")
	int findAllQuestions(); //Q&A 전체 count
	
	@Select("select * from qna_question order by id desc limit #{startIndex}, #{pageSize}")
	List<QuestionVO> findQuestionPaging(int startIndex, int pageSize);
	
	@Select("select * from qna_question where question_title like concat('%','${searchKeyword}','%') order by id desc limit #{startIndex}, #{pageSize}")
	List<QuestionVO> qna_searchList(String searchKeyword, int startIndex, int pageSize);

	@Insert("insert into qna_question (question_writer, question_title, question_phone, question_content, question_pw, question_status, create_date)"
			+ " values (#{question_writer}, #{question_title}, #{question_phone}, #{question_content}, #{question_pw}, 0, sysdate())")
	boolean insertQuestion(QuestionVO questionvo);
	
	@Select("select * from qna_question where id = #{question_id}")
	QuestionVO viewQuestionDetail(int question_id); //문의글 상세-문의글 가져오기
	
	@Select("select * from qna_answer where question_id = #{question_id}")
	AnswerVO viewAnswerDetail(int question_id); //문의글 상세-문의글에 대한 답변 가져오기

	@Select("select count(*) from qna_question where id = #{question_id} and question_pw = #{question_pw}")
	int check_qna_pw(int question_id, int question_pw); //문의글 비밀번호 일치여부 check

	@Select("select id from qna_question order by id desc limit 1")
	int get_question_Id();

	@Update("update qna_question set question_writer = #{question_writer}, question_title = #{question_title}, question_phone = #{question_phone}, "
			+ "question_content = #{question_content}, question_pw = #{question_pw}, question_status = #{question_status}, update_date = sysdate()")
	boolean updateQuestion(QuestionVO questionVo);

	@Select("select question_status from qna_question where id = #{id}")
	int get_question_status(int id);

	@Select("select id from qna_question where id = #{id}")
	int get_detail_Id(int id);
}
