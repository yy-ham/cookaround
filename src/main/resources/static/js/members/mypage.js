$(function () {

    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });

    // 모달
    $(document).on("click", "#edit-profile-btn", openModal);
    $(document).on("click", "#close-modal-btn", closeModal);

    // 새로운 프로필 사진 업로드
    $(document).on("click", "#upload-profile-btn", uploadFile);
    $(document).on("change", "#upload-file", previewNewProfile);
    
    // 프로필 사진 삭제 (기본 이미지 설정)
    $(document).on("click", "#delete-profile-btn", deleteProfile);

    // 프로필 사진 편집
    $(document).on("click", "#cancel-edit-profile-btn", closeModal);


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