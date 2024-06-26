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
function saveFormData() {
    const formData = {
        orderSummary: [...document.querySelectorAll('table tbody tr')].map(row => {
            return {
                name: row.querySelector('td:nth-child(1)').innerText,
                price: row.querySelector('td:nth-child(2)').innerText,
                quantity: row.querySelector('td:nth-child(3)').innerText,
                discount: row.querySelector('td:nth-child(4)').innerText,
                total: row.querySelector('td:nth-child(5)').innerText,
            };
        }),
        packaging: document.querySelector('select[name="packaging"]').value,
        coupon: document.getElementById('selectedCouponName').value,
        points: document.getElementById('pointsInput').value,
        deliveryDate: [...document.querySelectorAll('.delivery-date-btn')].find(btn => btn.classList.contains('selected')).innerText,
        senderInfo: {
            name: document.getElementById('sender_name').value,
            phone: document.getElementById('sender_phone').value
        },
        recipientInfo: {
            name: document.getElementById('name').value,
            postcode: document.getElementById('sample4_postcode').value,
            roadAddress: document.getElementById('sample4_roadAddress').value,
            jibunAddress: document.getElementById('sample4_jibunAddress').value,
            detailAddress: document.getElementById('sample4_detailAddress').value,
            extraAddress: document.getElementById('sample4_extraAddress').value,
            phone: document.getElementById('phone').value,
            email: document.getElementById('email').value
        }
    };
    sessionStorage.setItem('orderFormData', JSON.stringify(formData));
}

function loadFormData() {
    const formData = JSON.parse(sessionStorage.getItem('orderFormData'));
    if (!formData) return;

    formData.orderSummary.forEach((item, index) => {
        const row = document.querySelector(`table tbody tr:nth-child(${index + 1})`);
        if (row) {
            row.querySelector('td:nth-child(1)').innerText = item.name;
            row.querySelector('td:nth-child(2)').innerText = item.price;
            row.querySelector('td:nth-child(3)').innerText = item.quantity;
            row.querySelector('td:nth-child(4)').innerText = item.discount;
            row.querySelector('td:nth-child(5)').innerText = item.total;
        }
    });

    document.querySelector('select[name="packaging"]').value = formData.packaging;
    document.getElementById('selectedCouponName').value = formData.coupon;
    document.getElementById('pointsInput').value = formData.points;

    document.querySelectorAll('.delivery-date-btn').forEach(btn => {
        if (btn.innerText === formData.deliveryDate) {
            btn.classList.add('selected');
        } else {
            btn.classList.remove('selected');
        }
    });

    document.getElementById('sender_name').value = formData.senderInfo.name;
    document.getElementById('sender_phone').value = formData.senderInfo.phone;

    document.getElementById('name').value = formData.recipientInfo.name;
    document.getElementById('sample4_postcode').value = formData.recipientInfo.postcode;
    document.getElementById('sample4_roadAddress').value = formData.recipientInfo.roadAddress;
    document.getElementById('sample4_jibunAddress').value = formData.recipientInfo.jibunAddress;
    document.getElementById('sample4_detailAddress').value = formData.recipientInfo.detailAddress;
    document.getElementById('sample4_extraAddress').value = formData.recipientInfo.extraAddress;
    document.getElementById('phone').value = formData.recipientInfo.phone;
    document.getElementById('email').value = formData.recipientInfo.email;
}

window.addEventListener('beforeunload', saveFormData);
window.addEventListener('DOMContentLoaded', loadFormData);

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
    $('#toss-method').modal('show');
}
