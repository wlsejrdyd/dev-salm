package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String showIndexPage(Model model) {
        // 최신 게시글 3개
        List<Post> latestPosts = postService.findLatestPosts(3);
        // 추천 상품도 Post로 임시 처리 (추후 Product 엔티티로 분리 가능)
        List<Post> recommendedProducts = postService.findRecommendedPosts(2);

        model.addAttribute("posts", latestPosts);
        model.addAttribute("products", recommendedProducts);
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> postPage = postService.findPostsByPage(page, 10);
        model.addAttribute("postPage", postPage);
        return "dashboard";
    }
}
