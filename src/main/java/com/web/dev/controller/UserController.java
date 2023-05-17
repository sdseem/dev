package com.web.dev.controller;

import com.web.dev.dto.PostCreateDto;
import com.web.dev.dto.UserInfo;
import com.web.dev.entity.Post;
import com.web.dev.entity.PostComment;
import com.web.dev.entity.SelectionHistory;
import com.web.dev.entity.User;
import com.web.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
        model.addAttribute("fullname", userData.getFullName());
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

    @GetMapping("/edit/publication")
    public String showPostCreation(Model model,
                                   @AuthenticationPrincipal OidcUser user) {
        User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
        model.addAttribute("fullname", userData.getFullName());
        model.addAttribute("newPost", new PostCreateDto());
        return "post_create";
    }

    @PostMapping(value = "/edit/publication", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createPost(@ModelAttribute("newPost") PostCreateDto postCreateDto,
                           @RequestParam(name = "post_image1") MultipartFile file1,
                           @RequestParam(name = "post_image2") MultipartFile file2,
                           @AuthenticationPrincipal OidcUser user,
                           HttpServletResponse response) throws IOException {
        User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
        if (file1 == null) throw new NoSuchElementException();
        if (file1.getOriginalFilename() == null) throw new NoSuchElementException();
        if (file2 == null) throw new NoSuchElementException();
        if (file2.getOriginalFilename() == null) throw new NoSuchElementException();
        Post post = userService.addPost(userData.getFusionId(), postCreateDto, file1.getOriginalFilename(), file2.getOriginalFilename());
        Path currentRelativePath = Paths.get("");
        String folder = currentRelativePath.toAbsolutePath() + "/imgs/";
        byte[] img1 = file1.getBytes();
        Path path1 = Paths.get(folder + post.getPostMainPicture());
        Files.write(path1, img1);
        byte[] img2 = file2.getBytes();
        Path path2 = Paths.get(folder + post.getPostPicA());
        Files.write(path2, img2);
        response.sendRedirect("http://localhost:8100/blog/publication/" + post.getId());
    }


    @GetMapping("/education")
    public String startEdu(Model model,
                           @AuthenticationPrincipal OidcUser user,
                           @RequestParam(name = "sport", required = false) String sport,
                           @RequestParam(name = "level", required = false) Integer lvl) {
        if (user != null) {
            User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
            model.addAttribute("fullname");
            Integer level = userService.getSportLevel(userData.getId());
            if (lvl != null) {
                userService.saveLevel(userData.getId(), lvl);
                switch (lvl) {
                    case 0 -> {return "education_snow_level_1";}
                    case 1 -> {return "education_snow_level_2";}
                    case 2 -> {return "education_snow_level_3";}
                    case 3 -> {return "education_snow_level_4";}
                }
            } else {
                if (level != null) {
                    switch (level) {
                        case 0 -> {return "education_snow_level_1";}
                        case 1 -> {return "education_snow_level_2";}
                        case 2 -> {return "education_snow_level_3";}
                        case 3 -> {return "education_snow_level_4";}
                    }
                } else {
                    return "education_sport";
                }
            }
        } else {
            model.addAttribute("fullname", null);
        }
        return "education_sport";
    }

    @GetMapping("/education/test")
    public String getTest(Model model,
                          @AuthenticationPrincipal OidcUser user) {
        User userData = userService.getOrRegister(user.getSubject(), user.getFullName(), user.getEmail());
        model.addAttribute("fullname", userData.getFullName());
        return "test_snowboard";
    }
}
