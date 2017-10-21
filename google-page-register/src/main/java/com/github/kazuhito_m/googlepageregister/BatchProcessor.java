package com.github.kazuhito_m.googlepageregister;

import com.github.kazuhito_m.googlepageregister.scraping.BlogArticleScraper;
import com.github.kazuhito_m.googlepageregister.webbrothercontrol.GoogleUrlRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchProcessor {

    private static Logger logger = LoggerFactory.getLogger(BatchProcessor.class);

    private List<URL> links; // TODO SpringBatchでステップ間のデータわたしがよくわからん…ので暫定

    @Bean
    public Job job(JobBuilderFactory jobs, Step scrapingStep, Step googleUrlRegisterStep) {
        return jobs
                .get("myJob")
                .incrementer(new RunIdIncrementer())
                .start(scrapingStep)
                .next(googleUrlRegisterStep)
                .build();
    }

    @Bean
    public Step scrapingStep() {
        return steps.get("scrapingStep").tasklet((stepContribution, chunkContext) -> {
            links = scraper.articleLinks();
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step googleUrlRegisterStep() {
        return steps.get("googleUrlRegisterStep").tasklet((stepContribution, chunkContext) -> {
            register.register(links);
            logger.info("Page register executed.");
            return RepeatStatus.FINISHED;
        }).build();
    }

    final JobBuilderFactory jobs;
    final StepBuilderFactory steps;
    final BlogArticleScraper scraper;
    final GoogleUrlRegister register;

    public BatchProcessor(JobBuilderFactory jobs, StepBuilderFactory steps, BlogArticleScraper scraper, GoogleUrlRegister register) {
        this.jobs = jobs;
        this.steps = steps;
        this.scraper = scraper;
        this.register = register;
    }

}

