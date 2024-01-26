package com.Forum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ok")
public class SuccesLoginController {

    @PostMapping
    public String loginSucess() {
        return "zalogowano";
    }
}
