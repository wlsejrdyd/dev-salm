// dashboard.js

document.addEventListener("DOMContentLoaded", () => {
  const items = document.querySelectorAll(".item");

  items.forEach((item) => {
    item.addEventListener("mouseenter", () => {
      item.style.backgroundColor = "#fdf3e9";
    });

    item.addEventListener("mouseleave", () => {
      item.style.backgroundColor = "#fff";
    });
  });

  const icons = document.querySelectorAll(".icon");
  icons.forEach((icon) => {
    icon.addEventListener("click", () => {
      alert("기능은 아직 준비 중입니다.");
    });
  });
});
