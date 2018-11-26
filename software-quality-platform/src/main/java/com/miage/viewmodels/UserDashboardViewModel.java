/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.viewmodels;

import com.miage.models.Quality;
import com.miage.models.Sprint;
import java.util.List;

/**
 *
 * @author Tamer
 */
public class UserDashboardViewModel {

    Sprint period;
    Integer uploadedFiles;
    Integer reviewedFiles;
    List<String> badges;
    List<Quality> quality;
    List<SprintProgressViewModel> sprintGoal;
    List<SprintProgressViewModel> acceptedCode;

    public UserDashboardViewModel(Sprint period, Integer uploadedFiles, Integer reviewedFiles, List<String> badges, List<Quality> quality, List<SprintProgressViewModel> sprintGoal, List<SprintProgressViewModel> acceptedCode) {
        this.period = period;
        this.uploadedFiles = uploadedFiles;
        this.reviewedFiles = reviewedFiles;
        this.badges = badges;
        this.quality = quality;
        this.sprintGoal = sprintGoal;
        this.acceptedCode = acceptedCode;
    }

    public UserDashboardViewModel() {
    }

    public Sprint getPeriod() {
        return period;
    }

    public void setPeriod(Sprint period) {
        this.period = period;
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

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }

    public List<Quality> getQuality() {
        return quality;
    }

    public void setQuality(List<Quality> quality) {
        this.quality = quality;
    }

    public List<SprintProgressViewModel> getSprintGoal() {
        return sprintGoal;
    }

    public void setSprintGoal(List<SprintProgressViewModel> sprintGoal) {
        this.sprintGoal = sprintGoal;
    }

    public List<SprintProgressViewModel> getAcceptedCode() {
        return acceptedCode;
    }

    public void setAcceptedCode(List<SprintProgressViewModel> acceptedCode) {
        this.acceptedCode = acceptedCode;
    }

}
