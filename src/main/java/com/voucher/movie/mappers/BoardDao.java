package com.voucher.movie.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.voucher.movie.board.NewsFileVo;
import com.voucher.movie.board.NewsVO;

@Mapper
@Repository
public interface BoardDao {
	
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

}
