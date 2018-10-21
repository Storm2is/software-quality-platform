/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Tamer
 */
@Entity // This tells Hibernate to make a table out of this class
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "username")
    private String Username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public User() {
        this.Username = "";
        this.email = "";
        this.password = "";
    }

    // Id is auto generated
    public User(String Username, String email, String password) {
        this.Username = Username;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return userId;
    }

    public void setId(Integer id) {
        this.userId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
