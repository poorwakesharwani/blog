package com.mountblue.springboot.blog.blog.controller;

import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String createAccount(Model model) {
        Users user = new Users();
        model.addAttribute("newuser", user);
        return "register";
    }

    @PostMapping("/save")
    public String createUser(@ModelAttribute("newuser") Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("Author");
        usersService.save(user);
        return "register";
    }
}
