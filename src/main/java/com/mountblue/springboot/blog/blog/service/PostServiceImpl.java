package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Post;
import com.mountblue.springboot.blog.blog.model.PostTag;
import com.mountblue.springboot.blog.blog.model.Tag;
import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.PostRepository;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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

    private Model findAllTags(Map<Post,String>postIdVsTags,Model model,List<Post>posts,Map<Post,Users>postsVsUsers){
        for (Post post : posts) {
            System.out.println("id="+post.getId());
            List<PostTag> postTags = postTagService.findByPostId(post.getId());
            String tags = "";
            for (PostTag postTag : postTags) {
                tags = tags + "," + tagService.findById(postTag.getTagId()).getName();
            }
            postIdVsTags.put(post, tags.substring(1, tags.length()));
        }
        for(Post post : posts){
           postsVsUsers.put(post,post.getUser());
        }
        model.addAttribute("postVsUsers",postsVsUsers);
        model.addAttribute("postIdVsTags", postIdVsTags);
        model=allModelData(model);
        return model;
    }

    public Model search(Model model, String keyword, String sort) {
        System.out.println("search and sorting service "+keyword+" sort  "+sort);
        if(sort==null){
            sort="asc";
        }
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        List<Post> posts=null;
        if(sort.equals("asc")) {
            posts = postRepository.findSearchResultASC(keyword);
        } else if(sort.equals("desc")) {
            posts = postRepository.findSearchResultDESC(keyword);
        }
        Map<Post,Users>postVsUsers=new HashMap<>();
        return findAllTags(postIdVsTags,model,posts,postVsUsers);
    }

//    public Model display(Model model, int start, int limit) {
////        System.out.println("limit" + limit);
////        System.out.println("Size" + result.size());
//        Set<Map.Entry<Post, String>> entrySet = result.entrySet();
//        List<Map.Entry<Post, String>> list = new ArrayList<>(entrySet);
//        Map<Post, String> tenPost = new LinkedHashMap<>();
//        if ((start + limit - 1) > result.size()) {
//            limit = result.size() % limit;
//        }
//
//        for (int i = start - 1; i < start + limit - 1; i++) {
//           // System.out.println("i=" + i);
//            tenPost.put(list.get(i).getKey(), list.get(i).getValue());
//        }
//        model = allModelData(model);
//        model.addAttribute("postIdVsTags", tenPost);
//        return model;
//    }

    private Model allModelData(Model model) {
        List<String> tags = tagService.findAllTag();
        List<String> publishAt = postRepository.findPublishedAt();
        model.addAttribute("tagName", tags);
        model.addAttribute("authors", usersRepository.findAll());
       // System.out.println(model.getAttribute("authors")+"author name ");
        model.addAttribute("publishAt", publishAt);
        return model;
    }

    @Override
    public Model dashboard(Model model) {
        List<Post> posts = findAll();
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth!=null) {
//            System.out.println("all the details");
//            if(auth.getAuthorities().toString().equals("Author")){
//                System.out.println("name" + auth.getName()+"  Author");
//                Users user=usersService.findByEmail(auth.getName());
//                model.addAttribute("authority",user.getId());
//            }else if(auth.getAuthorities().toString().equals("Admin")){
//                System.out.println("name" + auth.getName()+"  Admin");
//                model.addAttribute("authority","Admin");
//            }
//        }
        Map<Post,Users>postVsUsers=new HashMap<>();
        return findAllTags(postIdVsTags,model,posts,postVsUsers);
    }

    @Override
    public Model filterByPublishedAt(Model model, List<String> publishedAt, String sort) {
        if(sort=="null"){
            sort="asc";
        }
        List<Post>posts=postRepository.filterByPublishedAt(publishedAt);
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        Map<Post,Users>postVsUsers=new HashMap<>();
        return findAllTags(postIdVsTags,model,posts,postVsUsers);
    }

    @Override
    public Model filterByAuthor(Model model, List<Integer> author, String sort,String keyword) {
        if(sort=="null"){
            sort="asc";
        }
        System.out.println("filter by author");
        if(keyword==null){
            keyword="";
        }
        List<Post>posts=postRepository.filterByAuthor(author,keyword);
        System.out.println(posts.size()+"  author list size");
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        Map<Post,Users>postVsUsers=new HashMap<>();
        return findAllTags(postIdVsTags,model,posts,postVsUsers);
    }

    @Override
    public Model filterByTag(Model model, List<String> tag, String sort, String keyword) {
        if(sort=="null"){
            sort="asc";
        }
        System.out.println("filter by author");
        if(keyword==null){
            keyword="";
        }
        List<Post>posts=postRepository.filterByTag(tag,keyword);
        System.out.println(posts.size()+"  author list size");
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        Map<Post,Users>postVsUsers=new HashMap<>();
        return findAllTags(postIdVsTags,model,posts,postVsUsers);
    }

    @Override
    public Model filterByTagAndAuthor(Model model, List<String> tags, List<Integer> author, String sort, String keyword) {
        if(sort=="null"){
            sort="asc";
        }
        System.out.println("filter by author");
        if(keyword==null){
            keyword="";
        }
        List<Post>posts=postRepository.filterByTagAndAuthor(tags,author,keyword);
        System.out.println(posts.size()+"  author list size");
        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
        Map<Post,Users>postVsUsers=new HashMap<>();
        return findAllTags(postIdVsTags,model,posts,postVsUsers);
    }

//    public Model sort(Model model, String sort) {
//        List<Post>posts=null;
//        if(sort.equals("asc")){
//         posts=   postRepository.findByOrderByPublishedAtAsc();
//        }else if(sort.equals("desc")){
//            posts=   postRepository.findByOrderByPublishedAtAsc();
//        }
//        Map<Post, String> postIdVsTags = new LinkedHashMap<>();
//        return findAllTags(postIdVsTags,model,posts);
//    }

    @Override
    public Model savePost(Post post, String tag, Model model,String authorId) {
        String tags[] = tag.split(",");
        if(authorId!=null){
            Users user= usersService.getById(Integer.parseInt(authorId));
            post.setUser(user);
        }else if(authorId==null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users user=usersService.findByEmail(auth.getName());
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
}
