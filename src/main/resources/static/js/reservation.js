$(function() {
	// 개인정보동의 안하면 확인 버튼 비활성화
    $("#agree_btn").click(function() {
		var checkVal = $('input[name=res_theaterCheck]:checked').val();
		
		//전시관 체크 & 개인정보 동의 -> 다음페이지
        if($('input[name=res_theaterCheck]').is(":checked")) {
			if($('input[name=agree_check]').is(":checked")){
				if(checkVal == 1){
					location.href="/reservation_group_date_1";
				}else if(checkVal == 2){
					location.href="/reservation_group_date_2";
				}else{
					alert("페이지 이동 오류!!");
				}
			}else{ //전시관 체크 & 개인정보 미동의
	            alert("이용약관 및 개인정보 처리 및 이용에 동의해주세요.");
			}
		}  //ajax
		//전시관 체크 X
        else{
			debugger;
			alert("전시관을 선택해주세요.");
        }
    });
});

$(function() {
	// 개인정보동의 안하면 확인 버튼 비활성화
    $("#date_submit").click(function() {
		
		var res_theaterCheck = $('input[name=res_theaterCheck]').val();
		var res_visitDate = $('input[name=res_visitDate]').val();
		var res_visitNum = $('input[name=res_visitNum]:checked').val();
		
        if(!$('input[name=res_visitDate]').val()) {
			alert("날짜를 선택해주세요.");
		}else if(!$('input[name=res_visitNum]').is(":checked")){
			alert("시간을 선택해주세요.");
		}
        else{
			$.ajax({
					url : "/reservation_group_date_register",
					type : 'post',
					data : {
							res_theaterCheck:res_theaterCheck,
							res_visitDate:res_visitDate,
							res_visitNum:res_visitNum
							},
					success : function (result) {
                        window.location.href= result;
                    }
			});
        }
    });
});

