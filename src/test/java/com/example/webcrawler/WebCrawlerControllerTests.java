package com.example.webcrawler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WebCrawlerController.class)
public class WebCrawlerControllerTests {

    @Autowired
    private WebCrawlerController webCrawlerController;

    @MockBean
    private WebCrawlerService webCrawlerService;

    @MockBean
    private ImageLinkRepository imageLinkRepository;

    @MockBean
    private WebCrawlerCallable webCrawlerCallable;

    @Autowired
    private MockMvc mockMvc;
    @Test
    void testCrawlAndFetchImages() {

    }

    @Test
    void testSaveImages() {

    }

    @Test
    void testTestApi() throws Exception {

        mockMvc.perform(get("/api/images"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello"));
    }
}
