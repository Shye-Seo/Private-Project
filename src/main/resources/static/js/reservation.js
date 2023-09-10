$(function() {
	
	if($('.agree_check_yes').val() == 1){
		$('#agree_check').prop('checked',true);
	}
	if($('.theater_check_yes').val() == 1){
		$('#radio_1').prop('checked',true);
	}else if($('.theater_check_yes').val() == 2){
		$('#radio_2').prop('checked',true);
	}
        
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

            // ajax를 이용하여 인증번호를 요청한다.
            $.ajax({
                url: "/phoneCheck",
                type: "GET",
                data: { user_phone: $("input[name=res_phone]").val() },
                dataType: "text",
            }).done(function(data){
                certinum = data;
	            alert("인증번호가 발송되었습니다. 인증번호를 입력해주세요.");
	            $('#certifinum_submit').val("재인증");
            });
        }
    });
    
    // 인증번호 일치하면 '재인증'을 '변경'으로
    $('#certifinum').keyup(function() {
        if($('#certifinum').val() == certinum && certinum!=0) {
			$('#certifinum_check').css({'background':'#6E6E6E'});
                $('#certifinum_check').css({'color':'#FFF'});
                $('#certifinum_check').attr('value','인증완료');
                certifi_checked=="1";
                var phoneNum = $('.input_phone').val();
                $('.phone_insert').text(phoneNum);
                $('input[name=certifi_ok]').val(1);
        }
    })
    
});

	$(document).ready(function() {
		
		var studentNum = 0;
		var adultNum = 0;
		var leaderNum = 0;
		
        $('input[name=res_theaterCheck]').change(function() { //전시관
        	var res_theaterCheck = $('input[name=res_theaterCheck]:checked').val();
        	if(res_theaterCheck == 1){
				$('div[class=place_confirm]').text('부산영화체험박물관+트릭아이뮤지엄');
			}else if(res_theaterCheck == 2){
				$('div[class=place_confirm]').text('부산영화체험박물관');
			}
        });
        $('input[name=res_visitNum]').change(function() { //회차
        	var res_visitNum = $('input[name=res_visitNum]:checked').val();
        	var res_visitTime = '';
        	for(i=1; i<15; i++){
				if(i == res_visitNum){
					res_visitTime = $('li[class=res_visitTime_'+i+']').text();
				}
			}
			$('div[class=time_insert]').text(res_visitNum+'회차 / '+res_visitTime);
			$('#visitTime').val(res_visitTime);
        });
        $('.input_groupName').change(function() { //인솔자명
        	var groupName = $('.input_groupName').val();
        	$('.groupName_insert').text(' '+groupName);
        });
        $('#studentNum').keyup(function() { //학생 수
			var studentNum = $('#studentNum').val();
			studentNum = parseInt(studentNum);
        	$('.student_total .tb_num').text(studentNum);
        	gettotal();
        });
        $('#leaderNum').keyup(function() { //인솔자 수
        	var leaderNum = $('#leaderNum').val();
        	leaderNum = parseInt(leaderNum);
        	$('.leader_total .tb_num').text(leaderNum);
        	gettotal();
        });
        $('#adultNum').keyup(function() { //성인 수
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
			    $('.totalNum_insert').text('총 '+totalNum+'명 / ');
			    $('#totalNum').val(totalNum);
		}
    
    function submitReservation() {
        
			//전시관 체크 & 개인정보 동의 & 날짜 및 회차선택 -> 다음페이지
			if($('input[name=agree_check]').is(":checked")){
	        	if(!$('input[name=res_theaterCheck]').is(":checked")) {
					alert("전시관을 선택해주세요.");
					return false;
				}else if(!$('input[name=res_visitDate]').val()) {
					alert("날짜를 선택해주세요.");
					return false;
				}else if(!$('input[name=res_visitNum]').is(":checked")){
					alert("시간을 선택해주세요.");
					return false;
				}else if(!$('input[name=res_groupName]').val()){
					alert("단체명을 입력해주세요.");
					return false;
				}else if(!$('input[name=res_name]').val()){
					alert("인솔자명을 입력해주세요.");
					return false;
				}else if(!$('input[name=res_totalNum]').val()){
					alert("인원수를 입력해주세요.");
					return false;
				}else if(!$('input[name=certifi_ok]').val()){
					alert("본인인증 후 예약이 가능합니다.");
					return false;
				}else if(!$('input[name=res_vehicleCheck]').is(":checked")){
					alert("방문차량을 선택해주세요.");
					return false;
				}else if(!$('input[name=res_requestCheck]').is(":checked")){
					alert("해설자 요청여부를 선택해주세요.");
					return false;
				}else { //form 제출
				
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
	        }
			//개인정보 수집동의 X
	        else{
				alert("이용약관 및 개인정보 처리 및 이용에 동의해주세요.");
				return false;
	        }
	        
	}

function countStudent(type){
	// 결과를 표시할 element
  var student = $('input[name=res_studentNum]').val();
  
  // 더하기/빼기
  if(type === 'plus') {
    student = parseInt(student) + 1;
  }else if(type === 'minus')  {
    student = parseInt(student) - 1;
    if(student < 0){
		student = 0;
	}
  }
  $('input[name=res_studentNum]').val(student);
  $('.student_total .tb_num').text(student);
  gettotal();
}

function countAdult(type){
	// 결과를 표시할 element
  var adult = $('input[name=res_adultNum]').val();
  
  // 더하기/빼기
  if(type === 'plus') {
    adult = parseInt(adult) + 1;
  }else if(type === 'minus')  {
    adult = parseInt(adult) - 1;
    if(adult < 0){
		adult = 0;
	}
  }
  $('input[name=res_adultNum]').val(adult);
  $('.adult_total .tb_num').text(adult);
  gettotal();
}

function countLeader(type){
	// 결과를 표시할 element
  var leader = $('input[name=res_leaderNum]').val();
  
  // 더하기/빼기
  if(type === 'plus') {
    leader = parseInt(leader) + 1;
  }else if(type === 'minus')  {
    leader = parseInt(leader) - 1;
    if(leader < 0){
		leader = 0;
	}
  }
  $('input[name=res_leaderNum]').val(leader);
  $('.leader_total .tb_num').text(leader);
  gettotal();
}
