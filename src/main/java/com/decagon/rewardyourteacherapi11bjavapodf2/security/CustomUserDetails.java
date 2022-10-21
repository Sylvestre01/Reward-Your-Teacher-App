package com.decagon.rewardyourteacherapi11bjavapodf2.security;

import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private  String email;
    private String password;

    private boolean isEnabled;

    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.isAccountVerified();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String userAuthority : user.getRole().toString().split(",")) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userAuthority);
            grantedAuthorities.add(simpleGrantedAuthority);}
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isEnabled;
    }
}
