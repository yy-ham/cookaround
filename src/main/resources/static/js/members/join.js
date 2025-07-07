$(function () {

    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });


    let isVerifiedEmail = false;


    // 회원가입 필드 유효성 검사
    $(document).on("blur", "#login-id", validateLoginId);
    $(document).on("blur", "#password", validatePassword);
    $(document).on("blur", "#password-check", validatePasswordCheck);
    $(document).on("blur", "#email", validateEmail);

    // 이메일 인증
    $(document).on("click", "#email-send-code-btn", sendEmailcode);
    $(document).on("click", "#email-verify-code-btn", verifyEmailCode);

    // 회원가입
    $(document).on("click", "#join-btn", join);
    $(document).on("click", "#join-cancel-btn", joinCancel);


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
            url: "/members/check-login-id",
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
            $("#password-check-error-text").text("비밀번호 확인: 비밀번호와 일치하지 않습니다. 다시 입력해 주세요.").show();
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
            url: "/members/check-email",
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


    // 이메일 인증코드 전송
    function sendEmailcode(e) {
        e.preventDefault();

        let email = $("#email").val();

        // 이메일 유효성 검사 완료 후 이메일 인증 진행
        if (validateEmail()) {
            $("#email-verify-code-error-text").hide();
            $("#form-email-verify").hide();

            $.ajax({
                url: "/members/send-email-code",
                type: "POST",
                data: {
                    email: email,
                    type: "join"
                },
                success: function (isSuccess) {
                    if (isSuccess == null) {
                        alert("인증코드 발송에 실패했습니다. 다시 시도해 주세요.");
                    } else {
                        alert("이메일이 발송되었습니다.");
                        $("#form-email-verify").show();
                        $("#email-verify-code-btn").show();
                        startTimer();
                    }
                },
            });
        }
    }

    // 이메일 인증코드 확인
    function verifyEmailCode(e) {
        e.preventDefault();

        let email = $("#email").val();
        let verificationCode = $("#email-verify-code").val();

        $("#email-verify-code-success-text").hide();
        $("#email-verify-code-error-text").hide();

        $.ajax({
            url: "/members/verify-email-code",
            type: "POST",
            data: {
                email: email,
                verificationCode: verificationCode
            },
            success: function (isVerified) {
                if (isVerified === "SUCCESS") {
                    $("#email-verify-code-success-text").text("인증 완료").show();
                    $("#email-verify-code-btn").attr("disabled",true);
                    $("#timer").hide();
                    isVerifiedEmail = true;
                    return;
                }

                if (isVerified === "EXPIRED") {
                    $("#email-verify-code-btn").hide();
                    $("#email-verify-code-error-text").text("시간이 만료되었습니다. 인증을 다시 시도해 주세요.").show();
                    return;
                }

                if (isVerified === "INVALID") {
                    $("#email-verify-code-error-text").text("올바른 인증번호를 입력해 주세요.").show();
                    return;
                }
            }
        });
    }

    // 이메일 인증코드 타이머
    function startTimer() {
        let display = $("#timer");
        let duration = 60 * 5; //지속 시간
        let minutes = String(Math.floor(duration / 60)).padStart(2, '0');
        let seconds = String(duration % 60).padStart(2, '0');
        display.text(minutes + ":" + seconds);

        let timer = setInterval(function () {
            duration--;
            minutes = String(Math.floor(duration / 60)).padStart(2, '0');
            seconds = String(duration % 60).padStart(2, '0');

            if (duration < 0) {
                clearInterval(timer);
                $("#email-verify-code-btn").hide();
                $("#email-verify-code-error-text").text("시간이 만료되었습니다. 인증을 다시 시도해 주세요.").show();
                return;
            }
            display.text(minutes + ":" + seconds);
        }, 1000);
    }


    // 회원가입
    function join(e) {
        e.preventDefault();

        // 최종 유효성 검사
        validateLoginId();
        validatePassword();
        validatePasswordCheck();
        validateEmail();

        if (!isVerifiedEmail) {
            return;
        }

        $("form").submit();
    }

    // 회원가입 취소
    function joinCancel(e) {
        e.preventDefault();
        if (confirm("회원가입을 취소하시겠습니까? 작성중인 내용은 저장되지 않습니다.")) {
            location.href = "/";
        }
    }

});