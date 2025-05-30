let postPage = 0;
let postLoading = false;
let postEnd = false;
let currentCategory = '';

// 일반 게시글 무한 스크롤 로딩
async function loadPosts() {
  if (postLoading || postEnd) return;
  postLoading = true;

  try {
    const apiUrl = currentCategory
      ? `/api/posts/category?category=${encodeURIComponent(currentCategory)}&page=${postPage}`
      : `/api/posts?page=${postPage}`;

    const res = await fetch(apiUrl);
    const json = await res.json();

    if (!json.content || json.content.length === 0) {
      postEnd = true;
      return;
    }

    const container = document.getElementById('post-list');
    json.content.forEach(post => {
      const el = document.createElement('div');
      el.className = 'post-card';
      el.innerHTML = `
        <a href="/post/${post.id}" class="card-link">
          <h3>${post.title}</h3>
          <p>${post.content.length > 100 ? post.content.substring(0, 100) + '...' : post.content}</p>
          <div class="meta">${post.authorName || '익명'} · ${post.createdAt.replace('T', ' ').substring(0, 16)}</div>
        </a>
      `;
      container.appendChild(el);
    });

    postPage++;
  } catch (e) {
    console.error('일반 게시글 로딩 실패:', e);
  } finally {
    postLoading = false;
  }
}

// 추천 슬라이드 초기 로딩 + 버튼 설정
async function loadRecommendSlider() {
  try {
    const res = await fetch('/api/posts/recommended');
    const json = await res.json();

    const slider = document.getElementById('recommend-slider');
    json.forEach(post => {
      const el = document.createElement('div');
      el.className = 'slider-item';
      el.innerHTML = `
        <a href="/post/${post.id}">
          <img src="/uploads/${post.thumbnail || 'default.jpg'}" alt="썸네일" class="recommend-thumb" />
          <div class="recommend-text">
            <h4>${post.title}</h4>
            <p>${post.content.length > 50 ? post.content.substring(0, 50) + '...' : post.content}</p>
          </div>
        </a>
      `;
      slider.appendChild(el);
    });

    setupSliderControls();
    startSlider();
  } catch (e) {
    console.error('추천 슬라이더 로딩 실패:', e);
  }
}

// 슬라이더 자동 전환
function startSlider() {
  const slider = document.getElementById('recommend-slider');
  setInterval(() => {
    slider.scrollBy({ left: 260, behavior: 'smooth' });
  }, 4000);
}

// 좌우 버튼 클릭 기능
function setupSliderControls() {
  const slider = document.getElementById('recommend-slider');

  const leftBtn = document.createElement('button');
  leftBtn.className = 'slider-btn left';
  leftBtn.innerText = '<';
  leftBtn.onclick = () => slider.scrollBy({ left: -260, behavior: 'smooth' });

  const rightBtn = document.createElement('button');
  rightBtn.className = 'slider-btn right';
  rightBtn.innerText = '>';
  rightBtn.onclick = () => slider.scrollBy({ left: 260, behavior: 'smooth' });

  slider.parentElement.appendChild(leftBtn);
  slider.parentElement.appendChild(rightBtn);
}

// 카테고리 버튼 클릭 처리
function setupCategoryButtons() {
  const buttons = document.querySelectorAll('.category-btn');
  buttons.forEach(btn => {
    btn.addEventListener('click', () => {
      buttons.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');

      currentCategory = btn.dataset.value;
      resetAndLoadPosts();
    });
  });
}

// 프로필 드롭다운 클릭 토글
function setupDropdownToggle() {
  const icon = document.querySelector('.profile-icon');
  const menu = document.querySelector('.dropdown-content');

  if (icon && menu) {
    icon.addEventListener('click', (e) => {
      e.stopPropagation();

      document.querySelectorAll('.dropdown-content').forEach(d => d.style.display = 'none');
      if (menu.style.display === 'block') {
        menu.style.display = 'none';
      } else {
        menu.style.display = 'block';
      }
    });

    document.addEventListener('click', () => {
      document.querySelectorAll('.dropdown-content').forEach(d => d.style.display = 'none');
    });
  }
}

// 게시글 초기화 후 로드
function resetAndLoadPosts() {
  const container = document.getElementById('post-list');
  container.innerHTML = '';
  postPage = 0;
  postEnd = false;
  loadPosts();
}

// 초기 실행
document.addEventListener('DOMContentLoaded', () => {
  loadRecommendSlider();
  loadPosts();
  setupCategoryButtons();
  setupDropdownToggle();

  window.addEventListener('scroll', () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
      loadPosts();
    }
  });
});
