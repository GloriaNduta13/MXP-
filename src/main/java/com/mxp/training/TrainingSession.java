/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.training;

import java.time.LocalDate;

public class TrainingSession {

    private int id;
    private String title;
    private LocalDate date;
    private String department;

    public TrainingSession() {
    }

    public TrainingSession(String title, LocalDate date, String department) {
        this.title = title;
        this.date = date;
        this.department = department;
    }

    public TrainingSession(int id, String title, LocalDate date, String department) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
