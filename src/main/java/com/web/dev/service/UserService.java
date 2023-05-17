package com.web.dev.service;

import com.web.dev.dto.PostCreateDto;
import com.web.dev.dto.UserInfo;
import com.web.dev.entity.Post;
import com.web.dev.entity.SelectionHistory;
import com.web.dev.entity.SportLevel;
import com.web.dev.entity.User;
import com.web.dev.repository.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final SelectionHistoryRepository selectionHistoryRepository;
    private final PostRepository postRepository;
    private final SportLevelRepository sportLevelRepository;

    public UserService(UserRepository userRepository, ViewHistoryRepository viewHistoryRepository, SelectionHistoryRepository selectionHistoryRepository, PostRepository postRepository, SportLevelRepository sportLevelRepository) {
        this.userRepository = userRepository;
        this.viewHistoryRepository = viewHistoryRepository;
        this.selectionHistoryRepository = selectionHistoryRepository;
        this.postRepository = postRepository;
        this.sportLevelRepository = sportLevelRepository;
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

    public Post addPost(String fusionId, PostCreateDto dto, String file1, String file2) {
        User user = userRepository.findByFusionId(fusionId).orElseThrow();
        Post post = new Post();
        post.setPostCreator(user.getId());
        post.setPostName(dto.postName);
        post.setPublicationDate(new Date());
        post.setPostDescription(dto.postDescription);
        post.setPostContentA(dto.postContentA);
        post.setPostContentB(dto.postContentB);
        post.setViewCount(0);
        postRepository.save(post);
        post.setPostMainPicture(post.getId() + "1_" + file1.replaceAll(" ", "_"));
        post.setPostPicA(post.getId() + "2_" + file2.replaceAll(" ", "_"));
        postRepository.save(post);
        return post;
    }

    public Integer getSportLevel(Integer userId) {
        Optional<SportLevel> optionalSportLevel = sportLevelRepository.findByUserId(userId);
        if (optionalSportLevel.isEmpty()) {
            return null;
        }
        return optionalSportLevel.get().getLevel();
    }

    public void saveLevel(Integer userId, Integer level) {
        Optional<SportLevel> optionalSportLevel = sportLevelRepository.findByUserId(userId);
        if (optionalSportLevel.isEmpty()) {
            SportLevel sportLevel = new SportLevel();
            sportLevel.setLevel(level);
            sportLevel.setUserId(userId);
            sportLevelRepository.save(sportLevel);
        } else {
            SportLevel sl = optionalSportLevel.get();
            sl.setLevel(level);
            sportLevelRepository.save(sl);
        }
    }
}
