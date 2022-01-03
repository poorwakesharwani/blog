package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.PostTag;
import com.mountblue.springboot.blog.blog.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagServiceImpl implements PostTagService {

    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    public void save(PostTag postTag) {
        postTagRepository.save(postTag);
    }

    @Override
    public List<PostTag> findByPostId(int id) {
        return postTagRepository.findByPostId(id);
    }
}
