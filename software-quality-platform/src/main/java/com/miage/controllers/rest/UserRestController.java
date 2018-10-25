/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers.rest;

import com.miage.models.User;
import com.miage.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rayhane
 */
@RestController
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User show(@PathVariable String id) {
        int userId = Integer.parseInt(id);
        return userRepository.findById(userId).get();
    }

    @GetMapping("/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name,
            @RequestParam String email) {
        User n = new User();
        n.setUsername(name);
        n.setEmail(email);
        userRepository.save(n);

        return "Saved";
    }

}
