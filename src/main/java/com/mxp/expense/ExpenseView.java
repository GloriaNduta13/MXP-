/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.expense;

import com.mxp.expense.ExpenseDAO;
import com.mxp.expense.Expense;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import com.mxp.login.LoginBean;

/**
 *
 * @author admin
 */
@Named("expenseView")
@ViewScoped
public class ExpenseView implements Serializable {

    private ExpenseDAO expenseDAO = new ExpenseDAO();
    private Expense expense = new Expense();
    private List<Expense> expenses;

    @PostConstruct
    public void init() {
        expenses = expenseDAO.getAllExpenses();
    }

    private final ExpenseDAO dao = new ExpenseDAO();

    public String getTotalExpenseAmount() {
        double total = dao.getAllExpenses()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        return NumberFormat.getNumberInstance(new Locale("en", "KE")).format(BigDecimal.valueOf(total));
    }

    public void submitExpense() {
        expenseDAO.save(expense);
        expenses = expenseDAO.getAllExpenses();
        expense = new Expense();

        LoginBean login = FacesContext.getCurrentInstance()
                .getApplication().evaluateExpressionGet(
                        FacesContext.getCurrentInstance(), "#{LoginBean}", LoginBean.class);

        expense.setEmployeeName(login.getUsername());

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Expense submitted successfully"));

        System.out.println("DEBUG: " + expense.getExpenseTitle() + ", " + expense.getAmount() + ", " + expense.getDate());

    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void resetForm() {
        expense = new Expense();
    }

}
