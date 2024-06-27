//도로명 주소 JS
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample4_postcode').value = data.zonecode;
            document.getElementById("sample4_roadAddress").value = roadAddr;
            document.getElementById("sample4_jibunAddress").value = data.jibunAddress;

            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if (roadAddr !== '') {
                document.getElementById("sample4_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if (data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if (data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
        }
    }).open();
}


// 쿠폰 모달 JS
function openCoupon() {
    $('#couponModal').modal('show');
}

function selectCoupon(id, name) {
    document.getElementById('selectedCouponName').value = name;
    document.getElementById('selectedCouponId').value = id;
    $('#couponModal').modal('hide');
}

function applyCoupon() {
    alert("쿠폰이 적용되었습니다!");
}


// 페이지를 벗어날때 주문 정보를 전송한다.
window.onbeforeunload = function () {

}

function selectDeliveryDate(element) {
    document.querySelectorAll('.delivery-date-btn').forEach(btn => btn.classList.remove('selected'));
    element.classList.add('selected');
}


// 포인트 등록 JS
function applyPoints() {
    const currentPoints = parseInt($('#currentPoints').text().replace('포인트', '').replace(',', ''), 10);
    const pointsInput = parseInt($('#pointsInput').val(), 10);

    if (pointsInput > currentPoints) {
        alert("사용할 포인트는 현재 포인트보다 많을 수 없습니다.");
        return;
    }

    alert("포인트가 적용되었습니다!");
    // 실제 적용 로직 필요
    // 예: 포인트 금액 업데이트, 최종 결제 금액 업데이트
    $('#pointsUsed').text(pointsInput + "원"); // 입력된 포인트 사용
    updateFinalAmount();
}


// 그 외
function updatePackagingPrice(selectElement) {
    const price = parseInt($(selectElement).find('option:selected').data('price'), 10);
    $('#additionalAmount').text(price + "원");
    updateFinalAmount();
}

function updateFinalAmount() {
    const totalAmount = parseInt($('#totalAmount').text().replace('원', '').replace(',', ''), 10);
    const additionalAmount = parseInt($('#additionalAmount').text().replace('원', '').replace(',', ''), 10);
    const discountAmount = parseInt($('#discountAmount').text().replace('원', '').replace(',', ''), 10);
    const pointsUsed = parseInt($('#pointsUsed').text().replace('원', '').replace(',', ''), 10);
    const shippingFee = parseInt($('#shippingFee').text().replace('원', '').replace(',', ''), 10);
    const finalAmount = totalAmount + additionalAmount - discountAmount - pointsUsed + shippingFee;
    $('#finalAmount').text(finalAmount.toLocaleString() + "원");
}

function selectDeliveryDate(element) {
    $('.delivery-date-btn').removeClass('selected');
    $(element).addClass('selected');
}


function processPayment() {
    // 폼 데이터 가져오기
    const formData = {
        senderName: document.getElementById('sender_name').value,
        senderPhone: document.getElementById('sender_phone').value,
        // receiver_name: document.getElementById('receiver_name').value
        // receiver_phone: document.getElementById('receiver_phone').value
    };


    alert("성공" + JSON.stringify(formData));

    // AJAX를 이용한 POST 요청 보내기
    const xhr = new XMLHttpRequest();
    const url = 'http://localhost:8080/orders/order/form';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {

                $('#toss-method').modal('show');
            } else {
                console.error('주문 정보 전송 중 오류가 발생했습니다.');
            }
        }
    };

    xhr.send(JSON.stringify(formData));
}
