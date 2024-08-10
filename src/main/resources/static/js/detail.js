// 모달 팝업 오픈&클로즈
$(document).ready(function(){
    var $openBtn = $('.open-btn'),
        $closeBtn = $('.close-btn'),
        $modal = $('.modal-container'),
        $dimLayer = $('.dim-layer');

    // console.log($openBtn);
    
    $openBtn.click(function () {
        modalShow();
    });
    
    $closeBtn.click(function () {
        modalHide();
    });
    
    $dimLayer.click(function () {
        modalHide();
    });

    function modalShow() {
        $modal.addClass('active');
        $dimLayer.show();
        // console.log('show button click');
        
        const bookNo = $("#bookNo").text();
        // console.log(bookNo);
        $("#modal-data-wrap").empty();
        $.ajax({
            url : "/book/ajax1",
            type : "get",
            data : {bookNo : bookNo},
            dataType : "json",
            success : function(data){
                // console.log(data);
            // console.log(typeof data);
                // console.log(data.length);
                for(let i = 0; i < data.length; i++){
                    const h2 = $("<h2></h2>");
                    h2.append(data[i].bookName);
                    //h2.text();
                    
                    const table = $("<table class='center_inventory'>");
                    
                    const thead = $("<thead>");
                    const tr1 = $("<tr>");
                    
                    const th1 = $("<th style='width: 20%;'>");//<th style="width: 20%;"></th>
                    th1.text("지점명");//<th style="width: 20%;">지점명</th>
                    tr1.append(th1);
                    
                    const th2 = $("<th style='width: 65%;'>");
                    th2.text("주소");
                    tr1.append(th2);
                    
                    const th3 = $("<th style='width: 15%;'>");
                    th3.text("재고 수량");
                    tr1.append(th3);
                    
                    thead.append(tr1);
                    /*
                    <th style="width: 20%;">지점명</th>
                    <th style="width: 65%;">주소</th>
                    <th style="width: 15%;">재고 수량</th>
                    */
                    
                    const tbody = $("<tbody>");
                    const tr2 = $("<tr>");//<tr></tr>
                    
                    const td1 = $("<td>");//<td></td>
                    td1.append(data[i].adminName);
                    tr2.append(td1);
                    
                    const td2 = $("<td>");
                    td2.append(data[i].adminAddr);
                    tr2.append(td2);
                    
                    const td3 = $("<td>");
                    td3.append(data[i].centerBookCount);
                    tr2.append(td3);
                    
                    tbody.append(tr2);
                    
                    /*
                    <td th:text="${center.adminName}"></td>
                    <td th:text="${center.adminAddr}"></td>
                    <td th:text="${center.centerBookCount}"></td>
                    */
                    
                    table.append(thead);
                    table.append(tbody);
                    
                    $("#modal-data-wrap").append(table);
                }
            },
            error : function(){
                console.log("에러");
            }
        });
    }
    
    function modalHide() {
        $modal.removeClass('active');
        $dimLayer.hide();
        // console.log('close button click');
    }
});

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

