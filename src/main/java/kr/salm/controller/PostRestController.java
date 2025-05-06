package kr.salm.controller;

import kr.salm.dto.PostDto;
import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/latest")
    public List<PostDto> getLatestPosts(@RequestParam(defaultValue = "70") int count) {
        return postService.findLatestPosts(count).stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/recommended")
    public List<PostDto> getRecommendedPosts(@RequestParam(defaultValue = "10") int count) {
        return postService.findRecommendedPosts(count).stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    // ✅ 슬래시 포함 카테고리 대응 (ex. 청소/정리)
    @GetMapping("/category")
    public Page<PostDto> getPostsByCategory(@RequestParam String category,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = postService.findPostsByCategory(category, page, size);
        List<PostDto> dtoList = posts.getContent().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, posts.getPageable(), posts.getTotalElements());
    }

    @GetMapping
    public Page<PostDto> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = postService.findPostsByPage(page, size);
        List<PostDto> dtoList = posts.getContent().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, posts.getPageable(), posts.getTotalElements());
    }
}

