package com.web.dev.controller;

import com.web.dev.entity.Resort;
import com.web.dev.entity.User;
import com.web.dev.entity.ViewHistory;
import com.web.dev.repository.UserRepository;
import com.web.dev.repository.ViewHistoryRepository;
import com.web.dev.service.ResortService;
import com.web.dev.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class ResortController {
    private final ResortService resortService;
    private final UserRepository userRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final UserService userService;

    public ResortController(ResortService resortService, UserRepository userRepository, ViewHistoryRepository viewHistoryRepository, UserService userService) {
        this.resortService = resortService;
        this.userRepository = userRepository;
        this.viewHistoryRepository = viewHistoryRepository;
        this.userService = userService;
    }

    @RequestMapping("/resort/{resortId}")
    public String getResort(Model model, @PathVariable("resortId") Integer resortId, @AuthenticationPrincipal OidcUser userFusion) {
        try {
            if (userFusion != null) {
                User userData = userService.getOrRegister(userFusion.getSubject(), userFusion.getFullName(), userFusion.getEmail());
                model.addAttribute("fullname", userData.getFullName());
                ViewHistory viewHistory = new ViewHistory();
                viewHistory.setUserId(userData.getId());
                viewHistory.setContentType("resort");
                viewHistory.setContentId(resortId);
                viewHistory.setViewDate(new Date(System.currentTimeMillis()));
                viewHistoryRepository.save(viewHistory);
            } else {
                model.addAttribute("fullname", null);
            }
            Resort resort = resortService.getResortById(resortId);
            resortService.incrementCount(resortId);
            model.addAttribute("resort", resort);

            return "resort_sample";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping("/resorts")
    public String resortsList(Model model, @RequestParam(name = "place", required = false) String place, @AuthenticationPrincipal OidcUser user) {
        try {
            if (user != null) {
                User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
                model.addAttribute("fullname", userData.getFullName());
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
