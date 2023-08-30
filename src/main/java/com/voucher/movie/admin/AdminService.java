package com.voucher.movie.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.mappers.AdminDao;
import com.voucher.movie.mappers.GroupDao;
import com.voucher.movie.reservation.GroupVO;

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

}
