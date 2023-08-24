package com.voucher.movie.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.voucher.movie.reservation.GroupVO;

@Mapper
@Repository
public interface GroupDao {
	
	@Insert("insert into reservation_group (res_name, res_phone, res_visitDate, res_visitNum, res_visitTime, res_theaterCheck, "
			+ "res_groupName, res_studentNum, res_adultNum, res_leaderNum, res_vehicleCheck, res_requestCheck, res_message, res_totalNum, create_date)"
			+ "values (#{res_name}, #{res_phone}, #{res_visitDate}, #{res_visitNum}, #{res_visitTime}, #{res_theaterCheck}, "
			+ "#{res_groupName}, #{res_studentNum}, #{res_adultNum}, #{res_leaderNum}, #{res_vehicleCheck}, #{res_requestCheck}, #{res_message}, #{res_totalNum}, sysdate())")
	boolean insertReservation (GroupVO groupVo); //예약신청 insert

}
