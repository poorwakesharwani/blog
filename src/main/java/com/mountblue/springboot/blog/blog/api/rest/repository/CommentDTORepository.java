package com.mountblue.springboot.blog.blog.api.rest.repository;

import com.mountblue.springboot.blog.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDTORepository extends JpaRepository<Comment, Integer> {
}
