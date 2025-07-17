/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.login;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.mxp.sql.sqlconnector;

/**
 *
 * @author admin
 */
@Named("LoginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public String login() {
        try (Connection conn = sqlconnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                setRole(rs.getString("role"));
                lastLogin = LocalDateTime.now();
                return "Dashboard.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Invalid username or password"));
                return null;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Database error: " + e.getMessage()));
            return null;
        }
    }

    public LoginBean() {
        username = null;
        password = null;

    }

    private LocalDateTime lastLogin;

    public String getFormattedLastLogin() {
        return lastLogin != null ? lastLogin.format(DateTimeFormatter.ofPattern("dd MMM yyyy 'at' HH:mm")) : "Unavailable";
    }

    /**
     * @return the email
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the email to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login.xhtml?faces-redirect=true";
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    public void validateAdminAccess() {
        if (!"admin".equals(role)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("AccessDenied.xhtml"); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
