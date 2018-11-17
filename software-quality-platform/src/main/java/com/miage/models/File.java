/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import static org.h2.util.DateTimeUtils.MILLIS_PER_DAY;

/**
 *
 * @author Tamer
 */
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fileId")
    private Integer fileId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "filePath")
    private String filePath;

    @Column(name = "fileLength")
    private Integer fileLength;

    @Column(name = "tags")
    private String tags;

    @Column(name = "pushTime")
    private Timestamp pushTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "statusId")
    private Status status;

    public File() {
    }

    // Id is auto generated
    public File(String fileName, String extension, String filePath, Integer fileLength, String tags, Timestamp pushTime, Status status, User user) {
        this.fileName = fileName;
        this.extension = extension;
        this.filePath = filePath;
        this.fileLength = fileLength;
        this.tags = tags;
        this.pushTime = pushTime;
        this.status = status;
        this.user = user;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileLength() {
        return fileLength;
    }

    public void setFileLength(Integer fileLength) {
        this.fileLength = fileLength;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Timestamp getPushTime() {
        return pushTime;
    }

    public void setPushTime(Timestamp pushTime) {
        this.pushTime = pushTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String Duration() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Date date1 = new Date(now.getTime());
        Date date2 = new Date(this.getPushTime().getTime());
        long millseconds = Math.abs(date1.getTime() - date2.getTime());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millseconds);
        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;

        String duration = "";

        if (days != 0) {
            duration += days + " Days, ";
        }

        if (hours != 0) {
            duration += hours + " Hours & ";
        }

        duration += minutes + " Minutes ";
        return duration;
    }
}
