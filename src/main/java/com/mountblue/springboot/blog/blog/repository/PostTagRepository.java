package com.mountblue.springboot.blog.blog.repository;

import com.mountblue.springboot.blog.blog.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

    List<PostTag> findByPostId(int id);
}
