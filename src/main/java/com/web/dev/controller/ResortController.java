package com.web.dev.controller;

import com.web.dev.entity.Post;
import com.web.dev.entity.Resort;
import com.web.dev.entity.User;
import com.web.dev.entity.ViewHistory;
import com.web.dev.repository.UserRepository;
import com.web.dev.repository.ViewHistoryRepository;
import com.web.dev.service.ResortService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class ResortController {
    private final ResortService resortService;
    private final UserRepository userRepository;
    private final ViewHistoryRepository viewHistoryRepository;

    public ResortController(ResortService resortService, UserRepository userRepository, ViewHistoryRepository viewHistoryRepository) {
        this.resortService = resortService;
        this.userRepository = userRepository;
        this.viewHistoryRepository = viewHistoryRepository;
    }

    @RequestMapping("/resort/{resortId}")
    public String getResort(Model model, @PathVariable("resortId") Integer resortId, @AuthenticationPrincipal OidcUser userFusion) {
        try {
            if (userFusion != null) {
                model.addAttribute("fullname", userFusion.getFullName());
            } else {
                model.addAttribute("fullname", null);
            }
            String userEmail = (String) userFusion.getClaims().get("email");
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            User user = new User();
            if (userOptional.isEmpty()) {
                user.setEmail(userEmail);
                user.setFusionId((String) userFusion.getClaims().get("sub"));
                user.setFullName((String) userFusion.getClaims().get("name"));
                userRepository.save(user);
            } else {
                user = userOptional.get();
            }
            Resort resort = resortService.getResortById(resortId);
            resortService.incrementCount(resortId);
            model.addAttribute("resort", resort);

            ViewHistory viewHistory = new ViewHistory();
            viewHistory.setUserId(user.getId());
            viewHistory.setContentType("resort");
            viewHistory.setContentId(resortId);
            viewHistory.setViewDate(new Date(System.currentTimeMillis()));
            viewHistoryRepository.save(viewHistory);

            return "resort_sample";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping("/resorts")
    public String resortsList(Model model, @RequestParam(name = "place", required = false) String place, @AuthenticationPrincipal OidcUser user) {
        try {
            if (user != null) {
                model.addAttribute("fullname", user.getFullName());
            } else {
                model.addAttribute("fullname", null);
            }
            List<Resort> resorts;
            if (place == null) {
                resorts = resortService.getResortList();
            } else {
                resorts = resortService.getResortListByPlace(place);
            }
            List<Resort> sortedResorts;
            if (resorts.size() > 1) {
                sortedResorts = resorts.stream().sorted(Comparator.comparingInt(Resort::getViewCount).reversed()).toList();
            } else {
                sortedResorts = resorts;
            }
            model.addAttribute("resorts", sortedResorts);
            return "resorts";
        } catch (Exception e) {
            return "error";
        }
    }
}
