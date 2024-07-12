$(document).ready(function() {
    axios.get("/api/v1/front/categories", {
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.status === 200) {
                let categories = response.data.categories; // categories 리스트에 접근

                // categories가 배열인지 확인
                if (!Array.isArray(categories)) {
                    console.log("카테고리 데이터가 올바르지 않습니다.");
                    return;
                }

                const dropdownMenu = $('.shop-category .dropdown-menu');
                dropdownMenu.empty(); // 기존 항목을 제거하고 새로 추가

                categories.forEach(category => {
                    let subCategoryItems = '';
                    if (category.sub && Array.isArray(category.sub)) {
                        category.sub.forEach(subCategory => {
                            subCategoryItems += `<li><a href="/search/category?category=${subCategory.name}">${subCategory.name}</a></li>`;
                        });
                    }

                    const categoryItem = `
                    <li class="dropdown-submenu">
                        <a href="/search/category?category=${category.name}" class="category-link">
                            ${category.name}
                        </a>
                        <ul class="dropdown-menu">
                            ${subCategoryItems}
                        </ul>
                    </li>
                `;

                    dropdownMenu.append(categoryItem);
                });

                // 카테고리 링크 클릭 이벤트 처리
                $('.category-link').on('click', function(e) {
                    e.stopPropagation(); // 이벤트 전파 중지
                    window.location.href = $(this).attr('href'); // 링크로 이동
                });

                // 드롭다운 토글 처리
                $('.dropdown-submenu > a').on("click", function(e) {
                    var submenu = $(this).next('.dropdown-menu');
                    $('.dropdown-menu').not(submenu).hide(); // 다른 서브메뉴 숨기기
                    submenu.toggle();
                    e.stopPropagation(); // 이벤트 전파 중지
                    e.preventDefault(); // 기본 동작 중지
                });
            } else {
                console.log("카테고리 불러오기에 실패하였습니다.");
            }
        })
        .catch(error => {

            // console.error('실패 : ', error.response ? error.response.data : error.message);
            alert('실패 : ', error.response ? error.response.data : error.message)
            // alert("오류가 발생하였습니다: " + (error.response ? error.response.data : error.message));
        });
});
