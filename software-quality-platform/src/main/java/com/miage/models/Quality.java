/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Tamer
 */
@Entity
@Table(name = "quality")
public class Quality {

    @Id
    @Column(name = "qualityId")
    private Integer qualityId;

    @Column(name = "sprintId")
    private Integer sprintId;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private Integer value;

    public Quality() {
    }

    public Quality(Integer qualityId, Integer sprintId, String label, Integer value) {
        this.qualityId = qualityId;
        this.sprintId = sprintId;
        this.label = label;
        this.value = value;
    }

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
