package com.ysama.sampleapp.controller;

import com.ysama.sampleapp.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/get-user")
    public User getUser() {
        User user = new User().setId(1001).setName("ysama");

        return user;
    }
}
