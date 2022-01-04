package com.mountblue.springboot.blog.blog.controller;

import com.mountblue.springboot.blog.blog.model.Comment;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.PostTag;
import com.mountblue.springboot.blog.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostTagService postTagService;
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/")
    public String allPost(Model model,
                          @RequestParam(value = "start", required = false, defaultValue = "1") int start,
                          @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "sort", required = false) String sort,
                          @RequestParam(value = "tag", required = false) List<String> tags,
                          @RequestParam(value = "author", required = false) List<Integer> author,
                          @RequestParam(value = "published", required = false) List<String> published
    ) {
        System.out.println("yes");
        System.out.println("published=="+published);
        if(tags!=null && author!=null && published!=null){
              // model=postService.filterByAll(model,tag,author,published,sort);
               return "dashboard";
        }else if(tags!=null && author!=null){
           model=postService.filterByTagAndAuthor(model,tags,author,sort,keyword);
            return "dashboard";
        }else if(tags!=null && published!=null){
           // model=postService.filterByTagAndPublished(model,tags,author,sort,keyword);
            return "dashboard";
        }else if(author!=null && published!=null){
            //model=postService.filterByAuthorAndPublished(model,tag,author,published,sort);
            return "dashboard";
        }else if(tags!=null) {
            model=postService.filterByTag(model,tags,sort,keyword);
            return "dashboard";
        }else if(author!=null) {
           model=postService.filterByAuthor(model,author,sort,keyword);
            return "dashboard";
        }else if(published!=null) {
           model=postService.filterByPublishedAt(model,published,sort);
            return "dashboard";
        }else if(keyword!=null){
                model=postService.search(model,keyword,sort);
                return "dashboard";
        }if(sort!=null){
           // model=postService.sort(model,sort);
            return "dashboard";
        }else{
        model = postService.dashboard(model);
        return "dashboard";
    }
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        model=usersService.findAllUsers(model);
        return "new-post";
    }

    @GetMapping("/post/{id}")
    public String readMore(@PathVariable(name = "id") int id, Model model) {
        Post post = postService.getById(id);
        Comment comment = new Comment();
        List<Comment> comments = commentService.findByPostId(id);
        List<PostTag> postTags = postTagService.findByPostId(id);
        String tags = "";
        for (int j = 0; j < postTags.size(); j++) {
            tags = tags + "," + tagService.findById(postTags.get(j).getTagId()).getName();
        }
        model.addAttribute("tags", tags.substring(1, tags.length()));
        model.addAttribute("comments", comments);
        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        return "blog";
    }

    @GetMapping("/updatepost/{id}")
    public String updatePost(@PathVariable(name = "id") int id, Model model) {
        Post post = postService.getById(id);
        List<PostTag> postTags = postTagService.findByPostId(id);
        String tags = "";
        for (int j = 0; j < postTags.size(); j++) {
            tags = tags + "," + tagService.findById(postTags.get(j).getTagId()).getName();
        }
        model.addAttribute("tags", tags.substring(1, tags.length()));
        model.addAttribute("post", post);
        return "/new-post";//change the name
    }

    @GetMapping("/deletepost/{id}")
    public String deletePost(@PathVariable(name = "id") int id, Model model) {
        Post post = postService.findById(id);
        if (post != null) {
            postService.deletedById(id);
        }
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "redirect:/";
    }

    @PostMapping("/publish")
    public String publishPost(@ModelAttribute("post") Post post, @RequestParam("tag") String tag, Model model,
                              @RequestParam(value = "authorName",required = false)String id) {
        System.out.println("author id== "+id);
        model = postService.savePost(post, tag, model,id);
        return "redirect:/";
    }
}
