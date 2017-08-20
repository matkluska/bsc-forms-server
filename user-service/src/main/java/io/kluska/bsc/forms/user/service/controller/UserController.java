package io.kluska.bsc.forms.user.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/{userId}")
    public String findUserById(@PathVariable String userId) {
        return "Info about user: " + userId;
    }
}
