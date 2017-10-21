package com.github.kazuhito_m.googlepageregister.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class BlogArticleScraper {

    private static final String BLOG_URL = "https://kazuhito-m.github.io";

    private static Logger logger = LoggerFactory.getLogger(BlogArticleScraper.class);

    public List<URL> articleLinks() throws IOException {
        Document document = Jsoup.connect(BLOG_URL).get();
        Elements elements = document.select(".posts > li > a");
        return elements.eachAttr("href").stream()
                .map(this::toUrl)
                .filter(url -> url != null)
                .collect(toList());
    }

    private URL toUrl(String href) {
        try {
            return new URL(BLOG_URL + href);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
