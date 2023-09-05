$(function() {
	$('div[class=reservation_wrap]').css('display','block');
//	$('div[class=reservation_wrap_info]').css('display','none');
        
    $("#date_submit").click(function() {
		var res_theaterCheck = $('input[name=res_theaterCheck]:checked').val();
		var res_visitDate = $('input[name=res_visitDate]').val();
		var res_visitNum = $('input[name=res_visitNum]:checked').val();
		var res_visitTime = '';
		debugger;
		
			//전시관 체크 & 개인정보 동의 & 날짜 및 회차선택 -> 다음페이지
			if($('input[name=agree_check]').is(":checked")){
	        	if(!$('input[name=res_theaterCheck]').is(":checked")) {
					alert("전시관을 선택해주세요.");
				}else if(!$('input[name=res_visitDate]').val()) {
					alert("날짜를 선택해주세요.");
				}else if(!$('input[name=res_visitNum]').is(":checked")){
					alert("시간을 선택해주세요.");
				}else{ //다음버튼 -> div 숨기고 info div 보여주기
				
					if(res_theaterCheck == 1){
						$('div[class=place_confirm]').text('부산영화체험박물관+트릭아이뮤지엄');
					}else if(res_theaterCheck == 2){
						$('div[class=place_confirm]').text('부산영화체험박물관');
					}
					
					for(i=1; i<15; i++){
						if(i == res_visitNum){
							res_visitTime = $('li[class=res_visitTime_'+i+']').text();
						}
					}
					$('div[class=time_insert]').text(res_visitNum+'회차 / '+res_visitTime);
					$('#visitTime').val(res_visitTime);
					
					$('div[class=reservation_wrap]').css('display','none');
					$('div[class=reservation_wrap_info]').css('display','block');
				}
	        }
			//개인정보 수집동의 X
	        else{
				alert("이용약관 및 개인정보 처리 및 이용에 동의해주세요.");
	        }
		
    });
    
    var certifi_checked = "0"; // 인증번호 확인 여부
	var certifinum = "1"; // 인증번호
	
    // 인증번호 발송
    $('#certifinum_submit').click(function() {
        $("#certifinum_check").attr("disabled", false);
        $("#certifi_time").text("");
        if($('input[name=res_phone]').val() == '' || $('input[name=res_phone]').val() == null) {
            alert('휴대번호를 입력해주세요.');
            $('input[name=res_phone]').focus();
            return false;
        }
        else {
            alert("인증번호가 발송되었습니다. 인증번호를 입력해주세요.");

            // 인증버튼css
            $('#certifinum_submit').val("재인증");
            $('#certifinum_submit').css({ 'border': '0.5px solid #5B8FD2', 'background-color': '#5B8FD2',"color":"#ffffff"})

            // ajax를 이용하여 인증번호를 요청한다.
            $.ajax({
                url: "/phoneCheck",
                type: "GET",
                data: { user_phone: $("input[name=res_phone]").val() },
                dataType: "text",
            }).done(function(data){
                certifinum = data;
            });
        }
    });
    
});
    // 인증번호 비교
    function certifinum_checking(){
		var input_num = parseInt($('#certifinum').val());
        if(input_num.length >= 4){
            if(certifinum == input_num){
                certifi_checked = "1";
                $('#certifinum').attr('readonly','true');
                $("#certifinum_submit").val("인증완료");
                $("#certifinum_submit").attr("disabled", true);
                var phoneNum = $('.input_phone').val();
                $('.phone_insert').text(phoneNum);
                debugger;
            }else{
                $('#certifinum_check').css({'background':'#FF7E93'});
                $('#certifinum_check').css({'font-size':'10px'});
                $('#certifinum_check').attr('value','✕');
                certifi_checked = "0";
                debugger;
            }
        }else{
            $('#certifinum_check').css({'background':'#DDDDDD'});
            $('#certifinum_check').css({'font-size':'14px'});
            $('#certifinum_check').attr('value','✓');
            certifi_checked = "0";
        }
    }

	$(document).ready(function() {
		
		var studentNum = 0;
		var adultNum = 0;
		var leaderNum = 0;
        
        $('.input_groupName').change(function() { //인솔자명
        	var groupName = $('.input_groupName').val();
        	$('.groupName_insert').text(' '+groupName);
        });
        $('#studentNum').change(function() { //학생 수
			var studentNum = $('#studentNum').val();
			studentNum = parseInt(studentNum);
        	$('.student_total .tb_num').text(studentNum);
        	gettotal();
        });
        $('#leaderNum').change(function() { //인솔자 수
        	var leaderNum = $('#leaderNum').val();
        	leaderNum = parseInt(leaderNum);
        	$('.leader_total .tb_num').text(leaderNum);
        	gettotal();
        });
        $('#adultNum').change(function() { //성인 수
        	var adultNum = $('#adultNum').val();
        	adultNum = parseInt(adultNum);
        	$('.adult_total .tb_num').text(adultNum);
        	gettotal();
        });
        $('.input_leaderName').change(function() { //인솔자명
        	var leaderName = $('.input_leaderName').val();
        	$('.name_insert').text(leaderName);
        });
        $('input[name=res_vehicleCheck]').change(function() { //차량
        	var vehicle = $('input[name=res_vehicleCheck]:checked').val();
        	$('.vehicle_insert').text(vehicle);
        });
        $('input[name=res_requestCheck]').change(function() { //해설자 요청
        	var request = $('input[name=res_requestCheck]:checked').val();
        	if(request == '예'){
				request = 'O';
			}else if(request == '아니오'){
				request = 'X';
			}
        	$('.request_insert').text(request);
        });
        
		function gettotal() {
		        var studentNum = $('#studentNum').val();
				studentNum = parseInt(studentNum);
				var adultNum = $('#adultNum').val();
		    	adultNum = parseInt(adultNum);
		    	var leaderNum = $('#leaderNum').val();
		    	leaderNum = parseInt(leaderNum);
		    	
//				var groupName = $('.input_groupName').val();
				var totalNum = studentNum+adultNum+leaderNum;
			    $('.total_check').text(totalNum+'명');
			    $('.totalNum_insert').text('총 '+totalNum+'명 /'+'&nbsp;');
			    $('#totalNum').val(totalNum);
		}
    });
    
    function submitReservation() {
        
        var form = $('form[name="res_info"]');
        var formData = new FormData(form[0]);

        $.ajax({
			url : "/reservation_ok",
			type : 'post',
			data : formData,
			enctype : 'multipart/form-data',
	        processData : false,
	        contentType : false,
	        cache : false,
			success : function (result) {
                window.location.href= result;
            }
		});
	}

	

