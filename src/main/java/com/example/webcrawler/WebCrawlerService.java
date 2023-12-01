package com.example.webcrawler;


import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class WebCrawlerService {

    @Autowired
    private ImageLinkRepository imageLinkRepository;

    public WebCrawlerService(ImageLinkRepository imageLinkRepository) {
        this.imageLinkRepository = imageLinkRepository;
    }

    public List<String> crawl(String url) { //Return list of img urls
        List<String> imageUrls = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements images = doc.select("img");

            for (Element img : images) {
                String imgUrl = img.absUrl("src");
                imageUrls.add(imgUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrls;
    }

    public void saveImages(List<String> imageUrls) {
        for (String url : imageUrls) {
            ImageLink imageLink = new ImageLink(url);
            imageLinkRepository.save(imageLink);
        }
    }
    
}
