/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import com.miage.models.User;
import com.miage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Rayhane
 */
@Controller
public class ConnectConrtoller {

    @Autowired
    private UserRepository userRepository;
    
    private User connectedUser = new User();

    /*@PostMapping("/")
    public String handlePostRequest(@RequestBody String id) {
        int userId = Integer.parseInt(id);
        this.connectedUser = userRepository.findById(userId).get();
        return "redirect:/PushCode";
    }*/

    @GetMapping("/uplaod")
    public String handleGetRequest(Model model) {
        model.addAttribute("user", connectedUser);
        return "PushCode";
    }
}
