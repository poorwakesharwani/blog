package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Users;

public interface UsersService {

    Users findByEmailAndPassword(String email, String password);

    void save(Users user);

    Users getById(int id);
}
