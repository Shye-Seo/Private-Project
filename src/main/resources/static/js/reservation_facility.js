$(function() {
	
			$("#input_file").bind('change', function() {
                selectFile(this.files);

                //파일 1개 이상일 시 버튼 비활성화
                if($('#file_names').children().length >= 1){
                    $('#input_file').attr("disabled","disabled");
                    $('.file_area label').css({'background-color':'#eee', 'cursor':'unset'});
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
		
        $('input[name=res_placeCheck]').change(function() { //전시관
        	var res_placeCheck = $('input[name=res_placeCheck]:checked').val();
        	if(res_placeCheck == 1){
				$('div[class=place_confirm]').text('강의실(30석)');
			}else if(res_placeCheck == 2){
				$('div[class=place_confirm]').text('다목적영상홀(210석)');
			}
        });
        $('input[name=res_visitNum]').change(function() { //회차
        	var res_visitNum = $('input[name=res_visitNum]:checked').val();
        	var res_visitTime = '';
        	for(i=1; i<4; i++){
				if(i == res_visitNum){
					res_visitTime = $('li[class=res_visitTime_'+i+']').text();
				}
			}
			$('div[class=time_insert]').text(res_visitNum+'회차 / '+res_visitTime);
        });
        $('.input_leaderName').change(function() { //신청자명
        	var leaderName = $('.input_leaderName').val();
        	$('.name_insert').text(leaderName);
        });
        $('input[name=facility_rentalCheck]').change(function() { //기자재 및 물품대여(, 구분)
        	var rental = [];
        	$('input[name=facility_rentalCheck]:checked').each(function(){
				var chk = $(this).val();
				rental.push(chk);
			});
        	$('.vehicle_insert').text(rental);
        	$('input[name=res_rentalCheck]').val(rental);
        });
        $('input[name=agree_facility]').change(function() { //냉난방 동의
        	var request = $('input[name=agree_facility]:checked').val();
        	if(request == '예'){
				request = 'O';
			}
        	$('.request_insert').text(request);
        });
        
    });
    
    function submitReservation() {
        
			//전시관 체크 & 개인정보 동의 & 날짜 및 회차선택 -> 다음페이지
			if($('input[name=agree_check]').is(":checked")){
	        	if(!$('input[name=res_placeCheck]').is(":checked")) {
					alert("대관시설을 선택해주세요.");
					return false;
				}else if(!$('input[name=res_visitDate]').val()) {
					alert("날짜를 선택해주세요.");
					return false;
				}else if(!$('input[name=res_visitNum]').is(":checked")){
					alert("시간을 선택해주세요.");
					return false;
				}else if(!$('input[name=res_name]').val()){
					alert("신청자명을 입력해주세요.");
					return false;
				}else if(!$('input[name=res_email]').val()){
					alert("이메일을 입력해주세요.");
					return false;
				}else if(!$('input[name=certifi_ok]').val()){
					alert("본인인증 후 예약이 가능합니다.");
					return false;
				}else if($('#file_names').children().length < 1){
					alert('대관신청서를 첨부해주세요.');
                    return false;
				}else if($('#file_names').children().length > 1){
					alert('대관신청서는 하나만 첨부해주세요.');
                    return false;
				}else if(!$('input[name=agree_facility]').is(":checked")){
					alert("냉/난방시설 이용안내에 동의해주세요.");
					return false;
				}else { //form 제출
				
			        var form = $('form[name="res_info"]');
			        var formData = new FormData(form[0]);
			        for (var i = 0; i < fileList.length; i++) {
	                    formData.append('application_file', fileList[i]);
	                    console.log(formData.get('application_file')) 
	                    console.log(fileList[i]) 
                	}
			
			        $.ajax({
						url : "/reservation_facilities_ok",
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
	
	// 파일 리스트 번호
            var fileIndex = 0;
            // 등록할 전체 파일 사이즈
            var totalFileSize = 0;
            // 파일 리스트
            var fileList = new Array();
            // 파일 사이즈 리스트
            var fileSizeList = new Array();
            // 등록 가능한 파일 사이즈 MB
            //    var uploadSize = 5;
            // 등록 가능한 총 파일 사이즈 MB
            //    var maxUploadSize = 500;
            
            var maxFileNum = 1;
            // 파일 최대갯수

            //행사수정 시 지울 파일 목록
            let deleteFileNameList = new Array();
            var files = new Array();
	
	// 파일 선택시
        function selectFile(fileObject) {
           
            files = $('#input_file')[0].files;
            // 다중파일 등록
            if (files != null) {
           
           
           for (var i = 0; i < files.length; i++) {
               // 파일 이름
               var fileName = files[i].name;
               var fileNameArr = fileName.split("\.");
               // 확장자
               var ext = fileNameArr[fileNameArr.length - 1];
               
               var special_pattern = /[\{\}\[\]|<>\"]/gi;

               if(special_pattern.test($("input[name='file']").val())){
                   alert('파일명에 특수문자를 제거해주세요.');
                   return false;
               }
               
               var fileSize = files[i].size; // 파일 사이즈(단위 :byte)
               console.log("fileSize="+fileSize);
               if (fileSize <= 0) {
                   console.log("0kb file return");
                   return;
               }
               
               var fileSizeKb = fileSize / 1024; // 파일 사이즈(단위 :kb)
               var fileSizeMb = fileSizeKb / 1024;    // 파일 사이즈(단위 :Mb)
               
               var fileSizeStr = "";
               if ((1024*1024) <= fileSize) {    // 파일 용량이 1메가 이상인 경우 
                   console.log("fileSizeMb="+fileSizeMb.toFixed(2));
                   fileSizeStr = fileSizeMb.toFixed(2) + " Mb";
               } else if ((1024) <= fileSize) {
                   console.log("fileSizeKb="+parseInt(fileSizeKb));
                   fileSizeStr = parseInt(fileSizeKb) + " kb";
               } else {
                   console.log("fileSize="+parseInt(fileSize));
                   fileSizeStr = parseInt(fileSize) + " byte";
               }
				
                   // 전체 파일 사이즈
                   totalFileSize += fileSizeMb;
                   // 파일 배열에 넣기
                   fileList[fileIndex] = files[i];
                   // 파일 사이즈 배열에 넣기
                   fileSizeList[fileIndex] = fileSizeMb;
                   // 업로드 파일 목록 생성
                   addFileList(fileIndex, fileName, fileSizeStr);
                   // 파일 번호 증가
                   fileIndex++;
           }
       } else {
           alert("ERROR");
       }
   }
	
	// 업로드 파일 목록 생성
   function addFileList(fIndex, fileName, fileSizeStr) {
	   var html = "";
       html += "<div id='fileTr_" + fIndex + "'>";
       html += "    <div class='file_con'>";
       html += "		<div class='filename'>"+fileName + "</div>" 
               + "<img src='/imgs/close_btn_black.svg' href='#' onclick='deleteFile("+fIndex+"); return false;'/>"
       html += "    </div>"
       html += "</div>"
       
       $('#file_names').empty();
       $('#file_names').append(html);
       $('input[name=res_file]').val(fileName);
   }
   
   // 업로드 파일 삭제
   function deleteFile(fIndex) {
       console.log("deleteFile.fIndex=" + fIndex);
       // 전체 파일 사이즈 수정
       totalFileSize -= fileSizeList[fIndex];
       // 파일 배열에서 삭제
       delete fileList[fIndex];
       // 파일 사이즈 배열 삭제
       delete fileSizeList[fIndex];
		
       // 업로드 파일 테이블 목록에서 삭제
       $("#fileTr_" + fIndex).remove();
       
       if($('#input_file').attr("disabled")=='disabled'){
        $('#input_file').removeAttr("disabled");
        $('.file_area label').css({'background-color':'#fff', 'cursor':'pointer'});
       }

		$('input[name=res_file]').val('');
	    $('input[name=application_file]').val('');
    }
    
    function deleteFile_for_update(fIndex, fileName){
        $("#fileTr_" + fIndex).remove();
        deleteFileNameList.push(fileName);

        if($('#input_file').attr("disabled")=='disabled'){
            $('#input_file').removeAttr("disabled");
            $('.file_area label').css({'background-color':'#fff', 'cursor':'pointer'});
        }
    }
    
    function deleteAll() {
	   for (var i = 0; i < files.length; i++) {
	   // 전체 파일 사이즈 수정
       totalFileSize -= fileSizeList[i];
       // 파일 배열에서 삭제
       delete fileList[i];
       // 파일 사이즈 배열 삭제
       delete fileSizeList[i];
		
       // 업로드 파일 테이블 목록에서 삭제
       $("#fileTr_" + i).empty();
	   }
	   
	   if($('#input_file').attr("disabled")=='disabled'){
            $('#input_file').removeAttr("disabled");
            $('.file_area label').css({'background-color':'#fff', 'cursor':'pointer'});
        }
        $('input[name=res_file]').val('');
	    $('input[name=application_file]').val('');
	}
