package com.mountblue.springboot.blog.blog.config;

import com.mountblue.springboot.blog.blog.model.Users;
import com.mountblue.springboot.blog.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
               Users user = usersRepository.findByEmail(email);
               if(user==null){
                   throw new UsernameNotFoundException("No Author");
               }else{
                   return  new CustomUserDetail(user);
               }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
