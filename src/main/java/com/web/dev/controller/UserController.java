package com.web.dev.controller;

import com.web.dev.dto.UserInfo;
import com.web.dev.entity.PostComment;
import com.web.dev.entity.SelectionHistory;
import com.web.dev.entity.User;
import com.web.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model,
                                  @AuthenticationPrincipal OidcUser user) {
        try {
            if (user == null) throw new NoSuchElementException();

            User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
            model.addAttribute("user", userData);
            model.addAttribute("fullname", userData.getFullName());
            List<SelectionHistory> list = userService.getSelectionHistory(userData.getId());
            model.addAttribute("selectionHistory", list);
            return "profile_history";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/profile/update")
    public String showProfileUpdatePage(Model model,
                                        @AuthenticationPrincipal OidcUser user) {
        model.addAttribute("newUserInfo", new UserInfo());
        return "profile_update";
    }

    @PostMapping("/profile/update")
    public void updateUser(@ModelAttribute("newUserInfo") UserInfo userInfo,
                           @AuthenticationPrincipal OidcUser user,
                           HttpServletResponse response) throws IOException {
        userService.updateUserInfo(user.getSubject(), userInfo);
        response.sendRedirect("http://localhost:8100/profile");
    }
}
