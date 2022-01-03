package com.mountblue.springboot.blog.blog.controller;

import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/checkingcredential")
    public String checkCredential(@ModelAttribute("user") Users user) {
        Users userDetail = usersService.findByEmailAndPassword(user.getEmail(), user.getPassword());
//       if(userDetail!=null){
//          // return ;
//       }else{
        return "login";
        //}
    }

    @GetMapping("/register")
    public String createAccount(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/save")
    public String createUser(@ModelAttribute("newuser") Users user) {
        usersService.save(user);
        return "register";
    }
}
