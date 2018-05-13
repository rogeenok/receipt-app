package com.netcracker.checkapp.server.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userInfo")
@Data
public class UserInfo {

    private String login;
    private String pwd;
    private String role;
}
