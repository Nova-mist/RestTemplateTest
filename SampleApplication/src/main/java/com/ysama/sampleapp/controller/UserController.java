package com.ysama.sampleapp.controller;

import com.ysama.sampleapp.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping("/add-user-1")
    public ResponseEntity<User> addUser1(@Nullable @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("location", "https://bilibili.com")
                .body(user);
    }

    @PostMapping("login")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password) {
        return String.format("Name: %s, Password: %s", name, password);
    }

    // update, not restful yet...
    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user) {
        return user;
    }

    // delete
    @DeleteMapping("/delete-user")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        return ResponseEntity.ok(user);
    }

}
