/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.newsletter;

import com.mxp.newsletter.NewsletterDAO;
import com.mxp.newsletter.Newsletter;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.InputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author admin
 */
@Named("newsletterView")
@ViewScoped
public class NewsletterView implements Serializable {

    private NewsletterDAO dao = new NewsletterDAO();
    private List<Newsletter> newsletters;

    public String getLatestNewsletterTitle() {
        return dao.getLatestTitle();
    }

    @PostConstruct
    public void init() {
        newsletters = dao.getAll();
    }

    public void handleUpload(FileUploadEvent event) {
        String fileName = event.getFile().getFileName();
        String contentType = event.getFile().getContentType();

        if (!"application/pdf".equalsIgnoreCase(contentType)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Invalid File Type",
                            "Only PDF files are allowed."));
            return;

        }

        Newsletter n = new Newsletter();
        n.setTitle(fileName.replace(".pdf", ""));
        n.setFilename(fileName);

        try (InputStream in = event.getFile().getInputStream()) {
            String uploadDir = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/pdfs/");

            Files.copy(in, Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);
            n.setCoverImage(fileName.replace(".pdf", ".jpg"));
            dao.save(n);
            newsletters = dao.getAll();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Upload Successful",
                            "Newsletter '" + fileName + "' was published successfully."));

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Upload Failed", e.getMessage()));
        }
    }

    public void handleCoverUpload(FileUploadEvent event) {
        String fileName = event.getFile().getFileName();

        try (InputStream in = event.getFile().getInputStream()) {
            String uploadDir = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/covers/");

            Files.copy(in, Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cover Image Uploaded", fileName));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Upload Failed", e.getMessage()));
        }
    }
    
    private String searchKeyword;

public void search() {
    newsletters = dao.searchByKeyword(getSearchKeyword());
}

    public List<Newsletter> getNewsletters() {
        return newsletters;
    }

    public void deleteNewsletter(int id) {
        dao.delete(id);
        newsletters = dao.getAll();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Deleted", "Newsletter removed successfully."));
    }

    /**
     * @return the searchKeyword
     */
    public String getSearchKeyword() {
        return searchKeyword;
    }

    /**
     * @param searchKeyword the searchKeyword to set
     */
    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

}
