package com.thymeleaf.bootproject.Cotroller;

import com.thymeleaf.bootproject.Model.UserVO;
import com.thymeleaf.bootproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String login(){
        return "user/login";
    }
    @GetMapping(value = "/register")
    public String register(){
        return "user/register";
    }

    @PostMapping(value = "/register")
    public String registerPost(UserVO user){
        userService.save(user);
        return "redirect:/";
    }
}
