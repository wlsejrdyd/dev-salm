package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.entity.User;
import kr.salm.service.FileService;
import kr.salm.service.PostService;
import kr.salm.repository.UserRepository;
import kr.salm.service.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    private final UserRepository userRepository;

    public PostController(PostService postService, FileService fileService, UserRepository userRepository) {
        this.postService = postService;
        this.fileService = fileService;
        this.userRepository = userRepository;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String submitPost(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam String category,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             @RequestParam(required = false) MultipartFile[] images) {

        // ✅ 인증 객체 null 방어 처리
        if (customUserDetails == null || customUserDetails.getUser() == null) {
            return "redirect:/login";
        }

        User user = customUserDetails.getUser();
        Post post = new Post(title, content, user, category);

        if (images != null && images.length > 0) {
            List<String> savedFileNames = fileService.saveFiles(images);
            post.setImages(savedFileNames);
        }

        postService.savePost(post);
        return "redirect:/";
    }
}
