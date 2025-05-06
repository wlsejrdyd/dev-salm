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
        Post post = new Post(title, content, author, category);
        return postRepository.save(post);
    }

    @Transactional
    public Post savePostWithImages(String title, String content, User author, String category, List<String> imageList) {
        Post post = new Post(title, content, author, category);
        if (imageList != null && !imageList.isEmpty()) {
            post.setImages(imageList);
        }
        return postRepository.save(post);
    }

    @Transactional
    public Post savePost(Post post) {
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

    public List<Post> findLatestPosts(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable).getContent();
    }

    public List<Post> findRecommendedPosts(int count) {
        return findLatestPosts(count);
    }
}
