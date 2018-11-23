/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mikhail
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/member")
    public String member_dashboard(Model model) throws IOException {
        return "member_dashboard";
    }
    
    @GetMapping("/master")
    public String master_dashboard(Model model) throws IOException {
        return "sm_dashboard";
    }
}
