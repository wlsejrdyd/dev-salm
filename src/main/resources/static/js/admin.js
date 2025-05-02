// admin.js - 삭제 버튼 클릭 시 경고창 (기능 구현 전용)

document.addEventListener('DOMContentLoaded', function () {
  const deleteButtons = document.querySelectorAll('.delete-btn');

  deleteButtons.forEach(btn => {
    btn.addEventListener('click', function () {
      const confirmed = confirm('정말 삭제하시겠습니까?');
      if (confirmed) {
        alert('삭제 기능은 아직 구현되지 않았습니다.');
      }
    });
  });
});
