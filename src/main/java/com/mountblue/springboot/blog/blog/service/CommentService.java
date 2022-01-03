package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Comment;

import java.util.List;

public interface CommentService {

    void save(Comment comment);

    List<Comment> findByPostId(int id);

    Comment findById(int id);

    void deletedById(int id);
}
