package com.example.webcrawler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ImageLinkRepositoryTests {

    @Autowired
    private ImageLinkRepository imageLinkRepository;

    @Test
    public void whenFindByUrl_thenReturnImageLink() {
        // given
        ImageLink link = new ImageLink("http://example.com/image.jpg");
        ImageLink link2 = new ImageLink("http://example.com/image.jpg");
        imageLinkRepository.save(link);
        imageLinkRepository.save(link2);

        // when
        List<ImageLink> found = imageLinkRepository.findByUrl(link.getUrl());

        // then
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getUrl()).isEqualTo(link.getUrl());
    }
}

