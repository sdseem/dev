package com.web.dev.repository;

import com.web.dev.entity.SelectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectionHistoryRepository extends JpaRepository<SelectionHistory, Integer> {
    List<SelectionHistory> findAllByUserId(Integer userId);
}
