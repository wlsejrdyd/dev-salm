package kr.salm.service;

import jakarta.transaction.Transactional;
import kr.salm.entity.Post;
import kr.salm.entity.User;
import kr.salm.repository.PostRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.regex.*;
import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post savePost(String title, String content, User author, String category) {
        List<String> urls = extractUrls(content);
        String cleanContent = removeUrls(content);

        Post post = new Post(title, cleanContent, author, category);
        if (urls != null && !urls.isEmpty()) {
            post.setUrl(urls.get(0));
            extractProductInfo(urls.get(0), post);
        }

        return postRepository.save(post);
    }

    @Transactional
    public Post savePostWithImages(String title, String content, User author, String category, List<String> imageList) {
        List<String> urls = extractUrls(content);
        String cleanContent = removeUrls(content);

        Post post = new Post(title, cleanContent, author, category);
        if (imageList != null && !imageList.isEmpty()) {
            post.setImages(imageList);
        }
        if (urls != null && !urls.isEmpty()) {
            post.setUrl(urls.get(0));
            extractProductInfo(urls.get(0), post);
        }

        return postRepository.save(post);
    }

    @Transactional
    public Post savePost(Post post) {
        List<String> urls = extractUrls(post.getContent());
        String cleanContent = removeUrls(post.getContent());
        post.setContent(cleanContent);

        if (urls != null && !urls.isEmpty()) {
            post.setUrl(urls.get(0));
            extractProductInfo(urls.get(0), post);
        }

        return postRepository.save(post);
    }

    public Page<Post> findPostsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable);
    }

    public Page<Post> findPostsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAllByCategory(category, pageable);
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public List<String> extractUrls(String content) {
        List<String> urls = new ArrayList<>();
        Pattern urlPattern = Pattern.compile("(https?:\\/\\/[^\\s]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(content);
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }

    public String removeUrls(String content) {
        if (content == null) return null;
        return content.replaceAll("(https?:\\/\\/[^\\s]+)", "").trim();
    }

    public List<Post> findLatestPosts(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable).getContent();
    }

    public List<Post> findRecommendedPosts(int count) {
        return findLatestPosts(count);
    }

    // ✅ 임시 상품 정보 추출 (URL 기반 예시)
    private void extractProductInfo(String url, Post post) {
        // 여기서는 상품 이름과 가격을 URL에서 단순 추정하는 임시 로직
        if (url.contains("coupang")) {
            post.setProductName("쿠팡 특가 상품");
            post.setProductPrice("₩19,900");
        } else if (url.contains("naver")) {
            post.setProductName("네이버 쇼핑 인기상품");
            post.setProductPrice("₩24,500");
        } else if (url.contains("gmarket")) {
            post.setProductName("지마켓 베스트 상품");
            post.setProductPrice("₩29,000");
        } else {
            post.setProductName("등록된 상품");
            post.setProductPrice("가격 정보 없음");
        }
    }
}
