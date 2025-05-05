package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.service.FileService;
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
    private final FileService fileService;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
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
                             @RequestParam String category,
                             @RequestParam(required = false, defaultValue = "익명") String author,
                             @RequestParam(required = false) MultipartFile[] images) {

        // 1. 게시글 객체 생성
        Post post = new Post(title, content, author, category);

        // 2. 이미지 저장
        if (images != null && images.length > 0) {
            List<String> savedFileNames = fileService.saveFiles(images);
            post.setImages(savedFileNames);  // Post에 이미지 파일명 추가
        }

        // 3. post + 이미지 함께 저장
        Post saved = postService.savePost(post);
        System.out.println("✅ 저장된 post.id: " + saved.getId());
        System.out.println("✅ 저장된 이미지들: " + saved.getImages());

        return "redirect:/";
    }
}
