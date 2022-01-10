package com.mountblue.springboot.blog.blog.api.rest.service;

import com.mountblue.springboot.blog.blog.api.rest.dto.CommentDTO;
import com.mountblue.springboot.blog.blog.api.rest.dto.PostDTO;
import com.mountblue.springboot.blog.blog.api.rest.dto.PostSummaryDTO;
import com.mountblue.springboot.blog.blog.api.rest.dto.TagDTO;
import com.mountblue.springboot.blog.blog.api.rest.repository.PostDTORepository;
import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.PostTag;
import com.mountblue.springboot.blog.blog.model.Tag;
import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.service.PostService;
import com.mountblue.springboot.blog.blog.service.PostTagService;
import com.mountblue.springboot.blog.blog.service.TagService;
import com.mountblue.springboot.blog.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostDTOServiceImpl implements PostDTOService{

    @Autowired
    private PostDTORepository postDTORepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PostTagService postTagService;

    @Override
    public Page<PostSummaryDTO> findAllPost(Pageable pageable) {
        Page<PostSummaryDTO>postSummaryDTOS=postDTORepository.findAllPostDTO(pageable);
        for (PostSummaryDTO pageSummaryDTO:postSummaryDTOS) {
            List<Tag> tags=postDTORepository.findAllTags(pageSummaryDTO.getId());
            List<TagDTO> tagsName=new ArrayList<>();
            for(Tag tag:tags){
                TagDTO tagDTO=new TagDTO();
                tagDTO.setName(tag.getName());
                tagsName.add(tagDTO);
            }
           pageSummaryDTO.setTags(tagsName);
        }
        return postSummaryDTOS;
    }

    public Pageable getDTOPage(int start, int limit, String sort) {
        if (sort.equals("asc")) {
            return PageRequest.of(start / limit, limit, Sort.by("publishedAt").ascending());
        } else if (sort.equals("desc")) {
            return PageRequest.of(start / limit, limit, Sort.by("publishedAt").descending());
        }
        return null;
    }

    @Override
    public PostDTO findById(int id) {
        PostDTO postDTO=postDTORepository.findByIdPost(id);
        List<Tag> tags=postDTORepository.findAllTags(id);
        List<TagDTO> tagsName=new ArrayList<>();
        for(Tag tag:tags){
            TagDTO tagDTO=new TagDTO();
            tagDTO.setName(tag.getName());
            tagsName.add(tagDTO);
        }
        postDTO.setTags(tagsName);
        postDTO.setComments(postDTORepository.findCommentsByPostId(id));
        return postDTO;
    }

    @Override
    public Post savePost(PostDTO postDTO) {
        Post post=new Post();
        if(postDTO.getId()!=0){
            Post oldPost=postService.getById(postDTO.getId());
            post.setCreatedAt(oldPost.getCreatedAt());
            post.setUpdatedAt(new Date());
        }else{
            post.setCreatedAt(new Date());
            post.setUpdatedAt(new Date());
        }
        post.setId(postDTO.getId());
        post.setContent(postDTO.getContent().trim());
        post.setPublishedAt(new Date());
        post.setPublished(true);
        post.setExcerpt(post.getContent().trim().substring(0, post.getContent().length() / 4));
        post.setTitle(postDTO.getTitle());
        Users user=usersService.getById(postDTO.getAuthor().getId());
        post.setUser(user);
        post = postService.save(post);
        for (int i = 0; i < postDTO.getTags().size(); i++) {
            Tag tagPost = tagService.findByName(postDTO.getTags().get(i).getName().trim());
            if (tagPost == null) {
                Tag newTag = new Tag();
                newTag.setName(postDTO.getTags().get(i).getName().trim());
                newTag.setCreatedAt(new Date());
                newTag.setUpdatedAt(new Date());
                tagService.save(newTag);
            }
        }

        for (int i = 0; i < postDTO.getTags().size(); i++) {
            PostTag postTag = new PostTag();
            postTag.setPostId(post.getId());
            Tag tagPost = tagService.findByName(postDTO.getTags().get(i).getName().trim());
            postTag.setTagId(tagPost.getId());
            postTag.setCreatedAt(new Date());
            postTag.setUpdatedAt(new Date());
            postTagService.save(postTag);
        }
        return post;
    }

    @Override
    public String deletePost(int id) {
        Post post = postService.findById(id);
        if(post == null){
            throw  new RuntimeException("Post id not found-"+id);
        }
        postService.deletedById(id);
        return "Deleted employee id - "+id;
    }

    @Override
    public Page findByKeyword(String keyword,Pageable pageable) {
        return postDTORepository.findByKeyword(keyword,pageable);
    }

    @Override
    public Page findByAuthor(List<Integer> author, Pageable pageable,String keyword) {
        if(keyword==null){
            keyword="";
        }
        return postDTORepository.filterByAuthor(author,keyword,pageable);
    }

    @Override
    public Page findByPublishedAt(String startDate , String endDate,String keyword, Pageable pageable) {
        if(keyword==null){
            keyword="";
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        if(keyword==null){
            keyword="";
        }
        Date start = null;
        Date end = null;
        try {
            start = formatter2.parse(startDate);
            end = formatter2.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDTORepository.filterByPublishedAt(start,end,keyword,pageable);
    }

    @Override
    public Page findByTag(List<String> tag, Pageable pageable,String keyword) {
        if(keyword==null){
            keyword="";
        }
        return postDTORepository.filterByTag(tag,keyword,pageable);
    }

    @Override
    public Page findByTagAndAuthor(List<String> tag, List<Integer> author, Pageable pageable,String keyword) {
        if(keyword==null){
            keyword="";
        }
        return postDTORepository.filterByTagAndAuthor(tag,author,keyword,pageable);
    }

    @Override
    public Page findByTagAndPublishedAt(List<String> tag, String startDate, String endDate, Pageable pageable,
                                        String keyword) {
        if(keyword==null){
            keyword="";
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = formatter2.parse(startDate);
            end = formatter2.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDTORepository.filterByTagAndPublished(tag,start,end,keyword,pageable);
    }

    @Override
    public Page findByAuthorAndPublishedAt(List<Integer> author, String startDate, String endDate, Pageable pageable,
                                           String keyword) {
        if(keyword==null){
            keyword="";
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = formatter2.parse(startDate);
            end = formatter2.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDTORepository.filterByAuthorAndPublished(author,start,end,keyword,pageable);
    }

    @Override
    public Page findByTagAndAuthorAndPublishedAt(List<Integer> author, List<String> tag, String startDate,
                                                 String endDate, Pageable pageable,String keyword) {
        if(keyword==null){
            keyword="";
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = formatter2.parse(startDate);
            end = formatter2.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDTORepository.filterByTagAndAuthorAndPublished(tag,author,start,end,keyword,pageable);
    }
}
