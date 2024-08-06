$(document).ready(function(){
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
    }
    
    function modalHide() {
        $modal.removeClass('active');
        $dimLayer.hide();
        console.log('close button click');
    }
});