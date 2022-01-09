package com.mountblue.springboot.blog.blog.api.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private int id;
    private String title;
    private String content;
    private Date publishedAt;
    private UserDTO author;
    private List<CommentDTO> comments;
    private List<TagDTO>tags;
    public PostDTO(int id, String title,String content, Date publishedAt, int userId,String userName,
                          String userEmail) {
        this.id = id;
        this.title=title;
        this.content = content;
        this.publishedAt = publishedAt;
        this.author = new UserDTO(userId,userName,userEmail);
    }
}
