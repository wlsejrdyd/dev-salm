package kr.salm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 50)
    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image")
    private List<String> images = new ArrayList<>();

    @Column(length = 255)
    private String thumbnail;

    @Column(length = 1000)
    private String url;  // ✅ 상품 링크

    @Column(length = 255)
    private String productName;  // ✅ 상품명

    @Column(length = 50)
    private String productPrice;  // ✅ 상품 가격

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Post() {}

    public Post(String title, String content, User author, String category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductPrice() { return productPrice; }
    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
