/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.training;

import com.mxp.training.TrainingDAO;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author admin
 */
@Named("trainingView")
@ViewScoped
public class TrainingView implements Serializable {

    private final TrainingDAO dao = new TrainingDAO();

    public int getUpcomingTrainings() {
        return dao.getUpcomingSessionsCount();
    }

}
