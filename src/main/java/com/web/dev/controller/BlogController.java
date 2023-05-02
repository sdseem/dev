package com.web.dev.controller;

import com.web.dev.dto.db.TagWithCountDto;
import com.web.dev.entity.*;
import com.web.dev.repository.UserRepository;
import com.web.dev.repository.ViewHistoryRepository;
import com.web.dev.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final UserRepository userRepository;
    private final ViewHistoryRepository viewHistoryRepository;

    @Autowired
    public BlogController(BlogService blogService, UserRepository userRepository, ViewHistoryRepository viewHistoryRepository) {
        this.blogService = blogService;
        this.userRepository = userRepository;
        this.viewHistoryRepository = viewHistoryRepository;
    }

    @RequestMapping("/blog/publication/{postId}")
    public String blogPage(Model model, @PathVariable("postId") Integer postId, @AuthenticationPrincipal OidcUser userFusion) {
        try {
            if (userFusion != null) {
                model.addAttribute("fullname", userFusion.getFullName());
            } else {
                model.addAttribute("fullname", null);
            }
            String userEmail = (String) userFusion.getClaims().get("email");
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            User user = new User();
            if (userOptional.isEmpty()) {
                user.setEmail(userEmail);
                user.setFusionId((String) userFusion.getClaims().get("sub"));
                user.setFullName((String) userFusion.getClaims().get("name"));
                userRepository.save(user);
            } else {
                user = userOptional.get();
            }
            Post post = blogService.getPostById(postId);
            blogService.incrementCount(postId);
            Optional<User> creator = userRepository.findById(post.getPostCreator());
            List<Tag> tags = blogService.getTagsByPostId(postId);
            creator.ifPresent(value -> model.addAttribute("user", value));
            model.addAttribute("post", post);
            model.addAttribute("tags", tags);

            ViewHistory viewHistory = new ViewHistory();
            viewHistory.setUserId(user.getId());
            viewHistory.setContentType("post");
            viewHistory.setContentId(postId);
            viewHistory.setViewDate(new Date(System.currentTimeMillis()));
            viewHistoryRepository.save(viewHistory);
            List<PostComment> comments = blogService.getCommentsByPost(postId);
            model.addAttribute("comments", comments);
            PostComment newComment = new PostComment();
            newComment.setPost(postId);
            model.addAttribute("newComment", newComment);
            return "blog_sample";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping("/blog")
    public String blogPage(Model model, @RequestParam(required = false, name = "tag") String tag, @AuthenticationPrincipal OidcUser user) {
        try {
            model.addAttribute("currentPath", "http://localhost:8100/blog");
            if (user != null) {
                model.addAttribute("fullname", user.getFullName());
            } else {
                model.addAttribute("fullname", null);
            }
            List<Post> posts;
            if (tag == null || tag.isEmpty()) {
                posts = blogService.getByPage(0);
                model.addAttribute("currentTag", null);
            } else {
                posts = blogService.getByPageAndTag(0, tag);
                model.addAttribute("currentTag", tag);
            }
            List<Post> sortedPosts = posts.stream().sorted(Comparator.comparingInt(Post::getViewCount).reversed()).toList();
            for (int i = 0; i < 10; i++) {
                if (i < sortedPosts.size()) {
                    model.addAttribute("post" + i, sortedPosts.get(i));
                    model.addAttribute("tags" + i, blogService.getTagsByPostId(sortedPosts.get(i).getId()));
                } else {
                    model.addAttribute("post" + i, null);
                }
            }
            List<TagWithCountDto> countedTags = blogService.findTopTags();
            countedTags.sort(Comparator.comparingLong(TagWithCountDto::getCountUsages).reversed());
            model.addAttribute("topTags", countedTags);
            model.addAttribute("currPage", 1);
            return "blog";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping("/blog/{page}")
    public String blogPageByPage(Model model, @PathVariable("page") Integer page, @AuthenticationPrincipal OidcUser user) {
        try {
            model.addAttribute("currentPath", "http://localhost:8100/blog/" + page);
            if (user != null) {
                model.addAttribute("fullname", user.getFullName());
            } else {
                model.addAttribute("fullname", null);
            }
            if (page <= 0) {
                return "error";
            }
            List<Post> posts = blogService.getByPage(page - 1);
            List<Post> sortedPosts = posts.stream().sorted(Comparator.comparingInt(Post::getViewCount).reversed()).toList();
            for (int i = 0; i < 10; i++) {
                if (i < sortedPosts.size()) {
                    model.addAttribute("post" + i, sortedPosts.get(i));
                    model.addAttribute("tags" + i, blogService.getTagsByPostId(sortedPosts.get(i).getId()));
                } else {
                    model.addAttribute("post" + i, null);
                }
            }
            List<TagWithCountDto> countedTags = blogService.findTopTags();
            countedTags.sort(Comparator.comparingLong(TagWithCountDto::getCountUsages).reversed());
            model.addAttribute("topTags", countedTags);
            model.addAttribute("currPage", page);
            return "blog";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/comments/add")
    public void addComment(@ModelAttribute("newComment") PostComment postComment,
                             @AuthenticationPrincipal OidcUser user,
                             HttpServletResponse response) throws IOException {
        blogService.saveComment(user.getSubject(), postComment);
        response.sendRedirect("http://localhost:8100/blog/publication/" + postComment.getPost());
    }
}
