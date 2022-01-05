package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Model findAllUsers(Model model) {
        List<Users> users = usersRepository.findAll();
        model.addAttribute("authors", users);
        return model;
    }

    @Override
    public void save(Users user) {
        usersRepository.save(user);
    }

    @Override
    public Users getById(int id) {
        return usersRepository.getById(id);
    }
}
