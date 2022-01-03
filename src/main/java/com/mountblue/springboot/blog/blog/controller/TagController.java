package com.mountblue.springboot.blog.blog.controller;


import com.mountblue.springboot.blog.blog.service.TagService;
import com.mountblue.springboot.blog.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
}
