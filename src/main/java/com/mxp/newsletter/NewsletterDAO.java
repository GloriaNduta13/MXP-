/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.newsletter;

import com.mxp.newsletter.Newsletter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mxp.sql.sqlconnector;

/**
 *
 * @author admin
 */
public class NewsletterDAO {

    public void save(Newsletter n) {
        String sql = "INSERT INTO newsletters (title, filename, cover_image) VALUES (?, ?, ?)";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, n.getTitle());
            ps.setString(2, n.getFilename());
            ps.setString(3, n.getCoverImage());
            ps.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public List<Newsletter> getAll() {
        List<Newsletter> list = new ArrayList<>();
        String sql = "SELECT * FROM newsletters ORDER BY uploaded_at DESC";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Newsletter n = new Newsletter();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setFilename(rs.getString("filename"));
                n.setUploadedAt(rs.getTimestamp("uploaded_at"));
                n.setCoverImage(rs.getString("cover_image"));

                list.add(n);
            }

        } catch (SQLException e) {
        }

        return list;
    }

    public String getLatestTitle() {
        String sql = "SELECT title FROM newsletters ORDER BY date DESC LIMIT 1";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getString("title") : "No newsletters yet";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching title";
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM newsletters WHERE id = ?";
        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public List<Newsletter> searchByKeyword(String keyword) {
    List<Newsletter> results = new ArrayList<>();
    String sql = "SELECT * FROM newsletters WHERE title LIKE ? ORDER BY uploaded_at DESC";

    try (Connection conn = sqlconnector.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Newsletter n = new Newsletter();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setFilename(rs.getString("filename"));
                n.setUploadedAt(rs.getTimestamp("uploaded_at"));
                n.setCoverImage(rs.getString("cover_image"));
                results.add(n);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return results;
}

}
