package com.mountblue.springboot.blog.blog.controller;

import com.mountblue.springboot.blog.blog.model.Comment;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.PostTag;
import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import com.mountblue.springboot.blog.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/")
    public String allPost(Model model,
                          @RequestParam(value = "start", required = false, defaultValue = "1") int start,
                          @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "sort", required = false) String sort,
                          @RequestParam(value = "tag", required = false) List<String> tags,
                          @RequestParam(value = "author", required = false) List<Integer> author,
                          @RequestParam(value = "date", required = false) String published
    ) {
        if (sort == null) {
            sort = "asc";
        }
        Pageable pageable = postService.getPage(start, limit, sort);
        String startDate = "";
        String endDate = "";
        if (published != null) {
            System.out.println("published==" + published);
            String date[] = published.split(",");
            System.out.println(date[0] + " " + date[1]);
            startDate = date[0];
            endDate = date[1];
        }
        if (tags != null && author != null && published != null) {
            model = postService.filterByAll(model, tags, author, startDate, endDate, sort, keyword, pageable);
            return "dashboard";
        } else if (tags != null && author != null) {
            model = postService.filterByTagAndAuthor(model, tags, author, sort, keyword, pageable);
            return "dashboard";
        } else if (tags != null && published != null) {
            model = postService.filterByTagAndPublished(model, tags, startDate, endDate, sort, keyword, pageable);
            return "dashboard";
        } else if (author != null && published != null) {
            System.out.println("author and published");
            model = postService.filterByAuthorAndPublished(model, author, startDate, endDate, sort, keyword, pageable);
            return "dashboard";
        } else if (tags != null) {
            model = postService.filterByTag(model, tags, sort, keyword, pageable);
            return "dashboard";
        } else if (author != null) {
            model = postService.filterByAuthor(model, author, sort, keyword, pageable);
            return "dashboard";
        } else if (published != null) {
            model = postService.filterByPublishedAt(model, startDate, endDate, sort, pageable);
            return "dashboard";
        } else if (keyword != null) {
            model = postService.search(model, keyword, sort, pageable);
            return "dashboard";
        } else {
            System.out.println("dashboard");
            model = postService.dashboard(model, pageable);
            return "dashboard";
        }
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        model = usersService.findAllUsers(model);
        return "new-post";
    }

    @GetMapping("/post/{id}")
    public String readMore(@PathVariable(name = "id") int id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName()+"name=======");
        if (auth != null) {
            Users currentUser = usersRepository.findByEmail(auth.getName());
            model.addAttribute("currentUser", currentUser);
        }
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
                              @RequestParam(value = "authorName", required = false) String id) {
        System.out.println("author id== " + id);
        model = postService.savePost(post, tag, model, id);
        return "redirect:/";
    }
}
