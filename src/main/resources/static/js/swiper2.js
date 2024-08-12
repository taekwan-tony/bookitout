//Swiper 메인 슬라이드
const swiper2 = new Swiper('.swiper2', {
    // Default parameters
    slidesPerView: 1,
    spaceBetween: 0,
    
    
    autoplay: {
      delay: 2500,
      disableOnInteraction: false,
    },
	
    // If we need pagination
    pagination: {
      el: '.swiper2-pagination',
    },

    // Navigation arrows
    navigation: {
      nextEl: '.swiper2-button-next',
      prevEl: '.swiper2-button-prev',
    }
});