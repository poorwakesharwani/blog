package com.mountblue.springboot.blog.blog.api.rest.service;

import com.mountblue.springboot.blog.blog.api.rest.dto.CommentDTO;
import com.mountblue.springboot.blog.blog.model.Comment;

public interface CommentDTOService {

    Comment saveComment(CommentDTO commentDTO, int postId);

    String deleteComment(CommentDTO commentDTO, int postId);
}
