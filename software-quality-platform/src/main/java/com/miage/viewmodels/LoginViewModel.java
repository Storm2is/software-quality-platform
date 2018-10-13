/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.viewmodels;

import com.miage.models.User;
import com.miage.repositories.UserRepository;

/**
 *
 * @author kimphuong
 */
public class LoginViewModel {

    private String UserName;
    private String Password;
    private UserRepository userRepository;

    public LoginViewModel(String UserName, String Password) {
        this.UserName = UserName;
        this.Password = Password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean Validate(LoginViewModel login) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername() == login.getUserName() && user.getPassword() == login.getPassword()) {
                return true;
            }
        }
        return false;
    }
}
