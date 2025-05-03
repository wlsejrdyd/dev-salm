document.addEventListener("DOMContentLoaded", function () {
  const dropZone = document.getElementById("drop-zone");
  const fileInput = document.getElementById("images");
  const thumbContainer = document.getElementById("thumb-container");
  const categoryInput = document.getElementById("category");
  const categoryButtons = document.querySelectorAll(".category-btn");

  const selectedFiles = new Map();

  function handleFiles(files) {
    for (let file of files) {
      if (!file.type.startsWith("image/")) continue;

      if (selectedFiles.size >= 5) {
        alert("이미지는 최대 5장까지만 업로드 가능합니다.");
        break;
      }

      if (selectedFiles.has(file.name)) {
        alert(`"${file.name}" 파일은 이미 추가되었습니다.`);
        continue;
      }

      selectedFiles.set(file.name, file);

      const reader = new FileReader();
      reader.onload = function (e) {
        const wrapper = document.createElement("div");
        wrapper.className = "thumb-wrapper";

        const img = document.createElement("img");
        img.src = e.target.result;

        const btn = document.createElement("button");
        btn.innerText = "×";
        btn.onclick = function () {
          selectedFiles.delete(file.name);
          wrapper.remove();
          updateInputFiles();
        };

        wrapper.appendChild(img);
        wrapper.appendChild(btn);
        thumbContainer.appendChild(wrapper);
      };
      reader.readAsDataURL(file);
    }

    updateInputFiles();
  }

  function updateInputFiles() {
    const dataTransfer = new DataTransfer();
    for (let file of selectedFiles.values()) {
      dataTransfer.items.add(file);
    }
    fileInput.files = dataTransfer.files;
  }

  dropZone.addEventListener("click", () => fileInput.click());

  dropZone.addEventListener("dragover", function (e) {
    e.preventDefault();
    dropZone.classList.add("drag-over");
  });

  dropZone.addEventListener("dragleave", function () {
    dropZone.classList.remove("drag-over");
  });

  dropZone.addEventListener("drop", function (e) {
    e.preventDefault();
    dropZone.classList.remove("drag-over");
    handleFiles(e.dataTransfer.files);
  });

  fileInput.addEventListener("change", function () {
    handleFiles(fileInput.files);
  });

  categoryButtons.forEach(button => {
    button.addEventListener("click", function () {
      categoryButtons.forEach(btn => btn.classList.remove("active"));
      this.classList.add("active");
      categoryInput.value = this.getAttribute("data-value");
    });
  });
});

function validateForm() {
  const title = document.getElementById("title").value.trim();
  const content = document.getElementById("content").value.trim();
  const category = document.getElementById("category").value.trim();

  if (!title || !content || !category) {
    document.getElementById("form-error").style.display = "block";
    return false;
  }
  return true;
}
