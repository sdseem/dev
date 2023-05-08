package com.web.dev.service;

import com.web.dev.dto.UserInfo;
import com.web.dev.entity.SelectionHistory;
import com.web.dev.entity.User;
import com.web.dev.repository.SelectionHistoryRepository;
import com.web.dev.repository.UserRepository;
import com.web.dev.repository.ViewHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final SelectionHistoryRepository selectionHistoryRepository;

    public UserService(UserRepository userRepository, ViewHistoryRepository viewHistoryRepository, SelectionHistoryRepository selectionHistoryRepository) {
        this.userRepository = userRepository;
        this.viewHistoryRepository = viewHistoryRepository;
        this.selectionHistoryRepository = selectionHistoryRepository;
    }

    public User getUserByFusionId(String fusionId) {
        return userRepository.findByFusionId(fusionId).orElseThrow();
    }

    public List<SelectionHistory> getSelectionHistory(Integer userId) {
        return selectionHistoryRepository.findAllByUserId(userId);
    }

    public User getOrRegister(String fusionId, String fullName, String email) {
        Optional<User> optionalUser = userRepository.findByFusionId(fusionId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User user = new User();
            user.setFusionId(fusionId);
            user.setEmail(email);
            user.setFullName(fullName);
            userRepository.save(user);
            return user;
        }
    }

    public void updateUserInfo(String fusionId, UserInfo userInfo) {
        Optional<User> optionalUser = userRepository.findByFusionId(fusionId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userInfo.age != null && !userInfo.age.replaceAll(" ", "").isEmpty()) {
                user.setAge(userInfo.age);
            }
            if (userInfo.email != null && !userInfo.email.replaceAll(" ", "").isEmpty()) {
                user.setEmail(userInfo.email);
            }
            if (userInfo.fullName != null && !userInfo.fullName.replaceAll(" ", "").isEmpty()) {
                user.setFullName(userInfo.fullName);
            }
            if (userInfo.gender != null && !userInfo.gender.replaceAll(" ", "").isEmpty()) {
                user.setGender(userInfo.gender);
            }
            if (userInfo.phone != null && !userInfo.phone.replaceAll(" ", "").isEmpty()) {
                user.setPhone(userInfo.phone);
            }
            userRepository.save(user);
        }
    }
}
