package com.mountblue.springboot.blog.blog.api.rest.repository;

import com.mountblue.springboot.blog.blog.api.rest.dto.*;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDTORepository extends JpaRepository<Post,Integer>{

    @Query(value = "select new com.mountblue.springboot.blog.blog.model.Tag(t.id,t.name,t.updatedAt,t.createdAt) from Tag t where id in " +
            "(select pt.tagId from PostTag pt where pt.postId = ?1)")
    List<Tag>findAllTags(int id);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.CommentDTO(c.id,c.name,c.email," +
            "c.commentData,c.createdAt,c.updatedAt)from Comment c where c.post.id=?1")
    List<CommentDTO>findCommentsByPostId(int postId);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p")
    Page<PostSummaryDTO> findAllPostDTO(Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostDTO" +
            "(p.id, p.title, p.content, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  where p.id=?1")
    PostDTO findByIdPost(int id);

}
