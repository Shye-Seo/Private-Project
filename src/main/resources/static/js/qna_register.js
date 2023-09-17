$(function () {
            
            //필수값 체크
            $('#submit_btn').click(function(){
                let result = true;
                if(!$('input[name=agree_check]').is(":checked")){
					alert("개인정보 수집 및 이용에 동의해주세요.");
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
                submitDate('/questionAdd');
            })
            $('#update_btn').click(function(){
                let result = true;
                if(!$('input[name=agree_check]').is(":checked")){
					alert("개인정보 수집 및 이용에 동의해주세요.");
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
                submitDate('/questionUpdate');
            })
            
        });


        // form submit
        function submitDate(actionUrl){
            if (confirm("등록 하시겠습니까?") == true){

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

