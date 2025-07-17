/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.training;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.mxp.login.LoginBean;

@Named("trainingBean")
@ViewScoped
public class TrainingBean implements Serializable {

    private List<TrainingSession> availableSessions = new ArrayList<>();
    private List<TrainingSession> mySessions = new ArrayList<>();

    private String selectedDepartment;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        System.out.println("🎯 Filter triggered for department: " + selectedDepartment);
        System.out.println("🎯 Results found: " + availableSessions.size());

    }

    public void filterSessions() {
        availableSessions = dao.filterSessions(selectedDepartment, startDate, endDate);
        System.out.println("🔥 Filtering triggered");
        System.out.println("Selected department: " + selectedDepartment);
        System.out.println("Start: " + startDate + ", End: " + endDate);
        System.out.println("Sessions after filter: " + availableSessions.size());

    }

    private TrainingDAO dao = new TrainingDAO();

    private TrainingSession newSession = new TrainingSession();

    @PostConstruct
    public void init() {
        availableSessions = dao.fetchAvailableSessions();
        mySessions = dao.getMyEnrollments(getCurrentLoggedInStaffName());
    }

    public TrainingSession getNewSession() {
        return newSession;
    }

    public void createSession() {
        dao.saveSession(newSession);
        availableSessions = dao.fetchAvailableSessions();
        newSession = new TrainingSession();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Session created successfully"));
    }

    private String getCurrentLoggedInStaffName() {
        LoginBean loginBean = FacesContext.getCurrentInstance()
                .getApplication()
                .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{LoginBean}", LoginBean.class);

        return loginBean.getUsername();

    }

    public void enroll(TrainingSession session) {
        String currentUser = getCurrentLoggedInStaffName();

        if (!dao.isAlreadyEnrolled(currentUser, session.getId())) {
            dao.enrollUser(currentUser, session.getId());
            mySessions = new ArrayList<>(dao.getMyEnrollments(currentUser));
            mySessions = dao.getMyEnrollments(currentUser);

            System.out.println("Enrolling as: " + currentUser);
            System.out.println("Enrolling: " + session.getTitle() + " for " + currentUser);
            System.out.println("My enrollments after update: " + mySessions.size());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Enrolled Successfully",
                            "You've enrolled in: " + session.getTitle()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Already Enrolled",
                            "You're already enrolled in: " + session.getTitle()));
        }
    }

    public List<TrainingSession> getAvailableSessions() {
        return availableSessions;
    }

    public List<TrainingSession> getMySessions() {
        return mySessions;
    }

    public int getNewTrainingInvites() {
        return dao.getUpcomingSessionsCount();
    }

}
