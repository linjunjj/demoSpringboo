package com.security;


import com.database.UserBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by linyuan on 2017/12/9.
 */
public class MyUserDetails implements UserDetails {

   private UserBean userBean;

    public MyUserDetails(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      String   roles = userBean.getRole();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles));
        return authorities;

//        List<GrantedAuthority> authorities = new ArrayList<>();
////        if (roles.size()>=1){
////            for (Role role : roles){
////                authorities.add(new SimpleGrantedAuthority(role.getName()));
////            }
//        authorities.add(new SimpleGrantedAuthority(userBean.getUsername()));
//            return authorities;
//
////        return AuthorityUtils.commaSeparatedStringToAuthorityList("");
    }

    @Override
    public String getPassword() {
        return userBean.getPassword();
    }

    @Override
    public String getUsername() {
        return userBean.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
