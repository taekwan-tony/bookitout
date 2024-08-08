$(document).ready(function(){
    // 모달 팝업 오픈&클로즈
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

// 구매 수량 증감
function minus(){
    const count = document.querySelector("#count");
    const currentCount = count.value;//value속성으로 읽어오면 문자열타입
    if(currentCount == 1){
        alert("최소 주문 수량은 1개 입니다.");
    }else{
        count.value = Number(currentCount) - 1;
    }
}

function plus(){
    const count = document.querySelector("#count");
    const currentCount = count.value;//value속성으로 읽어오면 문자열타입
    if(currentCount == 10){
        alert("최대 주문 수량은 10개 입니다.");
    }else{
        count.value = Number(currentCount) + 1;
    }
}

// 가격 천단위 콤마 찍기
// let price = document.querySelector(".price").innerText();
// let result = price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
// console.log(result);

// const price = $(".price").text();
// console.log(price.toLocaleString());
// 123,456,789
// `toLocaleString()`는 인자가 없이 사용을하면 사용자의 PC의 환경 로컬을 따라서 표시를 해줍니다.

// console.log(num.toLocaleString('ko-KR'))
// 123,456,789
// 로컬을 인자로 넣으면 해당 로컬의 포맷으로 표시해줍니다.

// const number = 123456789.123456
// console.log(number.toLocaleString('ko-KR', { maximumFractionDigits: 4 }))
// 123,456,789.1234
// option 인자로 전달되는 객체 안에는 maximumFractionDigits 값이 4로 지정되어 있어서 소숫점 4자리까지 표시