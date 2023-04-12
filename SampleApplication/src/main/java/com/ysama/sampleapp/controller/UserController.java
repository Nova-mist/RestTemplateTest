package com.ysama.sampleapp.controller;

import com.ysama.sampleapp.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/get-user")
    public User getUser() {
        User user = new User().setId(1001).setName("ysama");

        return user;
    }

    @PostMapping("/add-user")
    public User addUser(@RequestBody User user) {
        return user;
    }
}
