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
public class FileViewModel {

    private Integer fileid;
    private Integer userid;

    public FileViewModel() {
    }

    public FileViewModel(Integer fileid, Integer userid) {
        this.fileid = fileid;
        this.userid = userid;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}
