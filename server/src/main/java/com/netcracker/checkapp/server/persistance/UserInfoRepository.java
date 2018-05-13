package com.netcracker.checkapp.server.persistance;

import com.netcracker.checkapp.server.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    UserInfo findByLogin(String login);

    UserInfo deleteUserInfoByLogin(String login);

    boolean existsByLogin(String login);

}
