/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.helpdesk;

import com.mxp.helpdesk.HelpdeskTicket;
import com.mxp.helpdesk.HelpdeskDAO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.event.CellEditEvent;
import com.mxp.login.LoginBean;

/**
 *
 * @author admin
 */
@Named("helpdeskView")
@ViewScoped
public class HelpdeskView implements Serializable {

    private HelpdeskDAO helpdeskDAO = new HelpdeskDAO();
    private HelpdeskTicket ticket = new HelpdeskTicket();
    private List<HelpdeskTicket> tickets;

    @Inject
    private LoginBean loginBean;

    @PostConstruct
    public void init() {
        setTickets(helpdeskDAO.getAll());

    }
    private HelpdeskTicket selectedTicket = new HelpdeskTicket();

    public HelpdeskTicket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(HelpdeskTicket selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public void saveEditedSubject() {
        helpdeskDAO.updateSubject(selectedTicket.getId(), selectedTicket.getSubject());
        setTickets(helpdeskDAO.getAll());
    }

    public void submit() {
        getTicket().setSubmittedBy(loginBean.getUsername());
        getHelpdeskDAO().save(getTicket());
        setTickets(getHelpdeskDAO().getAll());
        setTicket(new HelpdeskTicket());
    }

    /**
     * @return the helpdeskDAO
     */
    public HelpdeskDAO getHelpdeskDAO() {
        return helpdeskDAO;
    }

    /**
     * @param helpdeskDAO the helpdeskDAO to set
     */
    public void setHelpdeskDAO(HelpdeskDAO helpdeskDAO) {
        this.helpdeskDAO = helpdeskDAO;
    }

    /**
     * @return the ticket
     */
    public HelpdeskTicket getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(HelpdeskTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the tickets
     */
    public List<HelpdeskTicket> getTickets() {
        return tickets;
    }

    /**
     * @param tickets the tickets to set
     */
    public void setTickets(List<HelpdeskTicket> tickets) {
        this.tickets = tickets;
    }

    public List<String> getStatusOptions() {
        List<String> list = new ArrayList<>();
        list.add("Open");
        list.add("In Progress");
        list.add("Resolved");
        list.add("Closed");
        return list;
    }

    public String getActivityClass(HelpdeskTicket t) {
    switch (t.getStatus().toLowerCase()) {
        case "open":       return "medium-activity";
        case "inprogress": return "high-activity";
        case "closed":
        case "resolved":   return "low-activity";
        default:           return "low-activity";
    }
}


    public void deleteTicket(HelpdeskTicket ticket) {
        helpdeskDAO.delete(ticket.getId());
        setTickets(helpdeskDAO.getAll());
    }

    public void updateStatus(HelpdeskTicket ticket, String newStatus) {
        helpdeskDAO.updateStatus(ticket.getId(), newStatus);
        setTickets(helpdeskDAO.getAll());
    }

    public String getStatusClass(HelpdeskTicket t) {
    if (t.getStatus() == null || t.getStatus().trim().isEmpty()) {
        return "status-empty";
    }
    return "status-" + t.getStatus().replaceAll("\\s+", "").toLowerCase();
}
    
public void onCellEdit(CellEditEvent<?> event) {
    HelpdeskTicket ticket = (HelpdeskTicket) event.getRowData();  
    Object oldVal = event.getOldValue();                          
    Object newVal = event.getNewValue();                          

    if (newVal != null && !newVal.equals(oldVal)) {
        ticket.setSubject(newVal.toString());                     
        helpdeskDAO.updateSubject(ticket.getId(), ticket.getSubject());
        setTickets(helpdeskDAO.getAll());                         
    }
}

}


