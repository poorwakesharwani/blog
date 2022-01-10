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

import java.util.List;

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
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value="author", required = false)List<Integer>author,
                                      @RequestParam(value="tag", required = false)List<String>tags,
                                      @RequestParam(value="date", required = false)String published,
                                      @RequestParam(value="keyword", required = false)String keyword){
        if (sort == null) {
            sort = "asc";
        }
        String startDate = "";
        String endDate = "";
        if (published != null) {
            System.out.println("published==" + published);
            String date[] = published.split(",");
            System.out.println(date[0] + " " + date[1]);
            startDate = date[0];
            endDate = date[1];
        }
        Pageable pageable = postDTOService.getDTOPage(start, limit, sort);
        if (tags != null && author != null && published != null) {
            return postDTOService.findByTagAndAuthorAndPublishedAt(author,tags,startDate,endDate,pageable,keyword);

        } else if (tags != null && author != null) {
            return postDTOService.findByTagAndAuthor(tags,author,pageable,keyword);

        } else if (tags != null && published != null) {
            return postDTOService.findByTagAndPublishedAt(tags,startDate,endDate,pageable,keyword);

        } else if (author != null && published != null) {
            return postDTOService.findByAuthorAndPublishedAt(author,startDate,endDate,pageable,keyword);

        }else if (tags != null) {
            return postDTOService.findByTag(tags,pageable,keyword);
        }else if (author != null) {
            return postDTOService.findByAuthor(author,pageable,keyword);

        } else if (published != null) {
            return postDTOService.findByPublishedAt(startDate,endDate,keyword,pageable);

        } else if (keyword != null) {
            return postDTOService.findByKeyword(keyword,pageable);
        }else{
            return postDTOService.findAllPost(pageable);
        }
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
