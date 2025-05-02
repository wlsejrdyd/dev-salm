package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 필요 없다면 이 아래 메서드는 제거해도 됨
    // @GetMapping("/list")
    // public String showAllPosts(Model model) {
    //     model.addAttribute("posts", postService.findPostsByPage(0, 10).getContent());
    //     return "posts";
    // }
}
