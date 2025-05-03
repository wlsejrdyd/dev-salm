package kr.salm.controller;

import kr.salm.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final PostService postService;

    public CategoryController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{category}")
    public String showCategoryPosts(@PathVariable String category, Model model) {
        model.addAttribute("category", category);
        return "posts";
    }
}
