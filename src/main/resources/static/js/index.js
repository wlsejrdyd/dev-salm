let postPage = 0;
let postLoading = false;
let postEnd = false;

// 일반 게시글 무한 스크롤 로딩
async function loadPosts() {
  if (postLoading || postEnd) return;
  postLoading = true;

  try {
    const res = await fetch(`/api/posts?page=${postPage}`);
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
          <div class="meta">${post.author} · ${post.createdAt.replace('T', ' ').substring(0, 16)}</div>
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

// 추천 슬라이드 초기 로딩
async function loadRecommendSlider() {
  try {
    const res = await fetch('/api/posts/recommend?page=0');
    const json = await res.json();

    const slider = document.getElementById('recommend-slider');
    json.content.forEach(post => {
      const el = document.createElement('div');
      el.className = 'slider-item';
      el.innerHTML = `
        <h4>${post.title}</h4>
        <p>${post.content.length > 50 ? post.content.substring(0, 50) + '...' : post.content}</p>
      `;
      slider.appendChild(el);
    });

    startSlider();
  } catch (e) {
    console.error('추천 슬라이더 로딩 실패:', e);
  }
}

// 자동 슬라이더
function startSlider() {
  const slider = document.getElementById('recommend-slider');
  let scrollAmount = 0;

  setInterval(() => {
    scrollAmount += 1;
    if (scrollAmount >= slider.scrollWidth - slider.clientWidth) {
      scrollAmount = 0;
    }
    slider.scrollTo({ left: scrollAmount, behavior: 'smooth' });
  }, 50);
}

// 초기 실행 및 스크롤 이벤트
document.addEventListener('DOMContentLoaded', () => {
  loadRecommendSlider();
  loadPosts();

  const postSection = document.querySelector('.post-section');
  postSection.addEventListener('scroll', () => {
    if (postSection.scrollTop + postSection.clientHeight >= postSection.scrollHeight - 100) {
      loadPosts();
    }
  });
});
