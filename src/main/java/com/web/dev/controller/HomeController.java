package com.web.dev.controller;

import com.web.dev.entity.User;
import com.web.dev.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal OidcUser user) {
        if (user != null) {
            User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
            model.addAttribute("fullname", userData.getFullName());
        } else {
            model.addAttribute("fullname", null);
        }
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model, @AuthenticationPrincipal OidcUser user) {
        if (user != null) {
            User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
            model.addAttribute("fullname", userData.getFullName());
        } else {
            model.addAttribute("fullname", null);
        }
        return "project";
    }

}