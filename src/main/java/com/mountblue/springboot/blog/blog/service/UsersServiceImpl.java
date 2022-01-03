package com.mountblue.springboot.blog.blog.service;

import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users findByEmailAndPassword(String email, String password) {


        return usersRepository.findByEmailAndPassword(email, password);
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
