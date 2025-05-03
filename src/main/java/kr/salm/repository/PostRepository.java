package kr.salm.repository;

import kr.salm.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByCategory(String category, Pageable pageable);  // ✅ 카테고리 기반 페이징
}
