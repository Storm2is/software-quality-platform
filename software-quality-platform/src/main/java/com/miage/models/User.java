/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.models;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Tamer
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user")
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

    @OneToOne
    @JoinColumn(name = "userId")
    private Point point;

    public User() {
        this.Username = "";
        this.email = "";
        this.password = "";
    }

    // Id is auto generated
    public User(String Username, String email, String password, Point point) {
        this.Username = Username;
        this.email = email;
        this.password = password;
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
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
