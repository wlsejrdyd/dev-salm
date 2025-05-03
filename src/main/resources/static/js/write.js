// write.js - 글쓰기 입력 유효성 검사

document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('.write-form');
  const titleInput = document.getElementById('title');
  const contentInput = document.getElementById('content');

  form.addEventListener('submit', e => {
    if (titleInput.value.trim() === '' || contentInput.value.trim() === '') {
      e.preventDefault();
      alert('제목과 내용을 모두 입력해 주세요.');
    }
  });
});
