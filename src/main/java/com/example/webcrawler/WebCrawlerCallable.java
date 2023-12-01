package com.example.webcrawler;

import java.util.*;
import java.util.concurrent.Callable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WebCrawlerCallable implements Callable<List<String>> {

    @Autowired
    private WebCrawlerService webCrawlerService;

    private String url;

    public WebCrawlerCallable(WebCrawlerService webCrawlerService) { //This is magical of Dependency Injection => no need to pass it if we use @Autowired at another file
        this.webCrawlerService = webCrawlerService;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public List<String> call() throws Exception {
	    List<String> imageUrls = webCrawlerService.crawl(url);
        System.out.println("Start to run thread");
        return imageUrls;
    }
    
}
