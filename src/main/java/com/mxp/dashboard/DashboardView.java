/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.dashboard;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author admin
 */
@Named("dashboardView")
@SessionScoped
public class DashboardView implements Serializable {

    private List<String> mxpImages;

    @PostConstruct
    public void init() {
        mxpImages = List.of(
                "1.png",
                "2.png",
                "3.png",
                "4.png",
                "5.png",
                "6.png"
        );
    }

    /**
     * @return the mxpImages
     */
    public List<String> getMxpImages() {
        return mxpImages;
    }
 }
