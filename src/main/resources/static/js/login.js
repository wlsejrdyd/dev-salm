document.addEventListener('DOMContentLoaded', function () {
  const idInput = document.querySelector('input[name="username"]');

  // 영문/숫자만 입력 제한
  idInput.addEventListener('input', function () {
    this.value = this.value.replace(/[^a-zA-Z0-9]/g, '');
  });

  // 키보드 안내 메시지 유지
  idInput.addEventListener('focus', function () {
    this.setAttribute('placeholder', '아이디 (영문/숫자만 입력)');
  });

  // 회원가입 성공 메시지 (redirect query check)
  const params = new URLSearchParams(window.location.search);
  if (params.get("success") === "true") {
    alert("🎉 회원가입을 축하합니다!");
    window.history.replaceState({}, document.title, location.pathname); // 쿼리 제거
  }
});
