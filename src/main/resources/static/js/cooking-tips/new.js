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

    // 요리팁 등록 버튼 클릭
    $(document).on("click", "#register-btn", registerCookingTip);


    let previewImageList = [];

    // 요리팁 등록
    function registerCookingTip(e) {
        e.preventDefault();

        let formData = new FormData();
        formData.append("category", $("#category").val());
        formData.append("title", $("#title").val());
        formData.append("description", $("#description").val());
        formData.append("content", $("#content").val());

        for (let i = 0; i < previewImageList.length; i++) {
            formData.append("images", previewImageList[i].file);
        }

        $.ajax({
            url: "/cooking-tips/new",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (cookingTipId) {
                if (cookingTipId != 0) {
                    location.href = "/cooking-tips/" + cookingTipId;
                }
            }
        });
    }

    // 이미지 등록
    function addImage() {
        $("#images").click();
    }

    // 이미지 삭제
    function deleteImage() {
        $(this).closest("li").remove();

        let url = $(this).siblings("img").attr("src");

        for(let i = 0; i < previewImageList.length; i++) {
            if (previewImageList[i].url == url) {
                previewImageList.splice(i, 1);
            }
        }

        $("#image-count").text(previewImageList.length + " / 10");
    }

    // 이미지 미리보기
    function previewImages() {
        let fileList = this.files;
        let fileCount = fileList.length;
        let totalCount = previewImageList.length + fileCount;

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
            li.css("margin-right", "5px");

            let img = $("<img>");
            img.attr("src", imageUrl);
            img.attr("width", 100);
            img.attr("height", 100);

            let btn = $("<button>");
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

});