package com.mountblue.springboot.blog.blog.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDTO {

    private int id;
    private String title;
    private String excerpt;
    private Date publishedAt;
    private UserDTO userDTO;
    private List<TagDTO> tags;

    public PostSummaryDTO(int id, String title, String excerpt, Date publishedAt, int userId, String userName,
                          String userEmail) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.publishedAt = publishedAt;
        this.userDTO = new UserDTO(userId, userName, userEmail);
    }
}
