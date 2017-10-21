package com.github.kazuhito_m.googlepageregister;

import com.github.kazuhito_m.googlepageregister.scraping.BlogArticleScraper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(Application.class, args)));
    }

    @Override
    public void run(String... args) throws Exception {
    }

    private final BlogArticleScraper scraper;

    public Application(BlogArticleScraper scraper) {
        this.scraper = scraper;
    }

}
