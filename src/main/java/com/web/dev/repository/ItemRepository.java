package com.web.dev.repository;

import com.web.dev.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(String gender, String skill, String eqType, String sport);

}
