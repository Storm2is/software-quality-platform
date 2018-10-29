/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.viewmodels;

/**
 *
 * @author Tamer
 */
public class AnnotationViewModel {

    private Integer fileId;
    private Integer reviewerId;
    private String originContent;
    private String annotations;

    public AnnotationViewModel(Integer fileId, Integer reviewerId, String annotations) {
        this.fileId = fileId;
        this.reviewerId = reviewerId;
        this.annotations = annotations;
    }

    public AnnotationViewModel() {
    }

    public String getOriginContent() {
        return originContent;
    }

    public void setOriginContent(String originContent) {
        this.originContent = originContent;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }
}
