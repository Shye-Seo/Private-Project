package com.voucher.movie.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.voucher.movie.reservation.TemporaryVO;

@Mapper
@Repository
public interface TempDao {

	@Insert("insert into reservation_temp (random_idx, res_theaterCheck, res_visitDate, res_visitNum, res_visitTime, create_date)"
			+ "values (#{random_idx}, #{res_theaterCheck}, #{res_visitDate}, #{res_visitNum}, #{res_visitTime}, sysdate())")
	boolean insertTemp (TemporaryVO tempVo); //임시저장
	
	@Select("select id from reservation_temp where random_idx = #{random_idx}")
	int getId(int random_idx); //임시저장 id

	@Select("select * from reservation_temp where id = #{id}")
	TemporaryVO getTemp(int id); //임시저장한 값 get
}
