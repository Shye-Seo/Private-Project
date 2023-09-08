package com.voucher.movie.board;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.voucher.movie.config.CardPagingVO;

@Controller
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	List<NewsVO> news_list;
	
	//메인 - 박물관 소식 리스트
	@RequestMapping(value="/museum_newsList", method=RequestMethod.GET)
	public String museum_newsList(@ModelAttribute NewsVO newsVo, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
				
		// 총 게시물 수 
		int totalListCnt = boardService.findAllNews();

		// 생성인자로  총 게시물 수, 현재 페이지를 전달
		CardPagingVO pagination = new CardPagingVO(totalListCnt, page);

		// DB select start index
		int startIndex = pagination.getStartIndex();
		// 페이지 당 보여지는 게시글의 최대 개수
		int pageSize = pagination.getPageSize();

		news_list = boardService.findNewsPaging(startIndex, pageSize);
		    
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		    
		for(NewsVO news : news_list) {
			String news_date = news.getCreate_date();
		    news_date = news_date.substring(0, 4) + "." + news_date.substring(5, 7) +"." + news_date.substring(8, 10);
		    news.setCreate_date(news_date);
		}
			    
		model.addAttribute("news_list", news_list);
		model.addAttribute("nowpage", page);
				 
		return "/museum_newsList";
	}
	
	//박물관 소식 조회
	@RequestMapping(value="/museum_newsDetail", method=RequestMethod.GET)
	public ModelAndView museum_newsDetail(@RequestParam("id") int news_id) throws Exception{
		ModelAndView mav = new ModelAndView();
		NewsVO detailVo = boardService.viewNewsDetail(news_id);
				
		List<NewsFileVo> newsFileList = boardService.viewNewsFileDetail(news_id);
		String news_date = detailVo.getCreate_date();
		news_date = news_date.substring(0, 4) + "." + news_date.substring(5, 7) +"." + news_date.substring(8, 10);
		detailVo.setCreate_date(news_date);
		    	
		mav.addObject("news", detailVo);
		mav.addObject("newsFileList", newsFileList);
		mav.addObject("key", "Co91hljxoTM");
		mav.setViewName("museum_newsDetail");
		return mav;
	}

}
