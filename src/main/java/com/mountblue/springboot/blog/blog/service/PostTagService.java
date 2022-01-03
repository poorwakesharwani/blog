package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.PostTag;

import java.util.List;

public interface PostTagService {

    void save(PostTag postTag);

    List<PostTag> findByPostId(int id);
}
