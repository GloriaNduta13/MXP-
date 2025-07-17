/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.report;

import com.mxp.expense.ExpenseDAO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.color.RGBAColor;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.options.BarOptions;
import software.xdev.chartjs.model.options.Plugins;
import software.xdev.chartjs.model.options.Title;

@Named("reportBean")
@ViewScoped
public class ReportBean implements Serializable {

    private String barModel;
    private final ExpenseDAO expenseDAO = new ExpenseDAO();

    public String getBarModel() {
        try {
            return barModel != null ? barModel.replaceAll("^\"|\"$", "") : "{}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
    

    @PostConstruct
    public void init() {
        generateExpenseChart();
    }

    public void generateExpenseChart() {
        Map<String, Double> data = expenseDAO.getExpenseTotalsByDepartment();

        if (data.isEmpty()) {
            barModel = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No expense data found",
                            "Chart will not render until data exists."));
            return;
        }

        BarDataset dataset = new BarDataset()
                .setLabel("Expenses by Department")
                .setBackgroundColor(new RGBAColor(75, 192, 192, 0.6))
                .setBorderColor(new RGBAColor(75, 192, 192))
                .setBorderWidth(1);

        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            labels.add(entry.getKey());
            dataset.addData(BigDecimal.valueOf(entry.getValue()));
        }

        barModel = new BarChart()
                .setData(new BarData().addDataset(dataset).setLabels(labels))
                .setOptions(new BarOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setPlugins(new Plugins()
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Expense Summary Report")))
                ).toJson();
    }
}
