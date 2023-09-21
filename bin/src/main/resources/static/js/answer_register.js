$(function () {
            
            //필수값 체크
            $('#answer_submit_btn').click(function(){
                let result = true;
                $('.required').each(function(){
                    if($(this).val() == ""){
                        $(this).focus();
                        result = false;
                        return false;
                    }
                })
                if(!result) return false;
                submitDate('/answerAdd');
            })
            $('#answer_update_btn').click(function(){
                let result = true;
                $('.required_update').each(function(){
                    if($(this).val() == ""){
                        $(this).focus();
                        result = false;
                        return false;
                    }
                })
                debugger;
                if(!result) return false;
                UpdateDate('/answerUpdate');
            })
            
        });


        // form submit
        function submitDate(actionUrl){
            if (confirm("등록하시겠습니까?") == true){

                // var uploadFileList = Object.keys(fileList);
                var form = $('form[name="answerAddForm"]');
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
        function UpdateDate(actionUrl){
            if (confirm("수정하시겠습니까?") == true){

                // var uploadFileList = Object.keys(fileList);
                var form = $('form[name="answerUpdateForm"]');
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

