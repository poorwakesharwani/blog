package com.mountblue.springboot.blog.blog.repository;

import com.mountblue.springboot.blog.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);

    @Query(value = "Select Distinct name from tags", nativeQuery = true)
    List<String> findAllTag();
}
