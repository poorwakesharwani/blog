package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TagService {

    void save(Tag tag);

    List<Tag> findAll();

    Tag findByName(String name);

    Tag findById(int id);

    List<String> findAllTag();
}
