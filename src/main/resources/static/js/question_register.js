$(function () {
//            var question_pw = $('input[name=question_pw]').val();
//            question_pw.length;
//            debugger;
            //필수값 체크
            $('#submit_btn').click(function(){
				var question_pw = $('input[name=question_pw]').val();
                let result = true;
                if(!$('input[name=agree_check]').is(":checked")){
					alert("개인정보 수집 및 이용에 동의해주세요.");
					return false;
				}else if(question_pw.length != 6){
					alert("비밀번호는 6자리로 입력해주세요.");
					return false;
				}
                $('.required').each(function(){
                    if($(this).val() == ""){
                        $(this).focus();
                        result = false;
                        return false;
                    }
                })
                debugger;
                if(!result) return false;
                submitDate('/questionAdd');
            })
            $('#update_btn_qna').click(function(){
				var question_pw = $('input[name=question_pw]').val();
                let result = true;
                if(!$('input[name=agree_check]').is(":checked")){
					alert("개인정보 수집 및 이용에 동의해주세요.");
					return false;
				}else if(question_pw.length != 6){
					alert("비밀번호는 6자리로 입력해주세요.");
					return false;
				}
                $('.required').each(function(){
                    if($(this).val() == ""){
                        $(this).focus();
                        result = false;
                        return false;
                    }
                })
                if(!result) return false;
                updateDate('/questionUpdate');
            })
            
        });


        // form submit
        function submitDate(actionUrl){
            if (confirm("등록하시겠습니까?") == true){

                // var uploadFileList = Object.keys(fileList);
                var form = $('form[name="questionAddForm"]');
                var formData = new FormData(form[0]);

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
        
        // form submit
        function updateDate(actionUrl){
            if (confirm("수정하시겠습니까?") == true){

                // var uploadFileList = Object.keys(fileList);
                var form = $('form[name="questionUpdateForm"]');
                var formData = new FormData(form[0]);

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

