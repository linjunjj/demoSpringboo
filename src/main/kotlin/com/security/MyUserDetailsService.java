package com.security;

import com.database.UserBean;
import com.database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        //从数据库中加载用户对象
//        Optional<User> user = userRepository.findByName(s);
//        //调试用，如果值存在则输出下用户名与密码
//        user.ifPresent((value)-> System.out.println("用户名:"+value.getName()+" 用户密码："+value.getPassword()));
//        //若值不再则返回null
//        UserBean userBean=userService.getUser(s);
        UserBean userBean=userService.getUser(s);
        if (userBean==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = createAuthorities(userBean.getRole());

        return new User(userBean.getUsername(), userBean.getPassword(), simpleGrantedAuthorities);
    }
    private List<SimpleGrantedAuthority> createAuthorities(String roleStr){
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
}
