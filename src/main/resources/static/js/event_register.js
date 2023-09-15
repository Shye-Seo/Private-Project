$(function () {

            $("#input_file").bind('change', function() {
                selectFile(this.files);

                //파일 5개 이상일 시 버튼 비활성화
                if($('#file_names').children().length >=5){
                    $('#input_file').attr("disabled","disabled");
                    $('.area3 .files_label').css({'background-color':'#eee', 'cursor':'unset'});
                }
            });
            
            $("#thumbnail").bind('change', function() {
                selectFile_thumbnail(this.files);

                //파일 1개 이상일 시 버튼 비활성화
                if($('#file_names_thumbnail').children().length > 1){
                    $('#thumbnail').attr("disabled","disabled");
                    $('.area3 .thumbnail_label').css({'background-color':'#eee', 'cursor':'unset'});
                }
            });

            //필수값 체크
            $('#submit_btn').click(function(){
                let result = true;
                $('.required').each(function(){
                    if($(this).val() == ""){
                        $(this).focus();
                        result = false;
                        return false;
                    }
                })
                if($('#file_names_thumbnail').children().length < 1){
					alert('대표이미지를 첨부해주세요.');
                    return false;
				}else if($('#file_names_thumbnail').children().length > 1){
					alert('대표이미지는 하나만 첨부해주세요.');
                    return false;
				}else if(!$('input[name=event_screeningCheck]').is(":checked")){
					alert('영화상영여부를 선택해주세요.');
					return false;
				}
                if(!result) return false;
                submitDate('/eventAdd');
            })
            $('#update_btn').click(function(){
                let result = true;
                $('.required').each(function(){
                    if($(this).val() == ""){
                        $(this).focus();
                        result = false;
                        return false;
                    }
                })
                if($('#file_names_thumbnail').children().length < 1){
					alert('대표이미지를 첨부해주세요.');
                    return false;
				}else if($('#file_names_thumbnail').children().length > 1){
					alert('대표이미지는 하나만 첨부해주세요.');
                    return false;
				}else if(!$('input[name=event_screeningCheck]').is(":checked")){
					alert('영화상영여부를 선택해주세요.');
					return false;
				}
                if(!result) return false;
                submitDate('/eventUpdate');
            })
            
        });


        // form submit
        function submitDate(actionUrl){
            if (confirm("등록 하시겠습니까?") == true){

                for(let i = 0; i < deleteFileNameList.length; i++){
                    $('form[name="eventAddForm"]').append("<input type='hidden' name='deleteFileNameList' value='"+deleteFileNameList[i]+"'/>")
                }
                for(let i = 0; i < deleteFileNameList_thumbnail.length; i++){
                    $('form[name="eventAddForm"]').append("<input type='hidden' name='deleteFileNameList_thumbnail' value='"+deleteFileNameList_thumbnail[i]+"'/>")
                }

                // var uploadFileList = Object.keys(fileList);
                var form = $('form[name="eventAddForm"]');
                var formData = new FormData(form[0]);
                for (var i = 0; i < fileList.length; i++) {
                    formData.append('event_file', fileList[i]);
                    console.log(formData.get('event_file')) 
                    console.log(fileList[i]) 
                }

                $.ajax({
                    url : actionUrl,
                    data : formData,
                    type : 'POST',
                    enctype : 'multipart/form-data',
                    processData : false,
                    contentType : false,
                    cache : false,
                    success : function (result) {
                        window.location.href= result;
                    }
                });
                } else {
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
            
            var maxFileNum = 5;
            // 파일 최대갯수

            //행사수정 시 지울 파일 목록
            let deleteFileNameList = new Array();
            let deleteFileNameList_thumbnail = new Array();

            var files = new Array();
            
            /*-------------------대표이미지------------*/
            // 파일 리스트 번호
            var fileIndex_thumbnail = 0;
            // 등록할 전체 파일 사이즈
            var totalFileSize_thumbnail = 0;
            // 파일 리스트
            var fileList_thumbnail = 1;
            // 파일 사이즈 리스트
            var fileSizeList_thumbnail = 1;

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
   
   // 파일 선택시
        function selectFile_thumbnail(fileObject) {
            var thumbnail_file = $('#thumbnail')[0].files;
            // 다중파일 등록
            if (thumbnail_file != null) {
           
           for (var i = 0; i < thumbnail_file.length; i++) {
               // 파일 이름
               var fileName_thumbnail = thumbnail_file[i].name;
               var fileNameArr_thumbnail = fileName_thumbnail.split("\.");
               // 확장자
               var ext_thumbnail = fileNameArr_thumbnail[fileNameArr_thumbnail.length - 1];
               
               var special_pattern = /[\{\}\[\]|<>\"]/gi;

               if(special_pattern.test($("input[name='thumbnail_file']").val())){
                   alert('파일명에 특수문자를 제거해주세요.');
                   return false;
               }
               
               var fileSize_thumbnail = thumbnail_file[i].size; // 파일 사이즈(단위 :byte)
               console.log("fileSize="+fileSize_thumbnail);
               if (fileSize_thumbnail <= 0) {
                   console.log("0kb file return");
                   return;
               }
               
               var fileSizeKb_thumbnail = fileSize_thumbnail / 1024; // 파일 사이즈(단위 :kb)
               var fileSizeMb_thumbnail = fileSizeKb_thumbnail / 1024;    // 파일 사이즈(단위 :Mb)
               
               var fileSizeStr_thumbnail = "";
               if ((1024*1024) <= fileSize_thumbnail) {    // 파일 용량이 1메가 이상인 경우 
                   console.log("fileSizeMb="+fileSizeMb_thumbnail.toFixed(2));
                   fileSizeStr_thumbnail = fileSizeMb_thumbnail.toFixed(2) + " Mb";
               } else if ((1024) <= fileSize_thumbnail) {
                   console.log("fileSizeKb="+parseInt(fileSizeKb_thumbnail));
                   fileSizeStr_thumbnail = parseInt(fileSizeKb_thumbnail) + " kb";
               } else {
                   console.log("fileSize="+parseInt(fileSize_thumbnail));
                   fileSizeStr_thumbnail = parseInt(fileSize_thumbnail) + " byte";
               }
				
                   // 전체 파일 사이즈
                   totalFileSize_thumbnail += fileSizeMb_thumbnail;
                   // 파일 배열에 넣기
                   fileList_thumbnail[fileIndex_thumbnail] = thumbnail_file[i];
                   // 파일 사이즈 배열에 넣기
                   fileSizeList_thumbnail[fileIndex_thumbnail] = fileSizeMb_thumbnail;
                   // 업로드 파일 목록 생성
                   addFileList_thumbnail(fileIndex_thumbnail, fileName_thumbnail, fileSizeStr_thumbnail);
                   // 파일 번호 증가
                   fileIndex_thumbnail++;
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
               + "<img src='/imgs/close_btn_black.svg' href='#' onclick='deleteFile(" + fIndex + "); return false;'/>"
       html += "    </div>"
       html += "</div>"
       
       $('#file_names').append(html);
   }
   
   // 업로드 파일 목록 생성
   function addFileList_thumbnail(fIndex_thumbnail, fileName_thumbnail, fileSizeStr_thumbnail) {
	   var html = "";
       html += "<div id='fileTrThumb_" + fIndex_thumbnail + "'>";
       html += "    <div class='file_con'>";
       html += "		<div class='filename'>"+fileName_thumbnail + "</div>" 
               + "<img src='/imgs/close_btn_black.svg' href='#' onclick='deleteFile_thumbnail(" + fIndex_thumbnail + "); return false;'/>"
       html += "    </div>"
       html += "</div>"
       
       $('#file_names_thumbnail').empty();
       $('#file_names_thumbnail').append(html);
       $('input[name=event_poster]').val(fileName_thumbnail);
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
       
       if($('#file_names').children().length < 5){
	       if($('#input_file').attr("disabled")=='disabled'){
	        $('#input_file').removeAttr("disabled");
	        $('.area3 .files_label').css({'background-color':'#fff', 'cursor':'pointer'});
	       }
       }

        console.log("totalFileSize="+totalFileSize);
    }
    
    // 업로드 파일 삭제
   function deleteFile_thumbnail(fIndex_thumbnail) {
       console.log("deleteFile.fIndex=" + fIndex_thumbnail);
       // 전체 파일 사이즈 수정
       totalFileSize_thumbnail -= fileSizeList_thumbnail[fIndex_thumbnail];
       // 파일 배열에서 삭제
       delete fileList_thumbnail[fIndex_thumbnail];
       // 파일 사이즈 배열 삭제
       delete fileSizeList_thumbnail[fIndex_thumbnail];
		
       // 업로드 파일 테이블 목록에서 삭제
       $("#fileTrThumb_" + fIndex_thumbnail).remove();
       
       if($('#thumbnail').attr("disabled")=='disabled'){
        $('#thumbnail').removeAttr("disabled");
        $('.area3 thumbnail_label').css({'background-color':'#fff', 'cursor':'pointer'});
       }

        $('input[name=event_poster]').val('');
	    $('input[name=thumbnail_file]').val('');
    }

    function deleteFile_for_update(fIndex, fileName){
        $("#fileTr_" + fIndex).remove();
        deleteFileNameList.push(fileName);
		
		if($('#file_names').children().length < 5){
	        if($('#input_file').attr("disabled")=='disabled'){
	            $('#input_file').removeAttr("disabled");
	            $('.area3 .files_label').css({'background-color':'#fff', 'cursor':'pointer'});
	        }
        }
    }
    
    function deleteFile_for_update_thumbnail(fIndex_thumbnail, fileName_thumbnail){
        $("#fileTrThumb_" + fIndex_thumbnail).remove();
        deleteFileNameList_thumbnail.push(fileName_thumbnail);

        if($('#thumbnail').attr("disabled")=='disabled'){
        $('#thumbnail').removeAttr("disabled");
        $('.area3 thumbnail_label').css({'background-color':'#fff', 'cursor':'pointer'});
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
       $("#fileTr_" + i).remove();
	   }
	   
	}

    function chkTime(obj){
        let timeData = "";
        let text = $(obj).val();
    
        if(!text.includes('.')){
            if(text.length<3){
                $(obj).val(text);
            }else if(text.length<6){
                timeData += text.substr(0, 2);
                timeData += ":"
                timeData += text.substr(2);
                $(obj).val(timeData.substr(0,5));
            }
        }
    }
/**
 * 
 */