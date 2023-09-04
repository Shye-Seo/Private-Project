$(function() {
        
    $("#date_submit").click(function() {
		var res_theaterCheck = $('input[name=res_theaterCheck]').val();
		var res_visitDate = $('input[name=res_visitDate]').val();
		var res_visitNum = $('input[name=res_visitNum]:checked').val();
		
			//전시관 체크 & 개인정보 동의 & 날짜 및 회차선택 -> 다음페이지
			if($('input[name=agree_check]').is(":checked")){
	        	if(!$('input[name=res_theaterCheck]').is(":checked")) {
					alert("전시관을 선택해주세요.");
				}else if(!$('input[name=res_visitDate]').val()) {
					alert("날짜를 선택해주세요.");
				}else if(!$('input[name=res_visitNum]').is(":checked")){
					alert("시간을 선택해주세요.");
				}else{ //다음버튼 -> div 숨기고 info div 보여주기
				
					$('div[class=reservation_wrap]').hide();
					$('div[class=reservation_wrap_info]').show();
				}
	        }
			//개인정보 수집동의 X
	        else{
				alert("이용약관 및 개인정보 처리 및 이용에 동의해주세요.");
	        }
		
    });
});

	

