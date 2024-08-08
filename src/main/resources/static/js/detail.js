$(document).ready(function(){
// 모달 팝업 오픈&클로즈
    var $openBtn = $('.open-btn'),
        $closeBtn = $('.close-btn'),
        $modal = $('.modal-container'),
        $dimLayer = $('.dim-layer');

    console.log($openBtn);
    
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
        console.log('show button click');
        
        //const bookNo = $("")
        console.log(window.location.href);
        $("#modal-data-wrap").empty();
		$.ajax({
			url : "/book/ajax1",
			type : "get",
			data : {bookNo : bookNo},
			dataType : "json",
			success : function(data){
				console.log("서버 요청 성공");
				console.log(data);
				for(let i = 0; i < data.length; i++){
					const h2 = $("<h2></h2>");
					h2.text();
					
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
        console.log('close button click');
    }
    
	// 재고 위치 조회
	$(".open-btn").on("click", function(){
		//const bookNo = $("#").val();
		
	});
});

// 장바구니 수량 증감
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