document.addEventListener('DOMContentLoaded', function () {
  const idInput = document.querySelector('input[name="username"]');
  const emailInput = document.querySelector('input[name="email"]');
  const pwInput = document.querySelector('input[name="password"]');
  const phoneInput = document.querySelector('input[name="phone"]');
  const birthInput = document.querySelector('input[name="birth"]');
  const pwMsg = document.getElementById('pw-msg');
  const idMsg = document.getElementById('id-msg');
  const emailMsg = document.getElementById('email-msg');

  // 아이디: 영문/숫자만
  idInput.addEventListener('input', function () {
    this.value = this.value.replace(/[^a-zA-Z0-9]/g, '');
    checkDuplication('/check-username', this.value, idMsg);
  });

  // 이메일: 중복 검사
  emailInput.addEventListener('blur', function () {
    if (this.value.includes('@')) {
      checkDuplication('/check-email', this.value, emailMsg);
    }
  });

  // 비밀번호 강도 검사
  pwInput.addEventListener('input', function () {
    const val = this.value;
    const strong = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+]).{8,}$/;
    if (strong.test(val)) {
      pwMsg.textContent = '사용 가능한 비밀번호입니다.';
      pwMsg.style.color = 'green';
    } else {
      pwMsg.textContent = '8자 이상, 영문+숫자+특수문자를 포함해야 합니다.';
      pwMsg.style.color = 'red';
    }
  });

  // 전화번호: 자동 하이픈
  phoneInput.addEventListener('input', function () {
    let val = this.value.replace(/[^0-9]/g, '');
    if (val.length < 4) this.value = val;
    else if (val.length < 8) this.value = val.slice(0, 3) + '-' + val.slice(3);
    else this.value = val.slice(0, 3) + '-' + val.slice(3, 7) + '-' + val.slice(7, 11);
  });

  // 생년월일: YYYY-MM-DD 자동 하이픈
  birthInput.addEventListener('input', function () {
    let val = this.value.replace(/[^0-9]/g, '').slice(0, 8);
    if (val.length < 5) this.value = val;
    else if (val.length < 7) this.value = val.slice(0, 4) + '-' + val.slice(4);
    else this.value = val.slice(0, 4) + '-' + val.slice(4, 6) + '-' + val.slice(6);
  });

  // 공통 중복 체크
  function checkDuplication(api, value, msgEl) {
    fetch(`${api}?value=${encodeURIComponent(value)}`)
      .then(res => res.json())
      .then(data => {
        if (data.duplicate === false || data === true) {
          msgEl.textContent = '사용 가능합니다.';
          msgEl.style.color = 'green';
        } else {
          msgEl.textContent = '이미 사용 중입니다.';
          msgEl.style.color = 'red';
        }
      });
  }
});
