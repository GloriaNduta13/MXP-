/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.user;

import com.mxp.sql.sqlconnector;
import java.sql.*;


/**
 *
 * @author admin
 */
public class StaffDAO {

    public int getStaffCount() {
        String sql = "SELECT COUNT(*) FROM mspace_staff";
        try (Connection conn = sqlconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getDepartmentCount() {
        String sql = "SELECT COUNT(DISTINCT department) FROM mspace_staff";
        try (Connection conn = sqlconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
