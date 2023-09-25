package com.voucher.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.admin.PopupVO;
import com.voucher.movie.board.EventVO;
import com.voucher.movie.board.NewsVO;
import com.voucher.movie.board.PartnerVO;
import com.voucher.movie.mappers.AdminDao;
import com.voucher.movie.mappers.WebDao;

@Service
public class WebService {
	
	@Autowired
	private WebDao webDao;

	public int get_popup_cnt() { //팝업 count
		return webDao.get_popup_cnt();
	}

	public List<PopupVO> getAllPopup() {
		return webDao.getAllPopup();
	}

	public int today_popup_cnt(String today) {
		return webDao.today_popup_cnt(today);
	}

	public List<PopupVO> get_today_popup(String today) {
		return webDao.get_today_popup(today);
	}

	public List<NewsVO> getAllNews() {
		return webDao.getAllNews();
	}

	public List<EventVO> getAllEvents() {
		return webDao.getAllEvents();
	}

	public List<PartnerVO> getAllPartners() {
		return webDao.getAllPartners();
	}

}
