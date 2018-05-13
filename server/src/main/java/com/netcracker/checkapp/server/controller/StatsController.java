package com.netcracker.checkapp.server.controller;

import com.netcracker.checkapp.server.service.statservice.StatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/stats")
public class StatsController {

    private StatService statService;

    StatsController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<?> getStats() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(statService.getStats(), HttpStatus.OK);
    }
}
