package com.web.dev.repository;

import com.web.dev.entity.TagRelation;
import com.web.dev.dto.db.TagWithCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRelationRepository extends JpaRepository<TagRelation, Integer> {
    List<TagRelation> findAllByPostId(Integer postId);

    @Query("SELECT new com.web.dev.dto.db.TagWithCountDto(a.tagName, count(b.postId)) from Tag as a inner join TagRelation as b on a.id=b.tagId group by a.tagName")
    List<TagWithCountDto> findTagsWithCount();

    List<TagRelation> findAllByTagId(Integer tagId);
}
