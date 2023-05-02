package com.web.dev.service;

import com.web.dev.dto.db.TagWithCountDto;
import com.web.dev.entity.*;
import com.web.dev.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BlogService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final TagRelationRepository tagRelationRepository;
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlogService(PostRepository postRepository, TagRepository tagRepository, TagRelationRepository tagRelationRepository, CommentsRepository commentsRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.tagRelationRepository = tagRelationRepository;
        this.commentsRepository = commentsRepository;
        this.userRepository = userRepository;
    }

    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElseThrow();
    }

    public void incrementCount(Integer postId) {
        postRepository.incrementViewCountByPostId(postId);
    }

    public List<Tag> getTagsByPostId(Integer postId) {
        List<TagRelation> relations = tagRelationRepository.findAllByPostId(postId);
        List<Integer> tagIds = new ArrayList<>(relations.size());
        for (TagRelation tr : relations) {
            tagIds.add(tr.getTagId());
        }
        return tagRepository.findAllByIdIn(tagIds);
    }

    public List<Post> getByPage(Integer page) {
        Pageable topTen = PageRequest.of(page, 10);
        Page<Post> pageTop = postRepository.findAll(topTen);
        return pageTop.toList();
    }

    public List<Post> getByPageAndTag(Integer page, String tagName) {
        Tag tag = tagRepository.findFirstByTagName(tagName).orElse(null);
        if (tag == null) {
            return getByPage(page);
        }
        List<TagRelation> tagRelations = tagRelationRepository.findAllByTagId(tag.getId());
        List<Integer> postIds = new ArrayList<>(tagRelations.size());
        for (TagRelation t : tagRelations) {
            postIds.add(t.getPostId());
        }
        Pageable topTen = PageRequest.of(page, 10);
        Page<Post> pageTop = postRepository.findAllByIdIn(topTen, postIds);
        return pageTop.toList();
    }

    public List<TagWithCountDto> findTopTags(){
        return tagRelationRepository.findTagsWithCount();
    }

    public List<PostComment> getCommentsByPost(Integer postId) {
        return commentsRepository.findAllByPost(postId);
    }

    public void saveComment(String fusionUserId, PostComment comment) {
        Optional<User> optionalUser = userRepository.findByFusionId(fusionUserId);
        if (optionalUser.isEmpty()) throw new NoSuchElementException();
        if (comment.getText().length() < 5) return;
        comment.setUser(optionalUser.get());
        comment.setDateCreated(new Date(System.currentTimeMillis()));
        commentsRepository.save(comment);
    }
}
