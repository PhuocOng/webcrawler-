package com.example.webcrawler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aj.org.objectweb.asm.Label;

@RestController
@RequestMapping("/api/images")
public class WebCrawlerController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    @Autowired
    private ImageLinkRepository imageLinkRepository;

    @Autowired
    private WebCrawlerCallable webCrawlerCallable;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public WebCrawlerController(WebCrawlerService webCrawlerService, ImageLinkRepository imageLinkRepository, WebCrawlerCallable webCrawlerCallable) {
        this.webCrawlerService = webCrawlerService;
        this.imageLinkRepository = imageLinkRepository;
        this.webCrawlerCallable = webCrawlerCallable;
    }

    @GetMapping("/crawl")
    public ResponseEntity<List<String>> crawlAndFetchImages(@RequestParam String url) {
        System.out.println("receive request to /crawl to fetch image at " + url);
        webCrawlerCallable.setUrl(url);
        
        WebCrawlerCallable task = webCrawlerCallable;
        

        List<String> imageUrls = webCrawlerService.crawl(url);
        Future<List<String>> threadResult = executorService.submit(task);
        
        List<String> all_labels = new ArrayList<String>();
        try {
            List<String> res = threadResult.get();
            System.out.println("After run thread, this is what we have" + res);
            List<Future<List<String>>> futures = new ArrayList<>();
            for (String img_url : res) {
                LabelsDetectCallable labelsTask = new LabelsDetectCallable();
                labelsTask.setUrl(img_url);
                Future<List<String>> labelsResult = executorService.submit(labelsTask);
                futures.add(labelsResult);
                // List<String> labels = labelsResult.get();
                // System.out.println(labels);
            }

            for (Future<List<String>> future : futures) {
                List<String> curr_labels = future.get();
                all_labels.addAll(curr_labels);
            }
            System.out.println("After run thread, this is all labels we have" + all_labels);
        } catch (Exception e) {
            System.out.println("There is problem with multithread");
            System.out.println(e);
        }
        return ResponseEntity.ok(imageUrls);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveImages(@RequestBody List<String> imageUrls) {
        System.out.println("receive request to /save to save images into database");
        webCrawlerService.saveImages(imageUrls);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public String testApi() {
        return "Hello";
    }
}