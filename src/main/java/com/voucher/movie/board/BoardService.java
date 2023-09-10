package com.voucher.movie.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.mappers.BoardDao;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public int findAllNews() {
		return boardDao.fintAllNews();
	}
	
	public List<NewsVO> findNewsPaging(int startIndex, int pageSize) {
		return boardDao.findNewsPaging(startIndex, pageSize);
	}
	
	//박물관 소식 상세
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

}
