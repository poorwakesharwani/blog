package com.mountblue.springboot.blog.blog.repository;

import com.mountblue.springboot.blog.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from posts", nativeQuery = true)
    Page<Post> findAll(Pageable pageable);

    @Query(value = "select * from posts where author in ?1 and id in (select  id from posts where (title like %?2% or content like %?2%  or  id IN " +
            " (select post_id from post_tags where tag_id IN (select id from tags where name like %?2%))))", nativeQuery = true)
    Page<Post> filterByAuthor(List<Integer> author, String keyword, Pageable pageable);

    @Query(value = "select * from posts where id in (select post_id from post_tags where tag_id IN " +
            "(select id from tags where name in ?1)) and id in " +
            "(select  id from posts where (title like %?2% or content like %?2%  or  id IN " +
            "(select post_id from post_tags where tag_id IN " +
            "(select id from tags where name like %?2%))))", nativeQuery = true)
    Page<Post> filterByTag(List<String> tag, String keyword, Pageable pageable);

    @Query(value = "select * from posts where id in (select post_id from post_tags where tag_id IN " +
            "(select id from tags where name in ?1)) and author in ?2 and id in " +
            "(select  id from posts where (title like %?3% or content like %?3%  or  id IN " +
            "(select post_id from post_tags where tag_id IN " +
            "(select id from tags where name like %?3%)))  )", nativeQuery = true)
    Page<Post> filterByTagAndAuthor(List<String> tags, List<Integer> author, String keyword, Pageable pageable);

    @Query(value = "select * from posts where published_at between ?1 and ?2", nativeQuery = true)
    Page<Post> filterByPublishedAt(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select Distinct published_at from posts", nativeQuery = true)
    List<String> findPublishedAt();

    @Query(value = "select * from posts " +
            "where (title like %?1% or content like %?1%  or  " +
            "id IN (select post_id from post_tags where tag_id IN " +
            "(select id from tags where name like %?1%)))", nativeQuery = true)
    Page<Post> findSearchResult(String keyword, Pageable pageable);

    @Query(value = "select * from posts where id in(select post_id from post_tags where tag_id in " +
            "(select id from tags where name in ?2)) and id " +
            "IN (select id from posts where (title like %?1% or content like %?1%  or id IN" +
            " (select post_id from post_tags where tag_id IN (select id from tags where name like %?1%))) )"
            , nativeQuery = true)
    Page<Post> filterByTag(String keyword, List<String> tag, Pageable pageable);

    @Query(value = "select * from posts where author in ?3 and published_at between ?1 and ?2 and id in " +
            "(select  id from posts where (title like %?4% or content like %?4%  or  id IN " +
            "(select post_id from post_tags where tag_id IN (select id from tags where name like %?4%))))", nativeQuery = true)
    Page<Post> filterByAuthorAndPublished(Date start, Date end, List<Integer> author, String keyword, Pageable pageable);

    @Query(value = "select * from posts where id in (select post_id from post_tags where tag_id in " +
            "(select id from tags where name in ?3)) " +
            "and published_at between ?1 and ?2 and id in (select  id from posts " +
            "where (title like %?4% or content like %?4%  or  id IN (select post_id from post_tags where tag_id IN " +
            "(select id from tags where name like %?4%))))", nativeQuery = true)
    Page<Post> filterByTagAndPublished(Date start, Date end, List<String> tags, String keyword, Pageable pageable);

    @Query(value = "select * from posts where  author in ?4 and id in (select post_id from post_tags where tag_id in " +
            "(select id from tags where name in ?3)) " +
            "and published_at between ?1 and ?2 and id in (select  id from posts " +
            "where (title like %?5% or content like %?5%  or  id IN (select post_id from post_tags where tag_id IN " +
            "(select id from tags where name like %?5%))))", nativeQuery = true)
    Page<Post> filterByTagsAndAuthorAndPublished(Date start, Date end, List<String> tags, List<Integer> author, String keyword, Pageable pageable);
}
