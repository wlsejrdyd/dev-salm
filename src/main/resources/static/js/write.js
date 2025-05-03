document.addEventListener("DOMContentLoaded", () => {
  const imageInput = document.getElementById("images");
  const previewContainer = document.getElementById("image-preview");

  imageInput.addEventListener("change", () => {
    previewContainer.innerHTML = ""; // 기존 썸네일 초기화

    const files = imageInput.files;
    if (!files || files.length === 0) return;

    Array.from(files).forEach(file => {
      if (!file.type.startsWith("image/")) return;

      const reader = new FileReader();
      reader.onload = e => {
        const img = document.createElement("img");
        img.src = e.target.result;
        img.classList.add("thumbnail");
        previewContainer.appendChild(img);
      };
      reader.readAsDataURL(file);
    });
  });
});
