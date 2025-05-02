package kr.salm.controller;

import kr.salm.service.PostService;
import kr.salm.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> postPage = postService.findPostsByPage(page, 10);
        model.addAttribute("postPage", postPage);
        return "dashboard";
    }
}
