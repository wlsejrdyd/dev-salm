package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/latest")
    public List<Post> getLatestPosts(@RequestParam(defaultValue = "70") int count) {
        return postService.findLatestPosts(count);
    }

    @GetMapping("/recommended")
    public List<Post> getRecommendedPosts(@RequestParam(defaultValue = "10") int count) {
        return postService.findRecommendedPosts(count);
    }

    @GetMapping("/category/{category}")
    public Page<Post> getPostsByCategory(@PathVariable String category,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return postService.findPostsByCategory(category, page, size);
    }

    // ✅ [추가] 일반 게시글 전체 조회 API
    @GetMapping
    public Page<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return postService.findPostsByPage(page, size);
    }
}
