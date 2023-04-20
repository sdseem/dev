package com.web.dev.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(){
        return "error";
    }
}
