package com.voucher.movie.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.admin.AnswerVO;
import com.voucher.movie.admin.QuestionVO;
import com.voucher.movie.mappers.BoardDao;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	//박물관소식------------------------------
	public int findAllNews() {
		return boardDao.fintAllNews();
	}
	
	public List<NewsVO> findNewsPaging(int startIndex, int pageSize) {
		return boardDao.findNewsPaging(startIndex, pageSize);
	}
	
	public NewsVO viewNewsDetail(int news_id) {
		return boardDao.viewNewsDetail(news_id);
	}

	public List<NewsFileVo> viewNewsFileDetail(int news_id) {
		return boardDao.viewNewsFileDetail(news_id);
	}

	public int searchNewsCnt(String searchKeyword) {
		return boardDao.searchNewsCnt(searchKeyword);
	}

	public List<NewsVO> news_searchList(String searchKeyword, int startIndex, int pageSize) {
		return boardDao.news_searchList(searchKeyword, startIndex, pageSize);
	}

	//박물관이벤트------------------------------
	public int findAllEvents() {
		return boardDao.findAllEvents();
	}

	public List<EventVO> findEventPaging(int startIndex, int pageSize) {
		return boardDao.findEventPaging(startIndex, pageSize);
	}

	public int searchEventCnt(String searchKeyword) {
		return boardDao.searchEventCnt(searchKeyword);
	}

	public List<EventVO> event_searchList(String searchKeyword, int startIndex, int pageSize) {
		return boardDao.event_searchList(searchKeyword, startIndex, pageSize);
	}

	public EventVO viewEventDetail(int event_id) {
		return boardDao.viewEventDetail(event_id);
	}

	public List<EventFileVo> viewEventFileDetail(int event_id) {
		return boardDao.viewEventFileDetail(event_id);
	}

	//제휴안내------------------------------
	public int findAllPartners() {
		return boardDao.findAllPartners();
	}

	public List<PartnerVO> findPartnerPaging(int startIndex, int pageSize) {
		return boardDao.findPartnerPaging(startIndex, pageSize);
	}

	public int searchPartnerCnt(String searchKeyword) {
		return boardDao.searchPartnerCnt(searchKeyword);
	}

	public List<PartnerVO> partner_searchList(String searchKeyword, int startIndex, int pageSize) {
		return boardDao.partner_searchList(searchKeyword, startIndex, pageSize);
	}

	public PartnerVO viewPartnerDetail(int partner_id) {
		return boardDao.viewPartnerDetail(partner_id);
	}

	public List<PartnerFileVo> viewPartnerFileDetail(int partner_id) {
		return boardDao.viewPartnerFileDetail(partner_id);
	}
	
	//지난교육------------------------------
	public int findAllEdu() {
		return boardDao.findAllEdu();
	}

	public List<EduVO> findEduPaging(int startIndex, int pageSize) {
		return boardDao.findEduPaging(startIndex, pageSize);
	}

	public int searchEduCnt(String searchKeyword) {
		return boardDao.searchEduCnt(searchKeyword);
	}

	public List<EduVO> edu_searchList(String searchKeyword, int startIndex, int pageSize) {
		return boardDao.edu_searchList(searchKeyword, startIndex, pageSize);
	}

	public EduVO viewEduDetail(int edu_id) {
		return boardDao.viewEduDetail(edu_id);
	}

	public List<EduFileVo> viewEduFileDetail(int edu_id) {
		return boardDao.viewEduFileDetail(edu_id);
	}
	
	//공고------------------------------
	public int findAllNotice() {
		return boardDao.findAllNotice();
	}

	public List<NoticeVO> findNoticePaging(int startIndex, int pageSize) {
		return boardDao.findNoticePaging(startIndex, pageSize);
	}

	public int searchNoticeCnt(String searchKeyword) {
		return boardDao.searchNoticeCnt(searchKeyword);
	}

	public List<NoticeVO> notice_searchList(String searchKeyword, int startIndex, int pageSize) {
		return boardDao.notice_searchList(searchKeyword, startIndex, pageSize);
	}

	public NoticeVO viewNoticeDetail(int notice_id) {
		return boardDao.viewNoticeDetail(notice_id);
	}

	public List<NoticeFileVo> viewNoticeFileDetail(int notice_id) {
		return boardDao.viewNoticeFileDetail(notice_id);
	}
	
	/*-------------Q&A--------*/
	public int findAllQuestions() {
		return boardDao.findAllQuestions();
	}

	public List<QuestionVO> findQuestionPaging(int startIndex, int pageSize) {
		return boardDao.findQuestionPaging(startIndex, pageSize);
	}
	
	public List<QuestionVO> qna_searchList(String searchKeyword, int startIndex, int pageSize) {
		return boardDao.qna_searchList(searchKeyword, startIndex, pageSize);
	}

	public boolean insertQuestion(QuestionVO questionvo) {
		return boardDao.insertQuestion(questionvo);
	}
	
	public QuestionVO viewQuestionDetail(int question_id) {
		return boardDao.viewQuestionDetail(question_id);
	}
	
	public AnswerVO viewAnswerDetail(int question_id) {
		return boardDao.viewAnswerDetail(question_id);
	}

	public int check_qna_pw(int question_id, int question_pw) {
		return boardDao.check_qna_pw(question_id, question_pw);
	}

	public int get_question_Id() {
		return boardDao.get_question_Id();
	}

	public boolean updateQuestion(QuestionVO questionVo) {
		return boardDao.updateQuestion(questionVo);
	}

	public int get_question_status(int id) {
		return boardDao.get_question_status(id);
	}

	public int get_detail_Id(int id) {
		return boardDao.get_detail_Id(id);
	}
}
