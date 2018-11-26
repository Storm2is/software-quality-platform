/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.viewmodels;

import com.miage.models.User;
import java.util.List;

/**
 *
 * @author Tamer
 */
public class UserViewModel {

    private User user;
    private Integer uploadedFiles;
    private Integer reviewedFiles;
    private Integer points;
    private List<String> badges;

    public UserViewModel(User user, Integer uploadedFiles, Integer reviewedFiles, Integer points, List<String> badges) {
        this.user = user;
        this.uploadedFiles = uploadedFiles;
        this.reviewedFiles = reviewedFiles;
        this.points = points;
        this.badges = badges;
    }

    public UserViewModel() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(Integer uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public Integer getReviewedFiles() {
        return reviewedFiles;
    }

    public void setReviewedFiles(Integer reviewedFiles) {
        this.reviewedFiles = reviewedFiles;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }

}
