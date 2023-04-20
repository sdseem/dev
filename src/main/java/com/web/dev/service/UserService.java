package com.web.dev.service;

import com.web.dev.repository.UserRepository;
import com.web.dev.repository.ViewHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ViewHistoryRepository viewHistoryRepository;

    public UserService(UserRepository userRepository, ViewHistoryRepository viewHistoryRepository) {
        this.userRepository = userRepository;
        this.viewHistoryRepository = viewHistoryRepository;
    }
}
