package com.mountblue.springboot.blog.blog.config;

import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email+" loadUserByUsername start");
        try {
            Users user = usersRepository.findByEmail(email);
            if (user == null) {
                System.out.println(email+" loadUserByUsername middle");
                throw new UsernameNotFoundException("No Author");
            } else {
                System.out.println(email+" loadUserByUsername create user");
                return new CustomUserDetail(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
