// 탑버튼(버튼 클릭 시 맨 위로 이동)
const topBtn = document.querySelector("#top-btn");
topBtn.onclick = () => {
  window.scrollTo({ top: 0, behavior: "smooth" });  
}