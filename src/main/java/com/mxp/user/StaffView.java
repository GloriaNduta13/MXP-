/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.user;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author admin
 */
@Named("staffView")
@ViewScoped
public class StaffView implements Serializable {

    private final StaffDAO dao = new StaffDAO();

    public int getTotalStaff() {
        return dao.getStaffCount();
    }

    public int getDepartmentCount() {
        return dao.getDepartmentCount();
    }
}
