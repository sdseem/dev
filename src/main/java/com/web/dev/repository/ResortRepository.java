package com.web.dev.repository;

import com.web.dev.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Integer> {
    List<Resort> findAllByResortPlace(String place);
    List<Resort> findAllByResortPlaceNot(String place);

    @Transactional
    @Modifying
    @Query("UPDATE Resort r SET r.viewCount = r.viewCount + 1 WHERE r.id = ?1")
    void incrementViewCountByResortId(Integer resortId);
}
