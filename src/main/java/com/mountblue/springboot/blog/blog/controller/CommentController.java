package com.mountblue.springboot.blog.blog.controller;

import com.mountblue.springboot.blog.blog.model.Comment;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.service.CommentService;
import com.mountblue.springboot.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @PostMapping("/savecomment")
    public String saveComment(@ModelAttribute("comment") Comment comment, @RequestParam("postId") String postId,
                              Model model) {
        Post post = postService.findById(Integer.parseInt(postId));
        comment.setPost(post);
        comment.setCreatedAt(new Date());
        comment.setCreatedAt(new Date());
        comment=commentService.save(comment);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/deletecomment/{id}")
    public String deleteComment(@PathVariable(name = "id") int id, Model model) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            commentService.deletedById(id);
        }
        return "redirect:/post/" + comment.getPost().getId();
    }

    @GetMapping("/updatecomment/{id}")
    public String updatePost(@PathVariable(name = "id") int id, Model model) {
        Comment comment = commentService.findById(id);
        Post post = postService.getById(comment.getPost().getId());
        List<Comment> comments = commentService.findByPostId(id);
        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "blog";
    }
}
