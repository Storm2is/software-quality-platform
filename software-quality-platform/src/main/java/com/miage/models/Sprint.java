/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.models;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author kimphuong
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "sprint")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sprintId")
    private Integer sprintId;

    @Column(name = "startTime")
    private Timestamp startTime;

    @Column(name = "endTime")
    private Timestamp endTime;

    @Column(name = "goal")
    private int goal;

    // Id is auto generated
    public Sprint(Timestamp startTime, Timestamp endTime, int goal) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.goal = goal;
    }

    public Sprint() {
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

}
