/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.employee;

import com.mxp.employee.EmployeeBean;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import com.mxp.sql.sqlconnector;

/**
 *
 * @author admin
 */
@Named("employeeView")
@SessionScoped


public class EmployeeView implements Serializable {

    private List<EmployeeBean> allEmployees;
    private List<EmployeeBean> selectedEmployees = new ArrayList<>();
    private EmployeeBean selectedEmployee;

    @PostConstruct
    public void init() {
        allEmployees = loadEmployeesFromDatabase();
    }

    public List<EmployeeBean> getAllEmployees() {
        return allEmployees;
    }

    private List<EmployeeBean> loadEmployeesFromDatabase() {
        List<EmployeeBean> employees = new ArrayList<>();

        String sql = "SELECT name, age, department, salary, email FROM mspace_staff";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EmployeeBean emp = new EmployeeBean(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getInt("salary"),
                        rs.getString("email")
                );  
                employees.add(emp);
            }

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database Error", e.getMessage()));
        }

        return employees;
    }

    public void saveEmployee() {
        if (selectedEmployee != null) {

            try (Connection conn = sqlconnector.getConnection()) {
                String checkSql = "SELECT COUNT(*) FROM mspace_staff WHERE email = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, selectedEmployee.getEmail());
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    String insertSql = "INSERT INTO mspace_staff (name, age, department, salary, email) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                    insertStmt.setString(1, selectedEmployee.getName());
                    insertStmt.setInt(2, selectedEmployee.getAge());
                    insertStmt.setString(3, selectedEmployee.getDepartment());
                    insertStmt.setInt(4, selectedEmployee.getSalary());
                    insertStmt.setString(5, selectedEmployee.getEmail());
                    insertStmt.executeUpdate();
                } else {
                    String updateSql = "UPDATE mspace_staff SET name=?, age=?, department=?, salary=? WHERE email=?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, selectedEmployee.getName());
                    updateStmt.setInt(2, selectedEmployee.getAge());
                    updateStmt.setString(3, selectedEmployee.getDepartment());
                    updateStmt.setInt(4, selectedEmployee.getSalary());
                    updateStmt.setString(5, selectedEmployee.getEmail());
                    updateStmt.executeUpdate();
                }
            } catch (SQLException e) {}
            allEmployees = loadEmployeesFromDatabase();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Employee saved successfully!"));

        }
        System.out.println("Saving employee: " + selectedEmployee.getName());
    }

    /*private List<EmployeeBean> selectedEmployees = new ArrayList<>()*/
           
            
    public List<EmployeeBean> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<EmployeeBean> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public EmployeeBean getSelectedEmployee() {
    return selectedEmployee;
}

public void setSelectedEmployee(EmployeeBean selectedEmployee) {
    this.selectedEmployee = selectedEmployee;
}

    
    public void deleteEmployee() {
        if (selectedEmployee != null) {
            try (Connection conn = sqlconnector.getConnection()) {
                String deleteSql = "DELETE FROM mspace_staff WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(deleteSql);
                stmt.setString(1, selectedEmployee.getEmail());
                stmt.executeUpdate();
            } catch (SQLException e) {
               
            }
            selectedEmployee = null;
        }
    }

    public void deleteSelectedEmployees() {
        try (Connection conn = sqlconnector.getConnection()) {
            String deleteSql = "DELETE FROM mspace_staff WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteSql);
            for (EmployeeBean emp : selectedEmployees) {
                stmt.setString(1, emp.getEmail());
                stmt.executeUpdate();
            }
            selectedEmployees.clear();
        } catch (SQLException e) {
        }
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEmployees()) {
            return selectedEmployees.size() > 1
                    ? selectedEmployees.size() + " employees selected"
                    : "1 employee selected";
        }
        return "Delete";
    }

    public boolean hasSelectedEmployees() {
        return selectedEmployees != null && !selectedEmployees.isEmpty();
    }

    public void openNew() {
        selectedEmployee = new EmployeeBean("", 18, "", 0, "");
        System.out.println("Initialized a new employee");
    }

}
