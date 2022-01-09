package com.mountblue.springboot.blog.blog.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private int id;
    private String name;
    private String email;
    private String commentContent;
    private Date createdAt;
    private Date updatedAt;
}
