package com.web.dev.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal OidcUser user) {
        if (user != null) {
            model.addAttribute("fullname", user.getFullName());
        } else {
            model.addAttribute("fullname", null);
        }
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model, @AuthenticationPrincipal OidcUser user) {
        if (user != null) {
            model.addAttribute("fullname", user.getFullName());
        } else {
            model.addAttribute("fullname", null);
        }
        return "project";
    }

}