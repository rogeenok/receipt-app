package com.netcracker.checkapp.server.service.userinfoservice;

import com.netcracker.checkapp.server.model.UserInfo;

import java.util.List;

public interface UserInfoService {
    boolean existsByUsername(String username);

    void save(UserInfo userInfo);

    UserInfo delete(String login);

    UserInfo findByUsername(String username);

    List<UserInfo> findAll();
}
