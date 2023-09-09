package com.voucher.movie.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.voucher.movie.admin.PopupVO;

@Mapper
@Repository
public interface WebDao {

	@Select("select count(*) from popup where status = 0")
	int get_popup_cnt();

	@Select("select * from popup where status = 0")
	List<PopupVO> getAllPopup();

	@Select("select count(*) from popup where (start_date <= #{today} and #{today} <= end_date) and status = 0")
	int today_popup_cnt(String today);

	@Select("select * from popup where (start_date <= #{today} and #{today} <= end_date) and status = 0")
	List<PopupVO> get_today_popup(String today);

}
