document.addEventListener("DOMContentLoaded", function () {
  const dropZone = document.getElementById("drop-zone");
  const fileInput = document.getElementById("images");
  const thumbContainer = document.getElementById("thumb-container");
  const categoryInput = document.getElementById("category");
  const categoryButtons = document.querySelectorAll(".category-btn");
  const representativeInput = document.getElementById("representativeIndex");

  let selectedFiles = [];

  function handleFiles(files) {
    for (let file of files) {
      if (!file.type.startsWith("image/")) continue;
      if (selectedFiles.length >= 5) {
        alert("이미지는 최대 5장까지만 업로드 가능합니다.");
        break;
      }
      if (selectedFiles.some(f => f.name === file.name)) {
        alert(`"${file.name}" 파일은 이미 추가되었습니다.`);
        continue;
      }

      selectedFiles.push(file);
    }
    renderThumbnails();
    updateInputFiles();
  }

  function renderThumbnails() {
    thumbContainer.innerHTML = '';
    selectedFiles.forEach((file, index) => {
      const wrapper = document.createElement("div");
      wrapper.className = "thumb-wrapper";
      wrapper.setAttribute("draggable", true);
      wrapper.setAttribute("data-index", index);

      const img = document.createElement("img");
      img.className = "thumbnail"; // ✅ 썸네일 클래스 추가
      const reader = new FileReader();
      reader.onload = e => { img.src = e.target.result; };
      reader.readAsDataURL(file);

      const delBtn = document.createElement("button");
      delBtn.innerText = "×";
      delBtn.onclick = () => {
        selectedFiles.splice(index, 1);
        renderThumbnails();
        updateInputFiles();
      };

      const repBadge = document.createElement("div");
      repBadge.innerText = index === 0 ? "★ 대표" : "";
      repBadge.className = "rep-badge";

      wrapper.appendChild(img);
      wrapper.appendChild(repBadge);
      wrapper.appendChild(delBtn);
      thumbContainer.appendChild(wrapper);
    });

    representativeInput.value = 0;
    setupDragAndDrop();
  }

  function updateInputFiles() {
    const dt = new DataTransfer();
    selectedFiles.forEach(f => dt.items.add(f));
    fileInput.files = dt.files;
  }

  function setupDragAndDrop() {
    let dragSrcEl = null;

    const wrappers = thumbContainer.querySelectorAll(".thumb-wrapper");

    wrappers.forEach(wrapper => {
      wrapper.addEventListener("dragstart", function (e) {
        dragSrcEl = this;
        e.dataTransfer.effectAllowed = "move";
        e.dataTransfer.setData("text/html", this.innerHTML);
      });

      wrapper.addEventListener("dragover", function (e) {
        e.preventDefault();
        return false;
      });

      wrapper.addEventListener("drop", function (e) {
        e.stopPropagation();
        if (dragSrcEl !== this) {
          const srcIndex = parseInt(dragSrcEl.getAttribute("data-index"));
          const targetIndex = parseInt(this.getAttribute("data-index"));
          const movedFile = selectedFiles.splice(srcIndex, 1)[0];
          selectedFiles.splice(targetIndex, 0, movedFile);
          renderThumbnails();
          updateInputFiles();
        }
        return false;
      });
    });
  }

  dropZone.addEventListener("click", () => fileInput.click());
  dropZone.addEventListener("dragover", e => {
    e.preventDefault();
    dropZone.classList.add("drag-over");
  });
  dropZone.addEventListener("dragleave", () => dropZone.classList.remove("drag-over"));
  dropZone.addEventListener("drop", e => {
    e.preventDefault();
    dropZone.classList.remove("drag-over");
    handleFiles(e.dataTransfer.files);
  });
  fileInput.addEventListener("change", () => handleFiles(fileInput.files));

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
