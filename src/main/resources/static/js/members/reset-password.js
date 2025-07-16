$(function () {

    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });


    // 비밀번호 재설정
    $(document).on("click", "#reset-password-btn", resetPassword);
    $(document).on("click", "#reset-password-cancel-btn", resetPasswordCancel);


    // 비밀번호 재설정
    function resetPassword(e) {
        e.preventDefault();
        if (validateNewPassword()) {
            $("form").submit();
        }
    }

    // 비밀번호 재설정 취소
    function resetPasswordCancel(e) {
        e.preventDefault();
        if (confirm("비밀번호 재설정을 취소하시겠습니까?")) {
            location.href = "/members/login";
        }
    }


    // 새 비밀번호 유효성 검사
    function validateNewPassword() {
        let newPassword = $("#new-password").val();
        let newPasswordCheck = $("#new-password-check").val();

        if (!checkRequiredPassword(newPassword)) {
            return false;
        }
        if (!checkFormatPassword(newPassword)) {
            return false;
        }
        if (!checkRequiredPasswordCheck(newPasswordCheck)) {
            return false;
        }
        if (!validatePasswordCheck(newPassword, newPasswordCheck)) {
            return false;
        }

        return true;
    }

    // 새 비밀번호 입력 여부 확인
    function checkRequiredPassword(newPassword) {
        if (newPassword === "") {
            alert("새 비밀번호를 입력해 주세요.");
            return false;
        }
        return true;
    }

    // 새 비밀번호 형식 검사 (영문 대소문자, 숫자, 특수문자 포함 8~15자 이하)
    function checkFormatPassword(newPassword) {
        const passwordRegex = /^[A-Za-z\d!@#$%^&*]{8,15}$/;
        if (!passwordRegex.test(newPassword)) {
            alert("비밀번호는 8~15자의 영문 대소문자, 숫자, 특수문자를 사용해 주세요.");
            return false;
        }
        return true;
    }

    // 새 비밀번호 확인 입력 여부 확인
    function checkRequiredPasswordCheck(newPasswordCheck) {
        if (newPasswordCheck === "") {
            alert("새 비밀번호 확인을 입력해 주세요.");
            return false;
        }
        return true;
    }

    // 새 비밀번호 및 새 비밀번호 확인 일치 여부 검사
    function validatePasswordCheck(newPassword, newPasswordCheck) {
        if (newPasswordCheck !== newPassword) {
            alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return false;
        }
        return true;
    }

});