$(document).ready(function () {
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
                $('.category-link').on('click', function (e) {
                    e.stopPropagation(); // 이벤트 전파 중지
                    window.location.href = $(this).attr('href'); // 링크로 이동
                });

                // 드롭다운 토글 처리
                $('.dropdown-submenu > a').on("click", function (e) {
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
            alert('서버 연결 중 잠시만 기다려주세요~  ', error.response ? error.response.data : error.message)
        });


    // Axios를 사용하여 백엔드에서 데이터를 가져옵니다.
    axios.get("/api/v1/front/members/member-name", {
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        const memberData = response.data;
        const memberName = memberData.memberName + "님"; // 응답 받은 데이터 중에서 사용할 값을 선택합니다.

        // HTML 요소에 데이터를 반영합니다.
        document.getElementById("member-name").innerText = memberName;
    }).catch(error => {
        document.getElementById("member-name").innerText = "비회원";
    });

});
