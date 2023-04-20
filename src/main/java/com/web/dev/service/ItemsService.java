package com.web.dev.service;

import com.web.dev.entity.Item;
import com.web.dev.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ItemsService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemsService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findMainItem(String sport, String skill, String gender) {
        List<Item> items = itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                gender, skill, "снаряд", sport
        );
        items.addAll(itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                    "универсальный", skill, "снаряд", sport
        ));
        if (items.size() == 1) {
            return items.get(0);
        } else if (items.size() > 1) {
            int index = ThreadLocalRandom.current().nextInt(0, items.size());
            return items.get(index);
        } else throw new NoSuchElementException();
    }

    public Item findBoots(String sport, String skill, String gender) {
        List<Item> items = itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                gender, skill, "ботинки", sport
        );
        items.addAll(itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                "универсальный", skill, "ботинки", sport
        ));
        if (items.size() > 0) {
            return items.get(0);
        } else throw new NoSuchElementException();
    }

    public Item findMounts(String sport, String skill, String gender) {
        List<Item> items = itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                gender, skill, "крепление", sport
        );
        items.addAll(itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                "универсальный", skill, "крепление", sport
        ));
        if (items.size() > 0) {
            return items.get(0);
        } else throw new NoSuchElementException();
    }

    public String lenRecommendation(String sport, String skill, String hRange) {
        int min = 0;
        int max = 0;
        if (hRange.startsWith("-")) {
            max = Integer.parseInt(hRange.substring(1));
            max = max - 10;
            min = max - 20;
            if (skill.equals("продвинутый") || skill.equals("эксперт")) {
                max += 5;
                min += 5;
            }
            if (sport.equals("сноуборд")) {
                max -= 15;
                min -= 10;
            }
        } else if (hRange.endsWith("-")) {
            min = Integer.parseInt(hRange.substring(0, 3));
            max = min + 10;
            min = min - 5;
            if (skill.equals("продвинутый") || skill.equals("эксперт")) {
                max += 5;
                min += 5;
            }
            if (sport.equals("сноуборд")) {
                max -= 25;
                min -= 20;
            }
        } else {
            max = Integer.parseInt(hRange.substring(4));
            min = Integer.parseInt(hRange.substring(0, 3));
            max -= 10;
            min -= 5;
            if (skill.equals("продвинутый") || skill.equals("эксперт")) {
                max += 5;
                min += 5;
            }
            if (sport.equals("сноуборд")) {
                max -= 25;
                min -= 20;
            }
        }
        return min + "-" + max + " см";
    }

    public Item findPoles() {
        List<Item> items = itemRepository.findAllByGenderAndItemSportLevelAndItemEquipmentTypeAndItemSportType(
                "универсальный", "начинающий", "палки", "лыжи"
        );
        return items.get(0);
    }

    public String poleLen(String hRange) {
        int len = 0;
        if (hRange.startsWith("-")) {
            len = Integer.parseInt(hRange.substring(1));
        } else if (hRange.endsWith("-")) {
            len = Integer.parseInt(hRange.substring(0, 3));
        } else {
            len = Integer.parseInt(hRange.substring(4)) + Integer.parseInt(hRange.substring(0, 3));
            len = len / 2;
        }
        double res = 0.7*len;
        return String.valueOf((int) res);
    }
}
