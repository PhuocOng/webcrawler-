package com.example.webcrawler;

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(ImageLinkRepository imageLinkRepository) {
        return args -> {
            imageLinkRepository.deleteAll();
            String url1 = "https://crl2020.imgix.net/img/stacked-logo-hr.png?auto=format,compress";
            String url2 = "https://crl2020.imgix.net/img/scale-fast-rev.png?auto=format,compress&w=1420";
            ImageLink imageLink1 = new ImageLink(url1);
            ImageLink imageLink2 = new ImageLink(url2);
            if (imageLinkRepository.findByUrl(url1) != null && imageLinkRepository.findByUrl(url2) != null)
                imageLinkRepository.saveAll(List.of(imageLink1, imageLink2));
        };
    }
}
