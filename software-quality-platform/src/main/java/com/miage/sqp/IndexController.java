/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sqp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Tamer
 */
@Controller
public class IndexController {

    @GetMapping("/home")
    public String index(Model model) {
        return "index";
    }

    // To test the logout page
    /* @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }
     */
}
