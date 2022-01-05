package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface PostService {

    List<Post> findAll();

    Post save(Post post);

    Post getById(int id);

    void deletedById(int id);

    Post findById(int id);

    Model search(Model model, String search,String sort,Pageable pageable);

    //Model display(Model model, int start, int limit);

    Model dashboard(Model model,Pageable pageable);

     Model filterByPublishedAt(Model model,String startDate,String endDate, String sort);
    Model filterByAuthor(Model model,List<Integer>author,String sort,String keyword);
    Model filterByTag(Model model,List<String>tag,String sort,String keyword);
    Model filterByTagAndAuthor(Model model,List<String>tags,List<Integer>author,String sort,String keyword);
    Model filterByAuthorAndPublished(Model model,List<Integer>author,String startDate,String endDate,String sort,
                                     String keyword);
    Model filterByTagAndPublished(Model model,List<String>tags,String startDate,String endDate,
                                  String sort,String keyword);
    Model filterByAll(Model model,List<String>tags,List<Integer>author,String startDate,String endDate,
                      String sort,String keyword);

    Model savePost(Post post, String tag, Model model,String authorId);
    Pageable getPage(int start, int limit, String sort);
}
