/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.training;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.mxp.sql.sqlconnector;

public class TrainingDAO {

    public List<TrainingSession> fetchAvailableSessions() {
        List<TrainingSession> list = new ArrayList<>();
        try (Connection conn = sqlconnector.getConnection()) {
            String sql = "SELECT id, title, department, session_date FROM training_sessions";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TrainingSession ts = new TrainingSession();
                ts.setId(rs.getInt("id"));
                ts.setTitle(rs.getString("title"));
                ts.setDepartment(rs.getString("department"));
                ts.setDate(rs.getDate("session_date").toLocalDate());

                list.add(ts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TrainingSession> filterSessions(String dept, LocalDate start, LocalDate end) {
        List<TrainingSession> sessions = new ArrayList<>();
        String sql = "SELECT id, title, department, session_date FROM training_sessions WHERE 1=1";
        List<Object> params = new ArrayList<>();
        

        if (dept != null && !dept.isEmpty()) {
            sql += " AND department = ?";
            params.add(dept);
        }
        if (start != null) {
            sql += " AND session_date >= ?";
            params.add(Date.valueOf(start));
        }
        if (end != null) {
            sql += " AND session_date <= ?";
            params.add(Date.valueOf(end));
        }

        sql += " ORDER BY session_date ASC";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TrainingSession ts = new TrainingSession();
                    ts.setId(rs.getInt("id"));
                    ts.setTitle(rs.getString("title"));
                    ts.setDate(rs.getDate("session_date").toLocalDate());
                    ts.setDepartment(rs.getString("department"));
                    sessions.add(ts);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return sessions;
    }

    public void saveSession(TrainingSession session) {
        String query = "INSERT INTO training_sessions (title, department, session_date) VALUES (?, ?, ?)";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, session.getTitle());
            stmt.setString(2, session.getDepartment());
            stmt.setDate(3, Date.valueOf(session.getDate()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUpcomingSessionsCount() {
        String sql = "SELECT COUNT(*) FROM training_sessions WHERE session_date >= CURDATE()";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isAlreadyEnrolled(String staffName, int sessionId) {
        String sql = "SELECT COUNT(*) FROM training_enrollments WHERE staff_name = ? AND session_id = ?";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, staffName);
            stmt.setInt(2, sessionId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TrainingSession> getMyEnrollments(String staffName) {
        List<TrainingSession> list = new ArrayList<>();
        String sql = """
        SELECT ts.id, ts.title, ts.session_date, ts.department
        FROM training_enrollments te
        JOIN training_sessions ts ON te.session_id = ts.id
        WHERE te.staff_name = ?
        ORDER BY ts.session_date ASC """;

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, staffName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new TrainingSession(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getDate("session_date").toLocalDate(),
                            rs.getString("department")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void enrollUser(String staffName, int sessionId) {
        String sql = "INSERT INTO training_enrollments (staff_name, session_id) VALUES (?, ?)";

        try (Connection conn = sqlconnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, staffName);
            stmt.setInt(2, sessionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

}
