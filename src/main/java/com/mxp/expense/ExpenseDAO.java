/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.expense;

import com.mxp.expense.Expense;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Date;
import com.mxp.sql.sqlconnector;

/**
 *
 * @author admin
 */
public class ExpenseDAO {

    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses ORDER BY date DESC";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Expense e = new Expense();
                e.setId(rs.getInt("id"));
                e.setEmployeeName(rs.getString("employee_name"));
                e.setDepartment(rs.getString("department"));
                e.setExpenseTitle(rs.getString("expense_title"));
                e.setAmount(rs.getDouble("amount"));
                e.setDate(rs.getDate("date"));
                list.add(e);
            }

        } catch (SQLException e) {
        }

        return list;
    }

    public void save(Expense e) {
        String sql = "INSERT INTO expenses (employee_name, department, expense_title, amount, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getEmployeeName());
            ps.setString(2, e.getDepartment());
            ps.setString(3, e.getExpenseTitle());
            ps.setDouble(4, e.getAmount());
            ps.setDate(5, new java.sql.Date(e.getDate().getTime()));
            ps.executeUpdate();

        } catch (SQLException ex) {
        }
    }

    public Map<String, Double> getExpenseTotalsByDepartment() {
        Map<String, Double> summary = new LinkedHashMap<>();
        String sql = "SELECT department, SUM(amount) AS total FROM expenses GROUP BY department";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                summary.put(rs.getString("department"), rs.getDouble("total"));
            }

        } catch (SQLException e) {
        }

        return summary;
    }

    public Map<String, Double> getExpenseTotalsByDepartment(java.util.Date startDate, java.util.Date endDate) {
        Map<String, Double> summary = new LinkedHashMap<>();
        String sql = "SELECT department, SUM(amount) AS total FROM expenses WHERE date BETWEEN ? AND ? GROUP BY department";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    summary.put(rs.getString("department"), rs.getDouble("total"));
                }
            }

        } catch (SQLException e) {}

        return summary;
    }

}
