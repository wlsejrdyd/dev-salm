package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(postService.findPostsByPage(page, 10));
    }
}
