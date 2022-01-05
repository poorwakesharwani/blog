package com.mountblue.springboot.blog.blog.repository;

import com.mountblue.springboot.blog.blog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {


    Users findByEmail(String email);
}
