package kr.salm.controller;

import kr.salm.entity.Post;
import kr.salm.entity.Comment;
import kr.salm.service.PostService;
import kr.salm.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/submit")
    public String submitPost(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             Model model) {
        postService.savePost(title, content, "guest");
        model.addAttribute("message", "게시글이 성공적으로 저장되었습니다.");
        return "redirect:/dashboard";
    }

    @GetMapping("/posts")
    public String showPostList(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return "posts";
    }

    @GetMapping("/post/{id}")
    public String showPostDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findPostById(id));
        model.addAttribute("comments", commentService.getCommentsByPostId(id));
        return "post-detail";
    }

    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam("content") String content) {
        commentService.saveComment(id, "guest", content);
        return "redirect:/post/" + id;
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return "redirect:/posts";
    }
}
