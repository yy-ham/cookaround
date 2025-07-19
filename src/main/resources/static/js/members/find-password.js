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

    // 비밀번호 찾기
    $(document).on("click", "#find-password-btn", findPassword);
    $(document).on("click", "#find-password-cancel-btn", findPasswordCancel);


    $(document).on("click", "#email-send-code-btn", function () {
        if (validateLoginId()) {
            sendEmailcode();
        }
    });
    $(document).on("click", "#email-verify-code-btn", verifyEmailCode);


    // 비밀번호 찾기
    function findPassword(e) {
        e.preventDefault();

        if (!isVerifiedEmail) {
            alert("이메일 인증을 해주세요.");
            return;
        }

        $("form").submit();
    }

    // 비밀번호 찾기 취소
    function findPasswordCancel(e) {
        e.preventDefault();
        if (confirm("비밀번호 찾기를 취소하시겠습니까?")) {
            location.href = "/members/login";
        }
    }


    // 아이디 유효성 검사
    function validateLoginId() {
        let loginId = $("#login-id").val();

        if (!checkRequiredLoginId(loginId)) {
            return false;
        }
        if (!existsByLoginId(loginId)) {
            return false;
        }

        return true;
    }

    // 아이디 입력 여부 확인
    function checkRequiredLoginId(loginId) {
        if (loginId === "") {
            alert("아이디를 입력해 주세요.");
            return false;
        }
        return true;
    }

    // 아이디 존재 여부 검사
    function existsByLoginId(loginId) {
        let existsByLoginId = false;
        $.ajax({
            url: "/members/check-login-id",
            type: "GET",
            async: false,
            data: {
                loginId: loginId
            },
            success: function (exists) {
                if (!exists) {
                    alert("입력하신 아이디를 찾을 수 없습니다.");
                }
                existsByLoginId = exists;
            }
        });
        return existsByLoginId;
    }


    // 이메일 유효성 검사
    function validateEmail() {
        let isValidEmail = false;
        let email = $("#email").val();

        if (!checkRequiredEmail(email)) {
            return;
        }
        if (!checkFormatEmail(email)) {
            return;
        }

        isValidEmail = true;
        return isValidEmail;
    }

    // 이메일 입력 여부 확인
    function checkRequiredEmail(email) {
        if (email == "") {
            alert("이메일을 입력해 주세요.");
            return false;
        }
        return true;
    }

    // 이메일 형식 검사
    function checkFormatEmail(email) {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            alert("이메일 주소가 정확한지 확인해 주세요.");
            return false;
        }
        return true;
    }


    // 이메일 인증코드 전송
    function sendEmailcode() {
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
                    type: "find-password"
                },
                success: function (isSuccess) {
                    if (isSuccess == null) {
                        alert("인증코드 발송에 실패했습니다. 다시 시도해 주세요.");
                    } else {
                        alert("이메일이 발송되었습니다. " +
                            "인증코드가 오지 않으면 입력하신 정보가 회원정보와 일치하는지 확인해 주세요.");
                        $("#form-email-verify").show();
                        $("#email-verify-code-btn").show();
                        startTimer();
                    }
                },
            });
        }
    }

    // 이메일 인증코드 확인
    function verifyEmailCode() {
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

});