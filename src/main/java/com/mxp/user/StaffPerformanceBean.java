/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.user;

import com.mxp.sql.sqlconnector;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.color.RGBAColor;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.options.BarOptions;
import software.xdev.chartjs.model.options.Plugins;
import software.xdev.chartjs.model.options.Title;

/**
 *
 * @author admin
 */

@Named("staffPerformanceBean")
@ViewScoped
public class StaffPerformanceBean implements Serializable {

    private String chartModel;

    public String getChartModel() {
        return chartModel != null ? chartModel.replaceAll("^\"|\"$", "") : "{}";
    }

    @PostConstruct
    public void init() {
        generateChart();
    }

    public void generateChart() {
        Map<String, Integer> resolvedTickets = new HashMap<>();
        Map<String, Integer> trainingsAttended = new HashMap<>();

        try (Connection conn = sqlconnector.getConnection()) {

            String ticketSql = "SELECT submitted_by, COUNT(*) AS count FROM helpdesk_tickets WHERE status = 'Resolved' GROUP BY submitted_by";
            try (PreparedStatement stmt = conn.prepareStatement(ticketSql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resolvedTickets.put(rs.getString("submitted_by"), rs.getInt("count"));
                }
            }

            String trainSql = """
                SELECT ms.name, COUNT(ts.id) AS sessions
                FROM mspace_staff ms
                JOIN training_sessions ts ON ms.department = ts.department
                GROUP BY ms.name
                """;
            try (PreparedStatement stmt = conn.prepareStatement(trainSql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trainingsAttended.put(rs.getString("name"), rs.getInt("sessions"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            chartModel = null;
            return;
        }

        Set<String> staffNames = new LinkedHashSet<>();
        staffNames.addAll(resolvedTickets.keySet());
        staffNames.addAll(trainingsAttended.keySet());

        BarDataset ticketDataset = new BarDataset()
                .setLabel("Tickets Resolved")
                .setBackgroundColor(new RGBAColor(75, 192, 192, 0.7));

        BarDataset trainingDataset = new BarDataset()
                .setLabel("Trainings Attended")
                .setBackgroundColor(new RGBAColor(255, 99, 132, 0.7));

        List<String> labels = new ArrayList<>();
        for (String name : staffNames) {
            labels.add(name);
            ticketDataset.addData(BigDecimal.valueOf(resolvedTickets.getOrDefault(name, 0)));
            trainingDataset.addData(BigDecimal.valueOf(trainingsAttended.getOrDefault(name, 0)));
        }

        chartModel = new BarChart()
                .setData(new BarData()
                        .addDataset(ticketDataset)
                        .addDataset(trainingDataset)
                        .setLabels(labels))
                .setOptions(new BarOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setPlugins(new Plugins()
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Staff Performance Report"))))
                .toJson();
    }
}
