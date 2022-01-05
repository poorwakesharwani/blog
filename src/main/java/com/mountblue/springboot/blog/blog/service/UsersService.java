package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Users;
import org.springframework.ui.Model;

import java.util.List;

public interface UsersService {

    Users findByEmail(String email);

    Model findAllUsers(Model model);

    void save(Users user);

    Users getById(int id);
}
