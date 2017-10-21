package com.github.kazuhito_m.googlepageregister.scraping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.List;

@Component
public class BlogArticleScraper {

    private static Logger logger = LoggerFactory.getLogger(BlogArticleScraper.class);

    public List<URL> articleLinks() {
        logger.info("スクレイプは通ってるよ");
        return Collections.emptyList();
    }
}
