package com.netcracker.checkapp.server.service.userinfoservice;

import com.netcracker.checkapp.server.model.UserInfo;
import com.netcracker.checkapp.server.persistance.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private UserInfoRepository userInfoRepository;

    UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userInfoRepository.existsByLogin(username);
    }

    @Override
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo delete(String login) {
        return userInfoRepository.deleteUserInfoByLogin(login);
    }

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByLogin(username);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }
}
