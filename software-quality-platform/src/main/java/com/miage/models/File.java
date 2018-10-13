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

    @Column(name = "tags")
    private String tags;

    @Column(name = "pushTime")
    private Timestamp pushTime;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "statusId")
    private Integer statusId;

    public File() {
    }

    // Id is auto generated
    public File(String fileName, String extension, String filePath, String tags, Timestamp pushTime, Integer userId, Integer statusId) {
        this.fileName = fileName;
        this.extension = extension;
        this.filePath = filePath;
        this.tags = tags;
        this.pushTime = pushTime;
        this.userId = userId;
        this.statusId = statusId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

}
