function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}

$(function() {
    // 변수
    var certifinum = "1"; // 인증번호
    var getid = "1"; // 아이디

    // login 관련 javascript
    // loginform_check() : 로그인 폼 체크
    $(".login_btn").click(function(){
        if($("#loginform input[name=login_id]").val() == ""){
            alert("아이디를 입력해주세요.");
            $("#loginform input[name=login_id]").focus();
            return false;
        }
        if($("#loginform input[name=login_pw]").val() == ""){
            alert("비밀번호를 입력해주세요.");
            $("#loginform input[name=login_pw]").focus();
            return false;
        }
        $("#loginform").submit();
    });
    
    //enter로 로그인하기
    $(".id_pw_text").on("keyup",function(key){
        if(key.keyCode==13) {
            if($("#loginform input[name=login_id]").val() == ""){
                alert("아이디를 입력해주세요.");
                $("#loginform input[name=login_id]").focus();
                return false;
            }
            if($("#loginform input[name=login_pw]").val() == ""){
                alert("비밀번호를 입력해주세요.");
                $("#loginform input[name=login_pw]").focus();
                return false;
            }
            $("#loginform").submit();
        }
    });

    // 쿠키 설정
    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var key = getCookie("key");
    $("#user_id").val(key);

    if($("#user_id").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
        $("#chk_id").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }

    $("#chk_id").change(function(){ // 체크박스에 변화가 있다면,
        if($("#chk_id").is(":checked")){ // ID 저장하기 체크했을 때,
            setCookie("key", $("#user_id").val(), 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("key");
        }
    });

    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("#user_id").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#chk_id").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            setCookie("key", $("#user_id").val(), 7); // 7일 동안 쿠키 보관
        }
    });

});