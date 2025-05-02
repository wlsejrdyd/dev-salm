package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    // 🔥 추천글 mock API - 총 70개, 10개씩 페이징
    @GetMapping("/recommend")
    public ResponseEntity<Page<Post>> getMockRecommendPosts(@RequestParam(defaultValue = "0") int page) {
        List<Post> mockList = new ArrayList<>();
        int start = page * 10;
        int end = Math.min(start + 10, 70);

        for (int i = start + 1; i <= end; i++) {
            Post post = new Post();
            post.setTitle("추천 살림 노하우 " + i);
            post.setContent("이건 추천 콘텐츠 예시입니다. 번호: " + i + "번 살림 꿀팁을 확인해보세요!");
            post.setAuthor("추천고수");
            post.setCreatedAt(java.time.LocalDateTime.now().minusDays(i));
            mockList.add(post);
        }

        Page<Post> mockPage = new PageImpl<>(mockList);
        return ResponseEntity.ok(mockPage);
    }
}
