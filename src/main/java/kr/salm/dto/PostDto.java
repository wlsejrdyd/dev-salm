package kr.salm.dto;

import kr.salm.entity.Post;
import java.time.LocalDateTime;

public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private String authorName;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
        this.authorName = post.getAuthor() != null ? post.getAuthor().getName() : "익명";
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAuthorName() {
        return authorName;
    }
}
