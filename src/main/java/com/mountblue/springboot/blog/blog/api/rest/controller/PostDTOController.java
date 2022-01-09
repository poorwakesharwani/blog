package com.mountblue.springboot.blog.blog.api.rest.controller;

import com.mountblue.springboot.blog.blog.api.rest.dto.PostDTO;
import com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO;
import com.mountblue.springboot.blog.blog.api.rest.repository.PostDTORepository;
import com.mountblue.springboot.blog.blog.api.rest.service.PostDTOService;
import com.mountblue.springboot.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostDTOController {

    @Autowired
     private PostDTOService postDTOService;
    @Autowired
    private  PostService postService;
    @Autowired
    private PostDTORepository postDTORepository;


    @GetMapping("/")
    public Page<PostSummaryDTO> posts(Model model,
                                      @RequestParam(value = "start", required = false, defaultValue = "1") int start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                      @RequestParam(value = "sort", required = false) String sort){
        if (sort == null) {
            sort = "asc";
        }
        Pageable pageable = postDTOService.getDTOPage(start,limit,sort);
           return postDTOService.findAllPost(pageable);
    }

    @GetMapping("/post/{id}")
    public PostDTO findByIdPost(@PathVariable(name="id")int id){
        System.out.println("post by id");
        return postDTOService.findById(id);
    }

    @PostMapping("/newpost")
    public String savePost(@RequestBody PostDTO postDTO){
        postDTO.setId(0);
        if(postDTOService.savePost(postDTO)==null){
            return "Post is not saved";
        }else {
            return "Post Saved";
        }
    }

    @PutMapping("/newpost")
    public String updatePost(@RequestBody PostDTO postDTO){
        if(postDTOService.savePost(postDTO)==null){
            return "Post is not updated";
        }else {
            return "Post is updated";
        }
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable(name="postId")int postId){
        return postDTOService.deletePost(postId);
    }
}
