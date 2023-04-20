package com.web.dev.repository;

import com.web.dev.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = ?1")
    void incrementViewCountByPostId(Integer postId);

    Page<Post> findAllByIdIn(Pageable pageable, List<Integer> ids);
}
