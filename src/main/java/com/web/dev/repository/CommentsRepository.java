package com.web.dev.repository;

import com.web.dev.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<PostComment, Integer> {
    List<PostComment> findAllByPost(Integer postId);
}
