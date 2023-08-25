package com.voucher.movie.reservation;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.movie.mappers.GroupDao;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	//예약신청 insert
	public boolean insertReservation(GroupVO groupVo) throws Exception {
		return groupDao.insertReservation(groupVo);
	}

	// coolsms에 정보를 보내 회원가입시 인증번호를 보낸 후, 인증번호를 return 한다.
    public void sendSms(String user_phone, int randomNumber) throws CoolsmsException {
        String api_key = "NCS9HI923SUSM5VF";
        String api_secret = "XLQEOJRXNGAIUHIFRGX5VUTCHEJV7D8N";

        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", user_phone);
        params.put("from", "010-9878-0502"); // 발신전화번호. 테스트시에는 발신, 수신 둘다 본인 번호로 하면됨
        params.put("type", "SMS");
        params.put("text", "[영화체험박물관] 본인인증번호는" + "["+randomNumber+"]" + "입니다."); // 문자 내용 입력
        
        System.out.println(randomNumber);

        // send() 를 통해 전송
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        }catch(CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
