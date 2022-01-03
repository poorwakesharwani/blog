package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Tag;
import com.mountblue.springboot.blog.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag findById(int id) {
        Optional<Tag> result = tagRepository.findById(id);
        Tag tag = null;
        if (result.isPresent()) {
            tag = result.get();
        }
        return tag;
    }

    @Override
    public List<String> findAllTag() {
        return tagRepository.findAllTag();
    }
}
