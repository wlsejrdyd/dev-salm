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
                             @RequestParam(required = false) List<MultipartFile> images) {

        postService.savePost(title, content, author, category);  // 수정된 부분

        if (images != null && !images.isEmpty()) {
            // MultipartFile[]로 변환하여 saveFiles 메소드 호출
            String[] imagesArray = new String[images.size()];
            imagesArray = images.stream().map(image -> fileService.saveFiles(images.toArray(new MultipartFile[0]))).toArray(String[]::new);
            
            // 저장된 파일 이름을 리스트로 받아서 처리할 수 있습니다
            for (String savedFileName : imagesArray) {
                System.out.println("저장된 파일명: " + savedFileName);
                // TODO: post와 연결되는 이미지 저장 로직 추가 예정
            }
        }

        return "redirect:/";
    }
}
