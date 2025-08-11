$(function () {
    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });


    $(document).on("click", ".liked", clickLike);

    function clickLike() {
        let status = $(this).attr("id");
        let contentType = $("#form-col-liked").attr("data-content-type");
        let contentId = $("#form-col-liked").attr("data-content-id");

        if (status == "not-liked-heart") {
            registerLike(contentType, contentId);
        } else {

        }
    }

    // 요리팁 좋아요 등록
    function registerLike(contentType, contentId) {
        $.ajax({
            url: "/likes/new",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                contentType: contentType,
                contentId: contentId
            }),
            success: function (isSuccess) {
                if (isSuccess) {
                    $("#not-liked-heart").hide();
                    $("#liked-heart").show();
                } else {
                    alert("로그인이 필요한 기능입니다.");
                }
            }
        });
    }

});