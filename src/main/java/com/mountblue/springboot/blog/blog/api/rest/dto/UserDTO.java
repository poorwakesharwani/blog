package com.mountblue.springboot.blog.blog.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private  int id;
    private String name;
    private  String email;
}
