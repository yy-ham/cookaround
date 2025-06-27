$(document).ready(function() {

    let category = 'ALL';
    let sort = 'newest';

    showRecipeList(category, sort);

    // 카테고리 버튼 클릭
    $('.category-section button').click(function() {
        category = $(this).data('category');
        showRecipeList(category, sort);
    }); // end of $('.category-section button').click


    // 정렬 버튼 클릭
    $('.sort-section button').click(function() {
        sort = $(this).data('sort');
        showRecipeList(category, sort);
    }); // end of $('.sort-section button').click


    // 카드 클릭 시 상세 페이지로 이동
    $(document).on('click', '.recipe-card', function() {
        const recipeId = $(this).data('id');
        console.log("recipeId확인: ", recipeId);

        if(recipeId) {
            window.location.href = `/recipe/detail?id=${recipeId}`;
        }
    });

}); // end of $(document).ready(function() {})-----------------------------


// 레시피 목록 띄우는 함수
function showRecipeList(category, sort) {
    $.ajax({
        url: '/recipe/api/list',
        method: 'GET',
        data: {
            category: category,
            sort: sort
        },
        dataType: 'json',
        success: function(data) {
            const $row = $('#recipe-list .row');
            $row.empty();

            if(data.length === 0) {
                $row.append('<p>레시피가 없습니다.</p>');
                return;
            }

            data.forEach(recipe => {
                const $card = recipeByButton(recipe);
                $row.append($card);
            });
        },
        error: function(err) {
            console.log(err);
            alert("데이터 로딩 실패");
        }
    }); // end of ajax
} // end of function showRecipeList(category)--------------


// 버튼(카테고리, 정렬) 클릭 함수
function recipeByButton(recipe) {
    const template = $('#recipe-list-template').html();
    const $card = $(template); // 문자열을 jQuery 객체로 변환

    $card.find('.recipe-card').attr('data-id', recipe.id); // 레시피 하나를 특정하기 위해 data-id 붙임
    
    $card.find('.card-img').attr('src', 'https://newsimg.sedaily.com/2020/11/17/1ZAFYQN80J_1.jpg');
    $card.find('.card-title').text(recipe.title);

    $card.find('.card-profile').text(recipe.memberProfile);
    $card.find('.card-loginId').text(recipe.loginId);

    $card.find('.review-avg').text(Number(recipe.avgRating).toFixed(1));
    $card.find('.review-count').text(recipe.reviewCount);

    return $card;
} // end of function recipeByCategory(recipe)-----------------



