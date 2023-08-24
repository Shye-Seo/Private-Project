package com.voucher.movie.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.mappers.GroupDao;
import com.voucher.movie.mappers.TempDao;

@Service
public class GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	//예약신청 insert
	public boolean insertReservation(GroupVO groupVo) throws Exception {
		return groupDao.insertReservation(groupVo);
	}

}
