package com.netcracker.checkapp.server.service.security;

import com.netcracker.checkapp.server.model.UserInfo;
import com.netcracker.checkapp.server.persistance.UserInfoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserInfoRepository userInfoRepository;

    UserDetailsServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByLogin(login);
        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
        UserDetails userDetails = (UserDetails) new User(userInfo.getLogin(), userInfo.getPwd(),
                Arrays.asList(authority));

        return userDetails;
    }
}
