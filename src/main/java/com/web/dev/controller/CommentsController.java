package com.web.dev.controller;

import com.web.dev.entity.PostComment;
import com.web.dev.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentsController {
    private final BlogService blogService;

    @Autowired
    public CommentsController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/api/v1/comments")
    public ResponseEntity<List<PostComment>> getComments(@RequestParam(name = "postid") Integer postId) {
        if (postId == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(blogService.getCommentsByPost(postId));
    }
}
