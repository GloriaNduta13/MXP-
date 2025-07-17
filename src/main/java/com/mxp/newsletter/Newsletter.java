/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.newsletter;

import java.sql.*;

/**
 *
 * @author admin
 */
public class Newsletter {
    private int id;
    private String title;
    private String filename;
    private Timestamp uploadedAt;
    private String coverImage;


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the uploadedAt
     */
    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    /**
     * @param uploadedAt the uploadedAt to set
     */
    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    /**
     * @return the coverImage
     */
    public String getCoverImage() {
        return coverImage;
    }

    /**
     * @param coverImage the coverImage to set
     */
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}

