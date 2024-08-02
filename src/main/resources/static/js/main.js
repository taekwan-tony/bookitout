//Swiper 메인 슬라이드
const swiper = new Swiper('.swiper', {
    // Default parameters
    slidesPerView: 1,
    spaceBetween: 0,
    
    autoplay: {
      delay: 2500,
      disableOnInteraction: false,
    },

    // If we need pagination
    pagination: {
      el: '.swiper-pagination',
    },

    // Navigation arrows
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    }
});

// 탑버튼(버튼 클릭 시 맨 위로 이동)
const topBtn = document.querySelector("#top-btn");
topBtn.onclick = () => {
  window.scrollTo({ top: 0, behavior: "smooth" });  
}