document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('.write-form');
  const titleInput = document.getElementById('title');
  const contentInput = document.getElementById('content');
  const imageInput = document.getElementById('images');
  const previewContainer = document.getElementById('image-preview');

  form.addEventListener('submit', e => {
    if (titleInput.value.trim() === '' || contentInput.value.trim() === '') {
      e.preventDefault();
      alert('제목과 내용을 모두 입력해 주세요.');
    }
  });

  imageInput.addEventListener('change', function () {
    previewContainer.innerHTML = '';
    const files = Array.from(this.files).slice(0, 5); // 최대 5장

    files.forEach(file => {
      const reader = new FileReader();
      reader.onload = e => {
        const img = document.createElement('img');
        img.src = e.target.result;
        previewContainer.appendChild(img);
      };
      reader.readAsDataURL(file);
    });
  });
});
