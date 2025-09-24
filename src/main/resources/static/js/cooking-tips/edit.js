$(function () {
    // AJAX POST 요청 시 CSRF 토큰을 헤더에 포함
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });


    // 사진 등록 버튼 클릭
    $(document).on("click", "#image-add-btn", addImage);

    // 업로드 이미지 미리보기
    $(document).on("change", "#images", previewImages);
    $(document).on("click", "#previewImages button[type='button']", deleteImage);

    // 요리팁 수정 버튼 클릭
    $(document).on("click", "#modify-btn", modifyCookingTip);
    // 요리팁 수정 취소 버튼 클릭
    $(document).on("click", "#cancel-btn", cancelCookingTip);


    // 이미지
    let previewImageList = []; // 신규 이미지
    let deletedImageList = [];  // 삭제 이미지
    const existingImages = $(".preview-image").length; // 기존 이미지 개수

    const path = window.location.pathname; // "/cooking-tips/123/edit"
    const cookingTipId = path.split("/")[2]; // "123"


    // 폼 내용 유효성 검사
    function validateForm() {
        let category = $("#category").val().trim();
        let title = $("#title").val().trim();
        let content = $("#content").val().trim();

        if (!category) {
            alert("카테고리를 선택해 주세요.");
            return false;
        }

        if (!title) {
            alert("요리팁 이름을 입력해 주세요.");
            return false;
        }

        if (!content) {
            alert("요리팁 내용을 입력해 주세요.");
            return false;
        }

        let currentImages = $(".preview-image").length;
        if (currentImages == 0) {
            alert("사진을 한 장 이상 등록해 주세요.");
            return false;
        }

        return true;
    }

    // 요리팁 수정
    function modifyCookingTip(e) {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        $.ajax({
            url: "/cooking-tips/" + cookingTipId,
            type: "PATCH",
            data: createFormData(),
            processData: false,
            contentType: false,
            success: function (cookingTipId) {
                if (cookingTipId != 0) {
                    location.href = "/cooking-tips/" + cookingTipId;
                }
            }
        });
    }

    // FormData 생성
    function createFormData() {
        let formData = new FormData();
        formData.append("category", $("#category").val());
        formData.append("title", $("#title").val());
        formData.append("description", $("#description").val());
        formData.append("content", $("#content").val());

        // 신규 이미지
        for (let i = 0; i < previewImageList.length; i++) {
            formData.append("images", previewImageList[i].file);
        }

        // 삭제 이미지 번호
        for (let i = 0; i < deletedImageList.length; i++) {
            formData.append("deletedImages", deletedImageList[i]);
        }

        return formData;
    }

    // 이미지 등록
    function addImage() {
        $("#images").click(); // 파일 선택 창 열기
    }

    // 이미지 삭제
    function deleteImage() {
        $(this).closest("li").remove(); // this: 버튼 태그
        let img = $(this).siblings("img");
        let id = img.attr("data-id");

        if (id != null) {
            deletedImageList.push(id);
            // existingImageList = existingImageList.filter(image => image != id);
        } else {
            let url = img.attr("src");
            for (let i = 0; i < previewImageList.length; i++) {
                if (previewImageList[i].url == url) {
                    previewImageList.splice(i, 1);
                }
            }
        }

        let totalCount = existingImages + previewImageList.length - deletedImageList.length;
        $("#image-count").text(totalCount + " / 10");
    }

    // 이미지 미리보기
    function previewImages() {
        let fileList = this.files;
        let fileCount = fileList.length;
        let totalCount = existingImages + previewImageList.length + fileCount - deletedImageList.length;

        if (totalCount > 10) {
            alert("사진은 최대 10장까지 등록할 수 있습니다.");
        } else {
            $("#image-count").text(totalCount + " / 10");
            renderPreview(fileList);
        }
    }

    // 이미지 미리보기 렌더링
    function renderPreview(fileList) {
        for (let i = 0; i < fileList.length; i++) {
            let imageUrl = URL.createObjectURL(fileList[i]);

            let ul = $("#previewImages");
            let li = $("<li>");
            li.addClass("preview-image");

            let img = $("<img>");
            img.attr("src", imageUrl);
            img.attr("width", 70);
            img.attr("height", 70);

            let btn = $("<button>");
            btn.addClass("remove-btn");
            btn.text("X");
            btn.attr("type", "button");

            li.append(img);
            li.append(btn);
            ul.append(li);

            previewImageList.push({
                file: fileList[i],
                url: imageUrl
            });
        }
    }

    // 요리팁 수정 취소
    function cancelCookingTip() {
        if (confirm("요리팁 수정을 취소하시겠습니까? 작성중인 내용은 저장되지 않습니다.")) {
            location.href = "/cooking-tips/" + cookingTipId;
        }
    }

});