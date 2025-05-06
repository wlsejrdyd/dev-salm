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
                             @RequestParam(required = false) MultipartFile[] images,
                             @RequestParam(required = false) Integer representativeIndex) {

        if (customUserDetails == null || customUserDetails.getUser() == null) {
            return "redirect:/login";
        }

        User user = customUserDetails.getUser();
        Post post = new Post(title, content, user, category);

        if (images != null && images.length > 0) {
            List<String> savedFileNames = fileService.saveFiles(images);
            post.setImages(savedFileNames);

            // ✅ 대표 이미지 안전하게 설정
            if (representativeIndex != null &&
                representativeIndex >= 0 &&
                representativeIndex < savedFileNames.size()) {
                post.setThumbnail(savedFileNames.get(representativeIndex));
            } else if (!savedFileNames.isEmpty()) {
                post.setThumbnail(savedFileNames.get(0));
            }
        }

        postService.savePost(post);
        return "redirect:/";
    }
}
