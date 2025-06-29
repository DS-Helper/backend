package com.project.ds_helper.domain.user.entity;

import com.project.ds_helper.domain.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    public String getId() {
        return user.getId();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public UserRole getRole(){
        return user.getRole();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }




    /** 
     * 사용하지 않는 메소드
     * **/
    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}