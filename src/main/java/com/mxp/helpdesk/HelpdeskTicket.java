/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.helpdesk;

import java.sql.*;

/**
 *
 * @author admin
 */
public class HelpdeskTicket {
    private int id;
    private String subject;
    private String message;
    private String submittedBy;
    private String status;
    private Timestamp submittedAt;
    private int activity;

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
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the submittedBy
     */
    public String getSubmittedBy() {
        return submittedBy;
    }

    /**
     * @param submittedBy the submittedBy to set
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the submittedAt
     */
    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    /**
     * @param submittedAt the submittedAt to set
     */
    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    /**
     * @return the activity
     */
    public int getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(int activity) {
        this.activity = activity;
    }

   
}

