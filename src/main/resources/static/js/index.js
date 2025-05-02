let pageRecommend = 0;
let pagePost = 0;
let loadingRecommend = false;
let loadingPost = false;
let endRecommend = false;
let endPost = false;

// 추천글 로딩
async function loadRecommendPosts() {
  if (loadingRecommend || endRecommend) return;
  loadingRecommend = true;

  try {
    const res = await fetch(`/api/posts/recommend?page=${pageRecommend}`);
    const json = await res.json();

    if (!json.content || json.content.length === 0) {
      endRecommend = true;
      return;
    }

    const container = document.getElementById('recommend-list');
    json.content.forEach(post => {
      const el = document.createElement('div');
      el.className = 'post-card';
      el.innerHTML = `
        <h3>${post.title}</h3>
        <p>${post.content && post.content.length > 80 ? post.content.substring(0, 80) + '...' : (post.content || '')}</p>
        <div class="meta">${post.author || '익명'} · ${(post.createdAt || '').replace('T', ' ').substring(0, 16)}</div>
        <a href="/post/${post.id}" class="read-more">자세히 보기</a>
      `;
      container.appendChild(el);
    });

    pageRecommend++;
  } catch (e) {
    console.error('추천 게시글 로딩 실패:', e);
  } finally {
    loadingRecommend = false;
  }
}

// 일반 게시글 로딩
async function loadPosts() {
  if (loadingPost || endPost) return;
  loadingPost = true;

  try {
    const res = await fetch(`/api/posts?page=${pagePost}`);
    const json = await res.json();

    if (!json.content || json.content.length === 0) {
      endPost = true;
      return;
    }

    const container = document.getElementById('post-list');
    json.content.forEach(post => {
      const el = document.createElement('div');
      el.className = 'post-card';
      el.innerHTML = `
        <h3>${post.title}</h3>
        <p>${post.content && post.content.length > 100 ? post.content.substring(0, 100) + '...' : (post.content || '')}</p>
        <div class="meta">${post.author || '익명'} · ${(post.createdAt || '').replace('T', ' ').substring(0, 16)}</div>
        <a href="/post/${post.id}" class="read-more">자세히 보기</a>
      `;
      container.appendChild(el);
    });

    pagePost++;
  } catch (e) {
    console.error('일반 게시글 로딩 실패:', e);
  } finally {
    loadingPost = false;
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const recommendBox = document.getElementById('recommend-list').parentElement;
  const postBox = document.getElementById('post-list').parentElement;

  loadRecommendPosts();
  loadPosts();

  recommendBox.addEventListener('scroll', () => {
    if (recommendBox.scrollTop + recommendBox.clientHeight >= recommendBox.scrollHeight - 100) {
      loadRecommendPosts();
    }
  });

  postBox.addEventListener('scroll', () => {
    if (postBox.scrollTop + postBox.clientHeight >= postBox.scrollHeight - 100) {
      loadPosts();
    }
  });
});
