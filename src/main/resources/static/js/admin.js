<!--전체 선택 로직 -->
function check_all(f)
{
    var chk = document.getElementsByName("chk[]");

    for (i=0; i<chk.length; i++)
        chk[i].checked = f.chkall.checked;
}

window.addEventListener('load', adjustSidebarHeight);
window.addEventListener('resize', adjustSidebarHeight);
function adjustSidebarHeight() {
    const content = document.querySelector('.wrap');
    const sidebar = document.querySelector('.nav1');

    // 요소들이 제대로 선택되었는지 확인
    if (content) {
        console.log('Content element found.');
    } else {
        console.error('Content element not found.');
    }

    if (sidebar) {
        console.log('Sidebar element found.');
    } else {
        console.error('Sidebar element not found.');
    }

    // if문 동작 여부 확인
    if (content && sidebar) {
        const contentScrollHeight = content.scrollHeight;
        const contentClientHeight = content.clientHeight;
        const contentHeight = Math.max(contentScrollHeight, contentClientHeight);
        const minHeight = 480;  // 원하는 최소 높이

        // 사이드바의 높이를 contentHeight와 minHeight 중 더 큰 값으로 설정
        const finalHeight = Math.max(contentHeight, minHeight);
        sidebar.style.height = finalHeight + 'px';

        // 디버깅용 콘솔 로그
        console.log(`Sidebar height set to: ${finalHeight}px`);
        console.log(`Content scroll height: ${contentScrollHeight}px`);
        console.log(`Content client height: ${contentClientHeight}px`);
        console.log(`Min height: ${minHeight}px`);
    } else {
        console.error('Content or sidebar element not found or invalid.');
    }
}