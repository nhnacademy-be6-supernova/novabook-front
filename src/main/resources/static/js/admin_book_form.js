// Handle book search form submission
// 전역 스코프에 books 배열 선언
let books = [];

const searchButton = document.querySelector('#book_search');

searchButton.addEventListener('click', function (event) {
    event.preventDefault();

    const query = document.querySelector('input[name="query"]').value;

    // AJAX request to fetch book data
    fetch(`/admin/books/book/search?query=${encodeURIComponent(query)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            books = data.items; // Fetch된 데이터를 전역 books 배열에 할당

            // Populate the modal with fetched book data
            const bookList = document.getElementById('bookList');
            bookList.innerHTML = ''; // Clear previous entries

            books.forEach(function (book, index) {
                const listItem = document.createElement('li');
                listItem.classList.add('list-group-item');
                listItem.innerHTML = `
                                    <div class="book-info">
                                        <img src="${book.image}" alt="${book.title}">
                                        <div class="details">
                                            <strong>${book.title}</strong> - ${book.author}, ${book.publisher}, ${book.pubdate} &nbsp;
                                            <a href="${book.link}" target="_blank">자세히 보기</a>
                                        </div>
                                        <div class="actions">
                                            <button type="button" class="btn btn-primary btn-sm" onclick="selectBook(${index})">선택</button>
                                        </div>
                                    </div>`;
                bookList.appendChild(listItem);
            });

            // Show the modal
            $('#bookModal').modal('show');
        })
        .catch(error => {
            console.error('Error fetching book data:', error);
        });
});

// Function to handle book selection
window.selectBook = function (index) {
    const selectedBook = books[index]; // 전역 books 배열에서 책 정보 가져오기

    // Update the panel content with selected book details
    const selectedBookInfo = document.getElementById('selectedBookInfo');
    selectedBookInfo.innerHTML = `
                                <h4><span id="book_title">${selectedBook.title}</span></h4>
                                <img src="${selectedBook.image}" alt="${selectedBook.title}" style="width: 100px;">
                                <p><strong>저자:</strong> <span id="author">${selectedBook.author}</span></p>
                                <p><strong>출판사:</strong> <span id="publisher">${selectedBook.publisher}</span></p>
                                <p><strong>출판일:</strong> <span id="pubdate">${selectedBook.pubdate}</span></p>
                                <p><strong>판매가:</strong> <span id="discount">${selectedBook.discount}</span>원</p>
                                <p><strong>ISBN:</strong> <span id="isbn">${selectedBook.isbn}</span></p>
                                <hr>
                                <p><span id="book_description">${selectedBook.description}</span></p>
                                <p><input type="hidden" id="book_link" value="${selectedBook.image}"></p> `;
    // Show the selected book panel
    const selectedBookPanel = document.getElementById('selectedBookPanel');
    selectedBookPanel.style.display = 'block';
};


// JavaScript 코드
document.addEventListener('DOMContentLoaded', function () {
    const Editor = toastui.Editor;
    const editor = new Editor({
        el: document.querySelector('#editor'),
        height: '500px',
        initialEditType: 'markdown',
        previewStyle: 'vertical'
    });

    // 게시글 저장
    async function savePost() {
        // 1. 콘텐츠 입력 유효성 검사
        const content = editor.getMarkdown().trim();


        if (content.length < 1) {
            alert('에디터 내용을 입력해 주세요.');
            throw new Error('editor content is required!');
        }

        // 2. url, parameter 세팅
        const url = '/admin/books';

        const stock = document.getElementById('inputStock')
        const category = document.getElementById('inputCategory')
        const statusSelect = document.getElementById('inputBookStatus')
        const isbn = document.getElementById('isbn');
        const title = document.getElementById('book_title');

        const author = document.getElementById('author');

        const description = document.getElementById('book_description');
        const bookDiscount = document.getElementById('inputDiscountPrice');
        const isPackaging = document.getElementsByName('inputPackagingStatus');
        const publisher = document.getElementById('publisher');
        const pubdate = document.getElementById('author');
        const price = document.getElementById('discount');
        const bookLink = document.getElementById('book_link');
        const tags = $("#tagSelect").val();


        if (!bookDiscount || bookDiscount.value === null || bookDiscount.value < 1000 || bookDiscount.value > 10000000) {
            alert('판매가는 1000원에서 10000000원 사이여야 합니다.');
            throw new Error('Invalid discount value');
        }

        if (!stock || stock.value==="" || stock.value < 0 || stock.value > 1000) {
            alert('재고 수량은 0개에서 1000개 사이여야 합니다.');
            throw new Error('Invalid stock value');
        }

        if(!title) {
            alert('책을 선택해주세요.');
            throw new Error('Invalid book ');
        }


        const params = {
            statusSelect: statusSelect.value,
            isbn: isbn.textContent,
            title: title.textContent,
            description:description.textContent,
            content: editor.getHTML(),
            author: author.textContent,
            publisher: publisher.textContent,
            pubdate: pubdate.textContent,
            stock:stock.value,
            price:price.textContent,
            bookDiscount:bookDiscount.value,
            isPackaging:isPackaging.value,
            bookLink: bookLink.value,
            tags: tags,
            category:category.value
        }

        // 3. API 호출
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(params),
            });
            const responseData = await response.json();

            alert("등록이 완료되었습니다! "+ JSON.stringify(params));
            location.href = '/admin/books/book/form';

        } catch (error) {
            console.error('저장 실패 : ', error);
        }
    }

    // 제출 버튼에 클릭 이벤트 리스너 추가
    const submitBtn = document.querySelector('#submitBtn');
    submitBtn.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작 방지
        savePost(); // savePost 함수 호출
    });
});
