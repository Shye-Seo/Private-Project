package com.voucher.movie.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.voucher.movie.admin.AdminVO;
import com.voucher.movie.reservation.GroupVO;

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
}
