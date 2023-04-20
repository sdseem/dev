package com.web.dev.repository;

import com.web.dev.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByIdIn(List<Integer> ids);
    Optional<Tag> findFirstByTagName(String name);
}
