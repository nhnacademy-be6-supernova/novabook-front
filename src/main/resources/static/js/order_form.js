// 페이지가 로드 되면 총 금액을 업데이트 한다.
document.addEventListener('DOMContentLoaded', function () {
    const totalDiscount = calculateTotalDiscount();
    $('#totalAmount').text(totalDiscount + "원");
    updateFinalAmount();
});

// 내 주소지 선택 시 내용을 채움
function selectAddress(button) {

    var streetAddresses = button.getAttribute('data-street-address');
    var detailAddress = button.getAttribute('data-detail-address');
    var postcode = button.getAttribute('data-zipcode');

    document.getElementById('sample6_address').value = streetAddresses;
    document.getElementById('sample6_detailAddress').value = detailAddress;
    document.getElementById('sample6_postcode').value = postcode;

    $('#myAddressModal').modal('hide'); // 모달 닫기
}

//도로명 주소 JS
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;
            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

// 쿠폰일 7/7 (일)에서 2024-07-07 로 포맷팅
function convertToDateObject(dateString) {
    let datePart = dateString.split(' ')[0];
    let [month, day] = datePart.split('/').map(Number);
    let year = new Date().getFullYear();
    let dateObject = new Date(year, month - 1, day);
    return dateObject.toISOString().split('T')[0];
}

// 쿠폰 모달 JS
function openCoupon() {
    $('#couponModal').modal('show');
}

// 내 주소지 모달
function openMyaddress() {
    $('#myAddressModal').modal('show');
}

// 쿠폰 선택 시 할인 가격 업데이트
function selectCoupon(button) {
    const discount = button.getAttribute('data-discount');
    let totalDiscount = 0;

    if (discount.endsWith('%')) {
        const discountPercentage = parseFloat(discount.slice(0, -1));
        totalDiscount = calculateTotalDiscount() * (discountPercentage / 100);
    } else if (discount.endsWith('원')) {
        totalDiscount = parseFloat(discount.slice(0, -1));
    }

    $('#discountAmount').text(totalDiscount + "원");
    updateFinalAmount();

    const couponId = button.getAttribute('data-id');
    const couponName = button.getAttribute('data-name');

    document.getElementById('selectedCouponName').value = couponName;
    document.getElementById('selectedCouponId').value = couponId;
    $('#couponModal').modal('hide');
}

// 책에 총 가격 계산
function calculateTotalDiscount() {
    let totalDiscount = 0;
    const itemTotalElements = document.querySelectorAll('[name="totalPrice"]');

    itemTotalElements.forEach(function (element) {
        const itemTotalText = element.innerText.replace(/[^0-9]/g, '');
        const itemTotal = parseInt(itemTotalText);
        totalDiscount += itemTotal;
    });

    return totalDiscount;
}

// 포인트 등록 JS
function applyPoints() {

    const currentPoints = parseInt($('#currentPoints').text().replace('포인트', '').replace(',', ''), 10);
    const pointsInput = parseInt($('#pointsInput').val(), 10);

    if (!pointsInput) {
        alert("포인트 값을 넣어주세요!")
        return;
    }
    if (pointsInput < 0) {
        alert("포인트는 0보다 작을 수 없습니다.")
        return;
    }
    if (pointsInput > currentPoints) {
        alert("사용할 포인트는 현재 포인트보다 많을 수 없습니다.");
        return;
    }

    const tempFinalAmount = parseInt($('#finalAmount').text().replace('원', '').replace(',', ''), 10);
    // 실제 적용 로직 필요
    // 예: 포인트 금액 업데이트, 최종 결제 금액 업데이트
    $('#pointsUsed').text(pointsInput + "원"); // 입력된 포인트 사용
    updateFinalAmount();

    const afterFinalAmount = parseInt($('#finalAmount').text().replace('원', '').replace(',', ''), 10);

    if (0 > afterFinalAmount) {
        alert("사용할 포인트는 결제할 금액보다 클 수 없습니다.");
        $('#finalAmount').text(tempFinalAmount.toLocaleString() + "원");
        return;
    }

    alert("포인트가 적용되었습니다!");


    updateFinalAmount();
}

// 포장지 가격 업데이트
function updatePackagingPrice(selectElement) {
    const price = parseInt($(selectElement).find('option:selected').data('price'), 10);
    $('#additionalAmount').text(price + "원");
    updateFinalAmount();
}


function isValidEmail(email) {
    // Basic email validation regex
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function isValidPhoneNumber(phone) {
    // Basic phone number validation regex (allows numbers, spaces, hyphens, parentheses)
    const phoneRegex = /^[0-9\-\+\(\) ]+$/;
    return phoneRegex.test(phone);
}

// 결제 정보 업데이트
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
    selectedDate = $(element).data('date');
    $(".delivery-date-btn").removeClass('selected');
    $(element).addClass('selected');
}


let globalItemSize;
let globalFirstItemName;
var selectedDate;

function processPayment() {
    var items = [];
    var itemsNames = [];

    $(".item-row").each(function () {
        var itemName = $(this).find(".item-name").text();
        var itemId = $(this).find(".item-id").val();
        var itemQuantity = $(this).find(".item-quantity").text();
        items.push({id: itemId, quantity: parseInt(itemQuantity)});
        itemsNames.push(itemName);
    });

    globalItemSize = items.length;
    globalFirstItemName = itemsNames[0];

    // 약관
    var serviceTermsChecked = document.getElementById('agreeServiceTerms').checked;
    var privacyPolicyChecked = document.getElementById('agreePrivacyPolicy').checked;

    var wrapPaperId = $('#wrap_paper').val();
    var couponId = $('#selectedCouponId').val();
    var pointAmount = $('#pointsInput').val();
    var currentPoints = parseInt($('#currentPoints').text().replace('포인트', '').replace(',', ''));

    try {
        if (!items.length) {
            alert("상품을 선택해주세요.");
            throw new Error("No items selected");
        }

        if (parseInt(pointAmount) > currentPoints) {
            alert("사용할 포인트가 현재 포인트보다 많습니다.");
            throw new Error("Insufficient points");
        }

        // 약관동의 체크
        if (!serviceTermsChecked || !privacyPolicyChecked) {
            alert('모든 약관에 동의하셔야 합니다.');
            throw new Error("Not check Service policy");
        }

        // 날짜 포멧 변경
        let formattedDate = convertToDateObject(selectedDate);

        var deliveryId = $("#deliveryFeeId").val();
        var senderName = $("#sender_name").val();
        var senderPhone = $("#sender_phone").val();
        var receiverName = $("#receiver_name").val();
        var receiverPhone = $("#receiver_phone").val();
        var receiverEmail = $("#receiver_email").val();
        var zipCode = $("#sample6_postcode").val();
        var streetAddresses = $("#sample6_address").val() + " " + $("#sample6_extraAddress").val();
        var detailAddress = $("#sample6_detailAddress").val();

        // Check required fields in nested objects
        if (!senderName || !senderPhone || !receiverName || !receiverPhone || !receiverEmail || !zipCode || !streetAddresses || !detailAddress || !deliveryId) {
            alert("모든 필드를 채워주세요.");
            throw new Error("Missing nested required fields");
        }

        // Validate email format
        if (!isValidEmail(receiverEmail)) {
            alert("유효한 이메일 주소를 입력해주세요.");
            throw new Error("Invalid email format");
        }

        // Validate phone number format
        if (!isValidPhoneNumber(senderPhone) || !isValidPhoneNumber(receiverPhone)) {
            alert("유효한 전화번호를 입력해주세요.");
            throw new Error("Invalid phone number format");
        }

        var memberID = $('#login_member_id').val();

        if (!memberID) {
            memberID = null;
        }

        if (!couponId) {
            couponId = null;
        }
        if (!pointAmount) {
            pointAmount = 0;
        }


        // 폼 내용
        var formData = {
            memberId: memberID,
            books: items,
            wrappingPaperId: wrapPaperId,
            couponId: couponId,
            usePointAmount: pointAmount,
            deliveryDate: formattedDate,
            deliveryId: deliveryId,
            orderSenderInfo: {
                name: senderName,
                phone: senderPhone
            },
            orderReceiverInfo: {
                name: receiverName,
                phone: receiverPhone,
                email: receiverEmail,
                orderAddressInfo: {
                    zipCode: zipCode,
                    streetAddresses: streetAddresses,
                    detailAddress: detailAddress
                }
            }
        };

        alert("보내는 내용" + JSON.stringify(formData));
        console.log(JSON.stringify(formData));
        sendOrderData(formData);

    } catch (error) {
        console.error(error.message);
    }
}

function sendOrderData(formData) {
    const xhr = new XMLHttpRequest();
    const url = '/orders/order/form/submit';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 201 || xhr.status === 200) {
                const responseCode = xhr.responseText;
                requestPayment(responseCode);
            } else {
                console.log(xhr.status);
                console.error('주문 정보 전송 중 오류가 발생했습니다.');
            }
        }
    };

    xhr.send(JSON.stringify(formData));
}


const clientKey = "test_ck_Z1aOwX7K8mzlogY57AQj3yQxzvNP";
const customerKey = "3z5vQX5-gKl9yu91pPpr-";
const tossPayments = TossPayments(clientKey);
const payment = tossPayments.payment({customerKey});


async function requestPayment(orderCode) {
    var totalPrice = parseInt($('#finalAmount').text().replace('원', '').replace(',', ''), 10);
    var orderName = globalFirstItemName;
    var memberID = $('#login_member_id').val();
    if (globalItemSize > 1) {
        orderName += " 외 " + (globalItemSize - 1) + "건";
    }

    // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
    // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
    await payment.requestPayment({
        method: "CARD", // 카드 결제
        amount: {
            currency: "KRW",
            value: totalPrice,
        },
        orderId: orderCode, // 고유 주문번호
        orderName: orderName,
        successUrl: 'https://www.novabook.store/orders/order/toss/success?memberId=' + memberID, // 결제 성공 시 이동할 페이지(이 주소는 예시입니다. 상점에서 직접 만들어주세요.)
        failUrl: 'https://www.novabook.store/orders/order/toss/fail',
    });
}