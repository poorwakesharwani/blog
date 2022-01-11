package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.PostTag;
import com.mountblue.springboot.blog.blog.model.Tag;
import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.PostRepository;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostTagService postTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getById(int id) {
        return postRepository.getById(id);
    }

    @Override
    public void deletedById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post findById(int id) {
        Optional<Post> result = postRepository.findById(id);
        Post post = null;
        if (result.isPresent()) {
            post = result.get();
        }
        return post;
    }

    private Model findAllTags(Map<Post, String> postIdVsTags, Model model, Page<Post> posts) {
        for (Post post : posts) {
            List<PostTag> postTags = postTagService.findByPostId(post.getId());
            String tags = "";
            for (PostTag postTag : postTags) {
                tags = tags + "," + tagService.findById(postTag.getTagId()).getName();
            }
            postIdVsTags.put(post, tags.substring(1, tags.length()));
        }
        model.addAttribute("postIdVsTags", postIdVsTags);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Users currentUser = usersRepository.findByEmail(auth.getName());
            model.addAttribute("currentUser", currentUser);
        }
        model = allModelData(model);
        return model;
    }

    public Model search(Model model, String keyword, String sort, Pageable pageable) {
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        Page<Post> posts = postRepository.findSearchResult(keyword, pageable);
        return findAllTags(postIdVsTags, model, posts);
    }

    private Model allModelData(Model model) {
        List<String> tags = tagService.findAllTag();
        List<String> publishAt = postRepository.findPublishedAt();
        model.addAttribute("tagName", tags);
        model.addAttribute("authors", usersRepository.findAll());
        model.addAttribute("publishAt", publishAt);
        return model;
    }

    @Override
    public Model dashboard(Model model, Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByPublishedAt(Model model, String startDate, String endDate, String sort, Pageable pageable) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page<Post> posts = postRepository.filterByPublishedAt(start, end, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByAuthor(Model model, List<Integer> author, String sort, String keyword, Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        Page<Post> posts = postRepository.filterByAuthor(author, keyword, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByTag(Model model, List<String> tag, String sort, String keyword
            , Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        Page<Post> posts = postRepository.filterByTag(tag, keyword, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByTagAndAuthor(Model model, List<String> tags, List<Integer> author, String sort,
                                      String keyword, Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        Page<Post> posts = postRepository.filterByTagAndAuthor(tags, author, keyword, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByAuthorAndPublished(Model model, List<Integer> author, String startDate, String endDate,
                                            String sort, String keyword, Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page<Post> posts = postRepository.filterByAuthorAndPublished(start, end, author, keyword, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByTagAndPublished(Model model, List<String> tags, String startDate, String endDate, String sort,
                                         String keyword, Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page<Post> posts = postRepository.filterByTagAndPublished(start, end, tags, keyword, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model filterByAll(Model model, List<String> tags, List<Integer> author, String startDate, String endDate,
                             String sort, String keyword, Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page<Post> posts = postRepository.filterByTagsAndAuthorAndPublished(start, end, tags, author, keyword, pageable);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        return findAllTags(postIdVsTags, model, posts);
    }

    @Override
    public Model savePost(Post post, String tag, Model model, String authorId) {
        String tags[] = tag.split(",");
        if (authorId != null) {
            Users user = usersService.getById(Integer.parseInt(authorId));
            post.setUser(user);
        } else if (authorId == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users user = usersService.findByEmail(auth.getName());
            post.setUser(user);
        }
        post.setExcerpt(post.getContent().trim().substring(0, post.getContent().length() / 4));
        post.setPublishedAt(new Date());
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());
        post.setContent(post.getContent().trim());
        post.setPublished(true);
        post = save(post);
        for (int i = 0; i < tags.length; i++) {
            Tag tagPost = tagService.findByName(tags[i].trim());
            if (tagPost == null) {
                Tag newTag = new Tag();
                newTag.setName(tags[i].trim());
                newTag.setCreatedAt(new Date());
                newTag.setUpdatedAt(new Date());
                tagService.save(newTag);
            }
        }

        for (int i = 0; i < tags.length; i++) {
            PostTag postTag = new PostTag();
            postTag.setPostId(post.getId());
            Tag tagPost = tagService.findByName(tags[i].trim());
            postTag.setTagId(tagPost.getId());
            postTag.setCreatedAt(new Date());
            postTag.setUpdatedAt(new Date());
            postTagService.save(postTag);
        }
        List<Post> posts = findAll();
        model.addAttribute("posts", posts);
        return model;
    }

    @Override
    public Pageable getPage(int start, int limit, String sort) {
        if (sort.equals("asc")) {
            return PageRequest.of(start / limit, limit, Sort.by("posts.published_at").ascending());
        } else if (sort.equals("desc")) {
            return PageRequest.of(start / limit, limit, Sort.by("posts.published_at").descending());
        }
        return null;
    }
}
