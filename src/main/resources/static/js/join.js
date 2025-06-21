$(function () {

    $(document).on("blur", "#login-id", validateLoginId);
    $(document).on("blur", "#password", validatePassword);
    $(document).on("blur", "#password-check", validatePasswordCheck);
    $(document).on("blur", "#email", validateEmail);


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


    // 비밀번호 유효성 검사
    function validatePassword() {
        let password = $("#password").val();
        $("#password-error-text").hide();

        if (!checkRequiredPassword(password)) {
            return;
        }
        checkFormatPassword(password);
    }

    // 비밀번호 입력 여부 확인
    function checkRequiredPassword(password) {
        if (password === "") {
            $("#password-error-text").text("비밀번호: 필수 정보입니다.").show();
            return false;
        }
        return true;
    }

    // 비밀번호 조합 검사 (영문 대/소문자, 숫자, 특수문자 포함 8~15자 이하)
    function checkFormatPassword(password) {
        const passwordRegex = /^[A-Za-z\d!@#$%^&*]{8,15}$/;
        if (!passwordRegex.test(password)) {
            $("#password-error-text").text("비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.").show();
            return false;
        }
        return true;
    }

    // 비밀번호 및 비밀번호 확일 일치 여부 검사
    function validatePasswordCheck() {
        let password = $("#password").val();
        let passwordCheck = $("#password-check").val();
        $("#password-check-error-text").hide();

        if (passwordCheck !== password) {
            $("#password-check-error-text").text("비밀번호와 일치하지 않습니다. 다시 입력해 주세요.").show();
        }
    }


    // 이메일 유효성 검사
    function validateEmail() {
        let isValidEmail = false;
        let email = $("#email").val();
        $("#email-error-text").hide();

        if (!checkRequiredEmail(email)) {
            return;
        }
        if (!checkFormatEmail(email)) {
            return;
        }
        if (checkDuplicateEmail(email)) {
            return;
        }

        isValidEmail = true;
        return isValidEmail;
    }

    // 이메일 입력 여부 확인
    function checkRequiredEmail(email) {
        if (email === "") {
            $("#email-error-text").text("이메일: 필수 정보입니다.").show();
            return false;
        }
        return true;
    }

    // 이메일 형식 검사
    function checkFormatEmail(email) {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            $("#email-error-text").text("이메일 주소가 정확한지 확인해 주세요.").show();
            return false;
        }
        return true;
    }

    // 이메일 중복 검사
    function checkDuplicateEmail(email) {
        let isDuplicateEmail = false;
        $.ajax({
            url: "/member/check-email",
            type: "GET",
            async: false,
            data: {
                email: email
            },
            success: function (isDuplicate) {
                if (isDuplicate) {
                    $("#email-error-text").text("이미 가입된 이메일입니다.").show();
                    isDuplicateEmail = isDuplicate;
                }
            },
        });
        return isDuplicateEmail;
    }

});