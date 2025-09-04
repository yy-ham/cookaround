$(function () {
    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });


    let contentType = "COOKINGTIP";


    $(document).on("click", ".liked", clickLike);


    function clickLike() {
        let clickedCard = $(this).closest(".card-like");
        let status = $(this).attr("id");
        let contentId = clickedCard.attr("data-content-id");
        let likeId = $(this).closest(".card-like").attr("data-like-id");

        if (status == "not-liked-heart") {
            // 좋아요 등록
            registerLike(contentType, contentId, clickedCard);
        } else {
            // 좋아요 삭제
            removeLike(likeId, clickedCard);
        }
    }

    // 요리팁 좋아요 등록
    function registerLike(contentType, contentId, clickedCard) {
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
                    clickedCard.find("#not-liked-heart").hide();
                    clickedCard.find("#liked-heart").show();
                    clickedCard.attr("data-like-id", response.likeId);
                } else {
                    alert("로그인이 필요한 기능입니다.");
                }
            }
        });
    }

    // 요리팁 좋아요 삭제
    function removeLike(likeId, clickedCard) {
        $.ajax({
            url: "/likes/" + likeId,
            type: "DELETE",
            success: function (response) {
                if (response.isSuccess) {
                    clickedCard.find("#liked-heart").hide();
                    clickedCard.find("#not-liked-heart").show();
                } else {
                    alert("로그인이 필요한 기능입니다.");
                }
            }
        });
    }

});