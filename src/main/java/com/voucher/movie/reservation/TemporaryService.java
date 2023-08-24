package com.voucher.movie.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.mappers.TempDao;

@Service
public class TemporaryService {

	@Autowired
	private TempDao tempDao;
	
	//예약정보 임시 insert
	public int insertTemp(TemporaryVO tempVo) throws Exception {
		tempDao.insertTemp(tempVo);
		int id = tempDao.getId(tempVo.getRandom_idx());
		return id;
	}

	//임시저장한 예약정보 get
	public TemporaryVO getTemp(int id) throws Exception {
		return tempDao.getTemp(id);
	}
}
