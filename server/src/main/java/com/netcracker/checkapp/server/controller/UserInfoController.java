package com.netcracker.checkapp.server.controller;

import com.netcracker.checkapp.server.model.UserInfo;
import com.netcracker.checkapp.server.service.httpservice.HttpService;
import com.netcracker.checkapp.server.service.userinfoservice.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserInfoController {

    private UserInfoService userInfoService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private HttpService httpService;

    UserInfoController(UserInfoService userInfoService, HttpService httpService) {
        this.userInfoService = userInfoService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.httpService = httpService;
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<?> addAdmin(@RequestBody Map<String, String> body) {
        UserInfo userInfo = new UserInfo();

        userInfo.setLogin(body.get("login"));
        if (userInfoService.existsByUsername(userInfo.getLogin())) {
            return new ResponseEntity<>(httpService.createMessage("Этот логин уже занят"), HttpStatus.CONFLICT);
        }
        userInfo.setPwd(bCryptPasswordEncoder.encode(body.get("pwd")));
        userInfo.setRole("ROLE_ADMIN");
        userInfoService.save(userInfo);

        return new ResponseEntity<>(httpService.createMessage("User created"), HttpStatus.CREATED);
    }

    @DeleteMapping
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> delete(@RequestBody(required = false) Map<String, String> body) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            userInfoService.delete(principal.getUsername());
            return new ResponseEntity<>(httpService.createMessage("Удаление прошло успешно"), HttpStatus.NO_CONTENT);
        }
        userInfoService.delete(body.get("login"));

        return new ResponseEntity<>(httpService.createMessage("Удаление прошло успешно"), HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<UserInfo> get(@RequestBody(required = false) Map<String, String> body) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) || body.isEmpty()) {
            return new ResponseEntity<>(userInfoService.findByUsername(principal.getUsername()), HttpStatus.OK);
        }

        return new ResponseEntity<>(userInfoService.findByUsername(body.get("login")), HttpStatus.OK);
    }

    @GetMapping("/all")
    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<UserInfo>> getAll() {
        return new ResponseEntity<>(userInfoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/current")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getCurrent() {
        Map<String, String> map = new HashMap<>();

        map.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
