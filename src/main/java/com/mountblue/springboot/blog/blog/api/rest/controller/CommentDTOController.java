package com.mountblue.springboot.blog.blog.api.rest.controller;

import com.mountblue.springboot.blog.blog.api.rest.dto.CommentDTO;
import com.mountblue.springboot.blog.blog.api.rest.service.CommentDTOService;
import com.mountblue.springboot.blog.blog.model.Comment;
import com.mountblue.springboot.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentDTOController {

    @Autowired
    private CommentDTOService commentDTOService;

    @PostMapping("/comment/{postId}")
    public String saveComment(@RequestBody CommentDTO commentDTO,@PathVariable(name="postId")int postId){
        commentDTO.setId(0);
       Comment comment= commentDTOService.saveComment(commentDTO, postId);
       if(comment!=null) {
           return "Comment Saved";
       }else {
           return "Comment is not saved try again";
       }
    }

    @PutMapping("/comment/{postId}")
    public String updateComment(@RequestBody CommentDTO commentDTO,@PathVariable(name="postId")int postId){
        Comment comment= commentDTOService.saveComment(commentDTO, postId);
        if(comment!=null) {
            return "Comment Update";
        }else {
            return "Comment Is Not Updated";
        }
    }

    @DeleteMapping("/comment/{postId}")
    public String deleteComment(@RequestBody CommentDTO commentDTO,@PathVariable(name="postId")int postId){
        return commentDTOService.deleteComment(commentDTO,postId);
    }
}
