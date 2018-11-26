/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.viewmodels;

import com.miage.models.Quality;
import com.miage.models.Sprint;
import com.miage.models.User;
import java.util.List;

/**
 *
 * @author Tamer
 */
public class SMDashboardViewModel {

    Sprint period;
    List<Quality> quality;
    List<SprintProgressViewModel> sprintGoal;
    List<SprintProgressViewModel> acceptedCode;
    List<UserViewModel> users;

    public SMDashboardViewModel(Sprint period, List<Quality> quality, List<SprintProgressViewModel> sprintGoal, List<SprintProgressViewModel> acceptedCode, List<UserViewModel> users) {
        this.period = period;
        this.quality = quality;
        this.sprintGoal = sprintGoal;
        this.acceptedCode = acceptedCode;
        this.users = users;
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

    public SMDashboardViewModel() {
    }

    public Sprint getPeriod() {
        return period;
    }

    public void setPeriod(Sprint period) {
        this.period = period;
    }

    public List<UserViewModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserViewModel> users) {
        this.users = users;
    }
}
