$(function () {
    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });

    listMyRecipe(); // 마이페이지 첫 화면에서 조회

    // 프로필 사진 변경 모달
    $(document).on("click", "#edit-profile-btn", openModal);
    $(document).on("click", "#close-modal-btn", closeModal);

    // 새로운 프로필 사진 업로드
    $(document).on("click", "#upload-profile-btn", uploadFile);
    $(document).on("change", "#upload-file", previewNewProfile);
    
    // 프로필 사진 삭제 (기본 이미지 설정)
    $(document).on("click", "#delete-profile-btn", deleteProfile);

    // 프로필 사진 변경
    $(document).on("click", "#cancel-edit-profile-btn", closeModal);


    // 내가 쓴 글/후기
    $(document).on("click", "#recipe-list", listMyRecipe); // 레시피
    $(document).on("click", "#cooking-tip-list", listMyCookingTip); // 요리팁

    // 내가 쓴 요리팁
    function listMyCookingTip() {
        $.ajax({
            url: "/api/members/mypage/cooking-tips",
            type: "GET",
            success: function (cookingTips) {
                $(".post-menu-item").removeClass("active");
                $("#cooking-tip-list").addClass("active");
                renderPost(cookingTips);
            },
        });
    }

    // 내가 쓴 레시피
    function listMyRecipe() {
        $.ajax({
            url: "/api/members/mypage/recipes",
            type: "GET",
            success: function (recipes) {
                $(".post-menu-item").removeClass("active");
                $("#recipe-list").addClass("active");
                renderPost(recipes);
            },
        });
    }

    // 내가 쓴 글/후기 - 동적 HTML 태그 생성
    function renderPost(posts) {
        let table = $("#post-table-body");
        table.empty();

        for (const post of posts) {
            let row = $("<tr>");
            let id = $("<td>").text(post.id);
            let category = $("<td>").text(post.category);
            let title = $("<td>").text(post.title);
            let createdAd = $("<td>").text(post.createdAt);

            row.append(id);
            row.append(category);
            row.append(title);
            row.append(createdAd);

            table.append(row);
        }
    }


    // 모달 열기
    function openModal() {
        $("#modal-background, #modal-container").fadeIn();
    }

    // 모달 닫기
    function closeModal() {
        $("#modal-background, #modal-container").fadeOut();
    }

    // input #upload-file 실행
    function uploadFile() {
        $("#upload-file").click();
    }

    // 프로필 사진 편집
    function previewNewProfile() {
        let newProfile = this.files;
        let imageUrl = URL.createObjectURL(newProfile[0]);
        $("#modal-profile img").attr("src", imageUrl);
    }

    // 프로필 사진 삭제 (기본 이미지 설정)
    function deleteProfile() {
        $("#modal-profile img").attr("src", "/uploads/profile/default.jpg");
        $("#default-profile").val(true);
    }

});