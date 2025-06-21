
$(document).ready(function() {

    let category = 'ALL';
    // let sort = 'newest';

    showRecipeList(category);

    // 카테고리 버튼 클릭 시 비동기 처리
    $('.category-section button').click(function() {
        const category = $(this).data('category');
        showRecipeList(category);
    }); // end of $('.category-section button').click


/*    // 정렬 버튼
    $('.sort-section button').click(function() {
        const sort = $(this).data('sort');
        showRecipeList(category);
    }); // end of $('.sort-section button').click
*/

    // 카드 클릭 시 상세 페이지로 이동
    $(document).on('click', '.recipe-card', function() {
        const recipeId = $(this).data('id');
        console.log("recipeId: ",recipeId);
        if(recipeId) {
            window.location.href = `/recipe/detail?id=${recipeId}`;
        }
    });

}); // end of $(document).ready(function() {})-----------------------------


// 레시피 목록 띄우는 함수
function showRecipeList(category) {
    $.ajax({
        url: '/recipe/api/list',
        method: 'GET',
        data: {category: category},
        dataType: 'json',
        success: function(data) {
            const $row = $('#recipe-list .row');
            $row.empty();

            if(data.length === 0) {
                $row.append('<p>레시피가 없습니다.</p>');
                return;
            }

            data.forEach(recipe => {
                const $card = recipeByCategory(recipe);
                $row.append($card);
            });
        },
        error: function(err) {
            console.log(err);
            alert("데이터 로딩 실패");
        }
    }); // end of ajax
} // end of function showRecipeList(category)--------------


// 카테고리 선택 함수
function recipeByCategory(recipe) {
    const template = $('#recipe-list-template').html();
    const $card = $(template); // 문자열을 jQuery 객체로 변환

    $card.find('.recipe-card').attr('data-id', recipe.id); // 레시피 하나를 특정하기 위해 data-id 붙임
    
    $card.find('.card-img').attr('src', 'https://i.namu.wiki/i/4-iX1WOxRnJOYQXM1IzJYwAeHtDRGv4HnO6xR0s6ZpsiltAmpO_RC7oyPXy9vIOYrFjapiqUhgZFH0O96h8g4w.webp');
    $card.find('.card-title').text(recipe.title);
    $card.find('.card-memberId').text(recipe.memberId);

    return $card;
} // end of function recipeByCategory(recipe)-----------------