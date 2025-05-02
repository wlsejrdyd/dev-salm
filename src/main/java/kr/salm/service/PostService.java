package kr.salm.service;

import jakarta.transaction.Transactional;
import kr.salm.entity.Post;
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
    public Post savePost(String title, String content, String author) {
        Post post = new Post(title, content, author);
        return postRepository.save(post);
    }

    public Page<Post> findPostsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable);
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    // ✅ 본문에서 URL 자동 추출
    public List<String> extractUrls(String content) {
        List<String> urls = new ArrayList<>();
        Pattern urlPattern = Pattern.compile(
            "(https?:\\/\\/[^\\s]+)",
            Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(content);
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }

    // ✅ 최신 게시글 N개 조회
    public List<Post> findLatestPosts(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable).getContent();
    }

    // ✅ 추천 게시물 N개 (임시로 최신글 재사용)
    public List<Post> findRecommendedPosts(int count) {
        return findLatestPosts(count);
    }
}
