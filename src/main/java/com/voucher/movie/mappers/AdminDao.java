package com.voucher.movie.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.voucher.movie.admin.AdminVO;
import com.voucher.movie.board.NewsFileVo;
import com.voucher.movie.board.NewsVO;
import com.voucher.movie.reservation.ClosedVO;
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

	@Select("select count(*) from museum_news")
	int fintAllNews(); //박물관 소식 전체 count
	
	@Select("select * from museum_news order by id desc limit #{startIndex}, #{pageSize}")
	List<NewsVO> findNewsPaging(int startIndex, int pageSize);

	@Insert("insert into museum_news (news_title, news_content, news_link, create_date)"
			+ "values (#{news_title}, #{news_content}, #{news_link}, sysdate())")
	boolean insertNews(NewsVO newsvo); //박물관 소식 등록

	@Select("select id from museum_news order by id desc limit 1")
	int get_news_Id(int id);

	@Insert("insert into news_files (news_id, file_name, create_date) value (#{news_id}, #{file_name}, sysdate())")
	boolean insertNewsFile(NewsFileVo newsFileVo); //박물관 소식 파일 추가

	@Select("select * from museum_news where id = #{news_id}")
	NewsVO viewNewsDetail(int news_id); //박물관 소식 상세-관리자

	@Select("select * from news_files where news_id = #{news_id}")
	List<NewsFileVo> viewNewsFileDetail(int news_id);

	@Update("update museum_news set news_title = #{news_title}, news_content = #{news_content}, news_link = #{news_link}, update_date = sysdate() where id = #{id}")
	boolean updateNews(NewsVO newsVo);

	@Delete("delete from news_files where news_id = #{news_id} and file_name = #{file_name}")
	boolean deleteFile(int news_id, String file_name);


}
