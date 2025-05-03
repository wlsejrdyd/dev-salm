document.addEventListener("DOMContentLoaded", () => {
  const fileInput = document.getElementById("images");
  const thumbContainer = document.getElementById("thumb-container");
  const dropZone = document.getElementById("drop-zone");
  const form = document.querySelector(".write-form");
  const submitBtn = form.querySelector("button[type='submit']");
  const loader = document.getElementById("loader");

  const MAX_FILES = 10;

  // 기존 업로드 이벤트
  fileInput.addEventListener("change", handleFiles);

  // 드래그앤드롭 지원
  ["dragenter", "dragover"].forEach(event =>
    dropZone.addEventListener(event, e => {
      e.preventDefault();
      dropZone.classList.add("drag-over");
    })
  );

  ["dragleave", "drop"].forEach(event =>
    dropZone.addEventListener(event, () => {
      dropZone.classList.remove("drag-over");
    })
  );

  dropZone.addEventListener("drop", e => {
    e.preventDefault();
    const files = e.dataTransfer.files;
    fileInput.files = files;
    handleFiles();
  });

  function handleFiles() {
    const files = fileInput.files;

    if (files.length > MAX_FILES) {
      alert("이미지는 최대 10장까지만 첨부할 수 있습니다.");
      fileInput.value = "";
      thumbContainer.innerHTML = "";
      return;
    }

    thumbContainer.innerHTML = "";

    [...files].forEach((file, idx) => {
      const reader = new FileReader();
      reader.onload = (e) => {
        const wrapper = document.createElement("div");
        wrapper.className = "thumb-wrapper";

        const img = document.createElement("img");
        img.src = e.target.result;

        const btn = document.createElement("button");
        btn.innerHTML = "×";
        btn.type = "button";
        btn.onclick = () => removeFile(idx);

        wrapper.appendChild(img);
        wrapper.appendChild(btn);
        thumbContainer.appendChild(wrapper);
      };
      reader.readAsDataURL(file);
    });
  }

  form.addEventListener("submit", (e) => {
    if (!validateForm()) {
      e.preventDefault();
      return;
    }

    loader.style.display = "block";
    submitBtn.disabled = true;
  });
});

function validateForm() {
  const title = document.getElementById("title").value.trim();
  const content = document.getElementById("content").value.trim();
  const errorBox = document.getElementById("form-error");

  if (!title || !content) {
    errorBox.style.display = "block";
    return false;
  }

  errorBox.style.display = "none";
  return true;
}

function removeFile(index) {
  const dt = new DataTransfer();
  const input = document.getElementById("images");
  const { files } = input;

  [...files].forEach((file, i) => {
    if (i !== index) dt.items.add(file);
  });

  input.files = dt.files;
  input.dispatchEvent(new Event("change"));
}
