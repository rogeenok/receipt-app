package com.netcracker.checkapp.server.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userInfo")
@Data
public class UserInfo {

    @Id
    private String id;
    private String login;
    private String pwd;
    private String role;
}
