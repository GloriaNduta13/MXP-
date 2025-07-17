/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.theme;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author admin
 */
@Named("themeBean")
@SessionScoped
public class ThemeBean implements Serializable {
    private String currentTheme = "bootstrap-blue-light";

    public void toggleTheme() {
        currentTheme = currentTheme.equals("bootstrap-blue-light") ? "bootstrap-blue-dark" : "bootstrap-blue-light";
    }

    public String getCurrentTheme() {
        return currentTheme;
    }
}


    

