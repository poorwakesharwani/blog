package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Comment;
import com.mountblue.springboot.blog.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByPostId(int id) {
        return commentRepository.findByPostId(id);
    }

    @Override
    public Comment findById(int id) {
        Optional<Comment> result = commentRepository.findById(id);
        Comment comment = null;
        if (result.isPresent()) {
            comment = result.get();
        }
        return comment;
    }

    @Override
    public void deletedById(int id) {
        commentRepository.deleteById(id);
    }
}
