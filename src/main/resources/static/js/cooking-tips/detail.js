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

    // 요리팁 삭제
    $(document).on("click", "#delete-btn", deleteCookingTip);

    getLike(); // 현재 좋아요 여부 조회

    let contentId = $("#form-col-liked").attr("data-content-id");
    let contentType = $("#form-col-liked").attr("data-content-type");


    // 요리팁 삭제
    function deleteCookingTip(e) {
        e.preventDefault();

        if (confirm("요리팁을 삭제하시겠습니까?")) {
            $.ajax({
                url: "/cooking-tips/" + contentId,
                type: "DELETE",
                success: function (isDeleted) {
                    if (isDeleted) {
                        alert("삭제가 완료되었습니다.");
                        location.href = "/cooking-tips";
                    }
                },
            })
        }
    }


    function clickLike() {
        let status = $(this).attr("id");

        if (status == "not-liked-heart") {
            // 좋아요 등록
            registerLike(contentType, contentId);
        } else {
            // 좋아요 삭제
            removeLike(contentType, contentId);
        }
    }

    // 요리팁 좋아요 여부 조회
    function getLike() {
        let contentType = $("#form-col-liked").attr("data-content-type");
        let contentId = $("#form-col-liked").attr("data-content-id");

        $.ajax({
            url: "/cooking-tips/" + contentId + "/likes",
            type: "GET",
            contentType: "application/json",
            data: {
                contentType: contentType,
            },
            success: function (response) {
                if (response != null) {
                    $("#like-id").val(response.id);
                }
            }
        });
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
            success: function (response) {
                if (response.isSuccess) {
                    $("#not-liked-heart").hide();
                    $("#liked-heart").show();
                    getLike();
                    $("#like-count").text(response.likeCount);
                } else {
                    alert("로그인이 필요한 기능입니다.");
                }
            }
        });
    }

    // 요리팁 좋아요 삭제
    function removeLike(contentType, contentId) {
        let id = $("#like-id").val();
        $.ajax({
            url: "/likes/" + id,
            type: "DELETE",
            contentType: "application/json",
            data: JSON.stringify({
                contentType: contentType,
                contentId: contentId
            }),
            success: function (response) {
                if (response.isSuccess) {
                    $("#liked-heart").hide();
                    $("#not-liked-heart").show();
                    getLike();
                    if (response.likeCount > 0) {
                        $("#like-count").text(response.likeCount);
                    }else {
                        $("#like-count").text("");
                    }
                } else {
                    alert("로그인이 필요한 기능입니다.");
                }
            }
        });
    }

});