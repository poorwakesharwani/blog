package com.mountblue.springboot.blog.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder getPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(getUserDetailsService());
        dao.setPasswordEncoder(getPassword());
        return dao;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/newpost/**").hasAnyAuthority("Author", "Admin")
//                .and().formLogin().loginPage("/login")
//                .loginProcessingUrl("/checkingcredential").permitAll().and().logout().permitAll()
//                .and().csrf().disable();
        http.csrf().disable().authorizeRequests()
                .antMatchers("/newpost/**", "/updatecomment/", "/deletecomment",
                        "/updatepost/", "/deletepost", "/api/newpost/*","/api/post/*", "/api/comment/*")
                .hasAnyAuthority("Admin", "Author")
                .antMatchers("/authenticate").permitAll().and().formLogin().loginPage("/login").
                loginProcessingUrl("/checkingcredential").permitAll().and().logout().permitAll();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
