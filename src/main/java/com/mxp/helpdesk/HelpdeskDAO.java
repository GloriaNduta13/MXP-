/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.helpdesk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mxp.sql.sqlconnector;

/**
 *
 * @author admin
 */
public class HelpdeskDAO {

    public void save(HelpdeskTicket ticket) {
        String sql = "INSERT INTO helpdesk_tickets (subject, message, submitted_by) VALUES (?, ?, ?)";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ticket.getSubject());
            ps.setString(2, ticket.getMessage());
            ps.setString(3, ticket.getSubmittedBy());
            ps.executeUpdate();

        } catch (SQLException e) {}
    }

    public List<HelpdeskTicket> getAll() {
        List<HelpdeskTicket> list = new ArrayList<>();
        String sql = "SELECT * FROM helpdesk_tickets ORDER BY submitted_at DESC";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HelpdeskTicket t = new HelpdeskTicket();
                t.setId(rs.getInt("id"));
                t.setSubject(rs.getString("subject"));
                t.setMessage(rs.getString("message"));
                t.setSubmittedBy(rs.getString("submitted_by"));
                t.setStatus(rs.getString("status"));
                t.setSubmittedAt(rs.getTimestamp("submitted_at"));
                String status = t.getStatus();

                switch (status.toLowerCase()) {
                    case "inprogress":
                    case "in progress":
                        t.setActivity(50);
                        break;
                    case "open":
                        t.setActivity(30);
                        break;
                    case "resolved":
                        t.setActivity(100);
                        break;
                    case "closed":
                        t.setActivity(0);
                        break;
                    default:
                        t.setActivity(0);
                        break;
                }

                list.add(t);
            }

        } catch (SQLException e) {}  
        return list;
    }

    public void delete(int id) {
        String sql = "DELETE FROM helpdesk_tickets WHERE id = ?";
        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {}
    }

    public void updateStatus(int ticketId, String newStatus) {
        String sql = "UPDATE helpdesk_tickets SET status = ? WHERE id = ?";
        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, ticketId);
            ps.executeUpdate();
        } catch (SQLException e) {}
        
    }

    public void updateSubject(int id, String newSubject) {
        String sql = "UPDATE helpdesk_tickets SET subject = ? WHERE id = ?";
        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newSubject);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {}
        
    }

}
