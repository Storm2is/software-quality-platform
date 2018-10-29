/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.models;

import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Tamer
 */
@Entity
public class Annotation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "annotationId")
    private Integer annotationId;

    @Column(name = "lineNb")
    private Integer lineNb;

    @Column(name = "comment")
    private String comment;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "isResolved")
    private Boolean isResolved;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fileId")
    private File file;

    public Annotation(Integer annotationId, Integer lineNb, String comment, Timestamp time, Boolean isResolved, User user, File file) {
        this.annotationId = annotationId;
        this.lineNb = lineNb;
        this.comment = comment;
        this.time = time;
        this.isResolved = isResolved;
        this.user = user;
        this.file = file;
    }

    public Annotation() {
    }

    public Integer getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(Integer annotationId) {
        this.annotationId = annotationId;
    }

    public Integer getLineNb() {
        return lineNb;
    }

    public void setLineNb(Integer lineNb) {
        this.lineNb = lineNb;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
