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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mikhail
 */
@RestController
@RequestMapping("/leaderBoard")
public class PointsRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUserPoints")
    public List<User> getUserPoints() {
        return userRepository.findAll();
    }

}
