package com.decagon.rewardyourteacherapi11bjavapodf2.security;

import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        CustomUserDetails userDetails =  null;
        if (user != null){
            userDetails = new CustomUserDetails(user);
        }
        return userDetails;
    }
}
