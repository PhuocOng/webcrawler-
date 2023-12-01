package com.example.webcrawler;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageLinkRepository extends JpaRepository<ImageLink, Long>{
    List<ImageLink> findByUrl(String url);
    
}
