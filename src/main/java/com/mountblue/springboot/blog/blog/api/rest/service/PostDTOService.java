package com.mountblue.springboot.blog.blog.api.rest.service;

import com.mountblue.springboot.blog.blog.api.rest.dto.PostDTO;
import com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostDTOService {

    Page<PostSummaryDTO> findAllPost(Pageable pageable);
    Pageable getDTOPage(int start, int limit, String sort);
    PostDTO findById(int id);
    Post savePost(PostDTO postDTO);
    String deletePost(int id);
}
