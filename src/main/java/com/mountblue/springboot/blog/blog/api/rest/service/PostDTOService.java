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

    Page findByKeyword(String keyword, Pageable pageable);

    Page findByAuthor(List<Integer> author, Pageable pageable, String keyword);

    Page findByPublishedAt(String endDate, String startDate, String keyword, Pageable pageable);

    Page findByTag(List<String> tag, Pageable pageable, String keyword);

    Page findByTagAndAuthor(List<String> tag, List<Integer> author, Pageable pageable, String keyword);

    Page findByTagAndPublishedAt(List<String> tag, String startDate, String endDate, Pageable pageable, String keyword);

    Page findByAuthorAndPublishedAt(List<Integer> author, String startDate, String endDate, Pageable pageable,
                                    String keyword);

    Page findByTagAndAuthorAndPublishedAt(List<Integer> author, List<String> tag, String startDate, String endDate,
                                          Pageable pageable, String keyword);
}
