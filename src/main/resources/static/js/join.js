$(function () {

    $(document).on("blur", "#login-id", validateLoginId);


    // 아이디 유효성 검사
    function validateLoginId() {
        let loginId = $("#login-id").val();
        $("#id-error-text").hide();

        if (!checkRequiredLoginId(loginId)) {
            return;
        }
        if (!checkFormatLoginId(loginId)) {
            return;
        }
        checkDuplicateLoginId(loginId);
    }

    // 아이디 입력 여부 확인
    function checkRequiredLoginId(loginId) {
        if (loginId === "") {
            $("#id-error-text").text("아이디: 필수 정보입니다.").show();
            return false;
        }
        return true;
    }

    // 아이디 조합 검사 (영문 소문자, 숫자 포함 5~20자 이하)
    function checkFormatLoginId(loginId) {
        const idRegex = /^[a-z0-9]{5,20}$/;
        if (!idRegex.test(loginId)) {
            $("#id-error-text").text("아이디: 5~20자의 영문 소문자, 숫자만 사용 가능합니다.").show();
            return false;
        }
        return true;
    }

    // 중복 아이디 검사
    function checkDuplicateLoginId(loginId) {
        $.ajax({
            url: "/member/check-login-id",
            type: "GET",
            data: {
                loginId: loginId
            }
        }).then(function (isDuplicate) {
            if (isDuplicate) {
                $("#id-error-text").text("아이디: 사용할 수 없는 아이디입니다. 다른 아이디를 입력해 주세요.").show();
            }
        });
    }

});