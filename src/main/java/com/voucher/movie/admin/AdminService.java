package com.voucher.movie.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.mappers.AdminDao;
import com.voucher.movie.mappers.GroupDao;
import com.voucher.movie.reservation.ClosedVO;
import com.voucher.movie.reservation.GroupVO;
import com.voucher.movie.reservation.TimeVO;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private GroupDao groupDao;
	
	// 관리자 로그인
    public int loginCheck(AdminVO adminVo, HttpSession session) {
        int result = adminDao.loginCheck(adminVo);
        System.out.println(result);

        if(result==1) { // true일 경우 세션에 등록
            AdminVO adminVo1 = viewInfo(adminVo);
            
            int id = adminVo1.getId();
            
            session.setAttribute("user_id", adminVo1.getLogin_id());
            session.setAttribute("user_name", "관리자");
            session.setAttribute("id",id);
            session.setAttribute("authority", adminVo1.getAuthority());

        }
        return result;
    }
    
    // 로그인할때 관리자정보 가져올때
    public AdminVO viewInfo(AdminVO adminVo) {
        return adminDao.viewInfo(adminVo);
    }
    
    /* ---페이징처리--- */
	public int findAllCnt() {
		return adminDao.findAllCnt();
	}
	
	public List<GroupVO> findListPaging(int startIndex, int pageSize) {
		return adminDao.findListPaging(startIndex, pageSize);
	}
	
	// 예약상태 set
	public boolean setStatus(int id, int i) throws Exception {
		return adminDao.setStatus(id, i);
	}

	// 휴관일 설정
	public boolean setClosed(String setting_date) {
		return adminDao.setClosed(setting_date);
		
	}
	
	public boolean setUnclosed(String setting_date) {
		return adminDao.setUnclosed(setting_date);
		
	}

	// 휴관일 상태 check
	public int compareStatus(String setting_date) {
		return adminDao.compareStatus(setting_date);
	}

	//회차설정-정보변경
	public boolean setting_resTime(String setting_date, int time_num, String setting_time, int limited_num, int time_status) {
		return adminDao.setting_resTime(setting_date, time_num, setting_time, limited_num, time_status);
	}
	
	//회차설정-비활성화
	public boolean setting_timeUnable(String setting_date, int time_num, int time_status) {
		return adminDao.setting_timeUnable(setting_date, time_num, time_status);
	}

	public int checkTime(String setting_date, int time_num) {
		return adminDao.checkTime(setting_date, time_num);
	}

	public boolean update_resTime(String setting_date, int time_num, String setting_time, int limited_num, int time_status) {
		return adminDao.update_resTime(setting_date, time_num, setting_time, limited_num, time_status);
	}

	public List<ClosedVO> getClosed() {
		return adminDao.getClosed();
	}

	public List<TimeVO> getTime() {
		return adminDao.getTime();
	}

	public List<GroupVO> getReservation_list(String date) {
		return adminDao.getReservation_list(date);
	}


}
