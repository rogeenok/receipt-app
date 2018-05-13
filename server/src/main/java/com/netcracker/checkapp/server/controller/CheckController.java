package com.netcracker.checkapp.server.controller;

import com.netcracker.checkapp.server.model.check.Check;
import com.netcracker.checkapp.server.service.checkservice.CheckService;
import com.netcracker.checkapp.server.service.httpservice.HttpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/receipts")
public class CheckController {

    private CheckService checkService;
    private HttpService httpService;

    CheckController(CheckService checkService, HttpService httpService) {
        this.checkService = checkService;
        this.httpService = httpService;
    }

    @GetMapping(value = "/places")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<List<Check>> getNearPlace(@RequestParam("longitude") String longitude,
                                                    @RequestParam("latitude") String latitude,
                                                    @RequestParam("radius") String radius ) {

        return new ResponseEntity<List<Check>>(checkService.getNearPlacesAndChecks(longitude,
                latitude,radius),
                HttpStatus.OK);
    }

    @PostMapping()
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<?> load(@RequestBody Check check) {
        Check fullCheck = checkService.save(checkService.getCheck(check));

        return new ResponseEntity<>(httpService.createMessage("Чек успешно добавлен"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable String id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            if (checkService.existsByIdAndUsername(id, principal.getUsername())) {
                return new ResponseEntity<Check>(checkService.findById(id), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(httpService.createMessage("Отказано в доступе"), HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<Check>(checkService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<List<Check>> getByUserInfoLogin(@RequestBody(required = false) Map<String, String> body) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return new ResponseEntity<List<Check>>(checkService.findByUsername(principal.getUsername()),
                    HttpStatus.OK);
        }

        return new ResponseEntity<List<Check>>(checkService.findByUsername(body.get("login")),
                HttpStatus.OK);
    }

}