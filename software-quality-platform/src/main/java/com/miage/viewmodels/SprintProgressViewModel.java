/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.viewmodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

/**
 *
 * @author Tamer
 */
public class SprintProgressViewModel {

    private Integer value;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Timestamp date;

    public SprintProgressViewModel() {
    }

    public SprintProgressViewModel(Integer value, Timestamp date) {
        this.value = value;
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
