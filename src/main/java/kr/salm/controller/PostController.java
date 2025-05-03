package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public String showPostDetail(@PathVariable Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "post-detail";
    }

    @GetMapping("/write")
    public String showWriteForm() {
        return "write";
    }

    @PostMapping("/write")
    public String submitPost(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam(required = false, defaultValue = "익명") String author,
                             @RequestParam(required = false) List<MultipartFile> images) {
        // 기본 저장만 처리. 이미지 저장은 추후 구현
        postService.savePost(title, content, author);

        // ✅ 향후 파일 저장 로직 추가 가능
        // images.forEach(file -> { ... });

        return "redirect:/";
    }
}
