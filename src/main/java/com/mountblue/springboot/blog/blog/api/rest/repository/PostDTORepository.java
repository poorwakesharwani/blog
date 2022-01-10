package com.mountblue.springboot.blog.blog.api.rest.repository;

import com.mountblue.springboot.blog.blog.api.rest.dto.*;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where p.title like %?1% or p.user.name like %?1% or p.content like %?1% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?1%))")
    Page findByKeyword(String keyword,Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where (p.title like %?2% or p.user.name like %?2% or p.content like %?2% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?2%))) and p.user.id in ?1")
    Page filterByAuthor(List<Integer>author,String keyword,Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p where " +
            "(p.title like %?2% or p.user.name like %?2% or p.content like %?2% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?2%))) and p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag t where t.name in ?1))")
    Page filterByTag(List<String>tag,String keyword,Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where (p.title like %?3% or p.user.name like %?3% or p.content like %?3% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?3%))) and p.publishedAt between ?1 and ?2 ")
    Page filterByPublishedAt(Date startDate,Date endDate,String keyword,Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where (p.title like %?5% or p.user.name like %?5% or p.content like %?5% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?5%))) and p.publishedAt between ?3 and ?4 and p.user.id in ?2 and p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag t where t.name in ?1))")
    Page filterByTagAndAuthorAndPublished(List<String>tag,List<Integer>author,Date startDate,Date endDate,
                                          String keyword,Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where (p.title like %?3% or p.user.name like %?3% or p.content like %?3% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?3%))) and p.user.id in ?2 and p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag t where t.name in ?1))")
    Page filterByTagAndAuthor(List<String>tag,List<Integer>author,
                                          String keyword,Pageable pageable);

    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where (p.title like %?4% or p.user.name like %?4% or p.content like %?4% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?4%))) and p.publishedAt between ?2 and ?3 and p.user.id in ?1")
    Page filterByAuthorAndPublished(List<Integer>author,Date startDate,Date endDate,
                                          String keyword,Pageable pageable);
    
    @Query("select new com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO" +
            "(p.id, p.title, p.excerpt, p.publishedAt, p.user.id, p.user.name,p.user.email) from Post p  " +
            "where (p.title like %?4% or p.user.name like %?4% or p.content like %?4% or p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag " +
            "t where t.name like %?4%))) and p.publishedAt between ?2 and ?3 and  p.id in " +
            "(select distinct pt.postId from PostTag pt where pt.tagId in (select t.id from Tag t where t.name in ?1))")
    Page filterByTagAndPublished(List<String>tag,Date startDate,Date endDate,
                                          String keyword,Pageable pageable);
}
