package com.mountblue.springboot.blog.blog.api.rest.service;

import com.mountblue.springboot.blog.blog.api.rest.dto.CommentDTO;
import com.mountblue.springboot.blog.blog.model.Comment;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.service.CommentService;
import com.mountblue.springboot.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentDTOServiceImpl implements CommentDTOService {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @Override
    public Comment saveComment(CommentDTO commentDTO, int postId) {
        Comment comment = new Comment();
        if (commentDTO.getId() == 0) {
            comment.setCreatedAt(new Date());
            comment.setUpdatedAt(new Date());
        } else {
            Comment oldComment = commentService.findById(commentDTO.getId());
            comment.setCreatedAt(oldComment.getCreatedAt());
            comment.setUpdatedAt(new Date());
        }
        Post post = postService.findById(postId);
        comment.setId(commentDTO.getId());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        comment.setCommentData(commentDTO.getCommentContent());
        comment.setPost(post);
        comment = commentService.save(comment);
        return comment;
    }

    public String deleteComment(CommentDTO commentDTO, int postId) {
        Comment comment = commentService.findById(commentDTO.getId());
        if (comment == null) {
            throw new RuntimeException("Comment id not found-" + commentDTO.getId());
        }
        commentService.deletedById(commentDTO.getId());
        return "Deleted Comment id - " + commentDTO.getId();
    }
}
