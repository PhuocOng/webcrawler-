package com.example.webcrawler;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ImageLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;

    
    public ImageLink() {
    }

    public ImageLink(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
    
}
