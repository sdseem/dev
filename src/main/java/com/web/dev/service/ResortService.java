package com.web.dev.service;

import com.web.dev.entity.Resort;
import com.web.dev.repository.ResortRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResortService {
    private final ResortRepository resortRepository;

    public ResortService(ResortRepository resortRepository) {
        this.resortRepository = resortRepository;
    }

    public Resort getResortById(Integer resortId) {
        return resortRepository.findById(resortId).orElseThrow();
    }

    public List<Resort> getResortList() {
        return resortRepository.findAll();
    }

    public List<Resort> getResortListByPlace(String place) {
        if (place == null || place.isEmpty()) {
            throw new NoSuchElementException("Empty filter");
        }
        if (place.equalsIgnoreCase("россия")) {
            return resortRepository.findAllByResortPlace("Россия");
        } else if (place.equalsIgnoreCase("зарубежье")) {
            return resortRepository.findAllByResortPlaceNot("Россия");
        } else {
            throw new NoSuchElementException("Empty filter");
        }
    }

    public void incrementCount(Integer resortId) {
        resortRepository.incrementViewCountByResortId(resortId);
    }
}
