document.addEventListener("DOMContentLoaded", function() {
    // 가격 정보를 포함하는 요소 선택
    const priceElement = document.querySelector("#price");
    const cntInput = document.querySelector("#count");
    const totalElement = document.querySelector("#total");

    // 가격을 숫자로 변환하여 반환
    const getPrice = () => {
        const priceText = priceElement.textContent; // '19800원' 형태의 텍스트
        return parseFloat(priceText.replace(/[^0-9.]/g, '')); // '원' 제거 후 숫자만 추출
    };

    // 수량을 정수로 변환
    const getCount = () => parseInt(cntInput.value, 10);

    // 총 금액 업데이트 함수
    const updateTotal = () => {
        const price = getPrice();
        const count = getCount();
        const result = price * count;
        totalElement.innerHTML = `${result.toLocaleString()}<span>원</span>`;
    };

    // 수량 감소 함수
    window.minus = function() {
        let count = getCount();
        if(count == 1){
            alert("최소 주문 수량은 1개 입니다.");
        }else{
            cntInput.value = count - 1;
            updateTotal();
        }
    };

    // 수량 증가 함수
    window.plus = function() {
        let count = getCount();
        if(count == 10){
            alert("최대 주문 수량은 10개 입니다.");
        }else{            
            cntInput.value = count + 1;
            updateTotal();
        }
    };

    // 초기 총 금액 설정
    updateTotal();
});

// 숫자 천단위 콤마 찍기
// 숫자 포맷팅 함수
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 상품 가격 천단위 콤마
$(document).ready(function() {
    var price = $("#price").text(); // 예: 상품 가격
    var formattedAmount = numberWithCommas(price);
    $('#price').text(formattedAmount); // #price는 표시할 요소의 ID
});

// 총 상품 금액 천단위 콤마
$(document).ready(function() {
    var total = $("#total").text(); // 예: 총 상품 금액
    var formattedAmount = numberWithCommas(total);
    $('#total').text(formattedAmount); // #total은 표시할 요소의 ID
});

/* 구매 수량 변경되면 가격 증가 */
// 수량 input 선택
/* const cntInput = document.querySelector("#count");
cntInput.addEventListener("change", function(){
    console.log("function 실행");
    // 가격 Tag 안에 텍스트 읽어오기
    //const price = document.querySelector("#price").innerText;
    const price = parseFloat(priceElement.innerText); // 문자열을 숫자로 변환
    // 수량 input 값 읽어오기
    //const cnt = document.querySelector("#count").value;
    const cnt = parseInt(cntInput.value, 10); // 문자열을 정수로 변환
    
    // 수량 * 가격
    const result = price * cnt;
    // 총 금액 span에 result 값 대입
    // document.querySelector("#total").innerText = result;
    document.querySelector("#total").innerText = result.toFixed(2); // 결과를 소수점 두 자리로 포맷
}); */

/*
$(".countPM > button").on("change", function(){
    let price = $(".price").text();
    let count = $("#count").text();

    let total = $(".total");
    totalPrice = price * count;
    total.text(totalPrice);
});*/

/* 
$("#input1").on("change", function(){
    // change : focusin할때 input의 value와 focusout할때 input의 value가 다르면 동작
    console.log("input값 변경", $(this).val());
});
*/

