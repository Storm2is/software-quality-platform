/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Tamer
 */
@Controller
public class ConnectController {

    @GetMapping("/")
    public String connect(Model model) {
        return "login";
    }

    @GetMapping("/disconnect")
    public String disconnect(Model model) {
        return "disconnect";
    }
}
