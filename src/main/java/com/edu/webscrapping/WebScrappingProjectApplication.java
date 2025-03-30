package com.edu.webscrapping;

import com.edu.webscrapping.service.WebScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebScrappingProjectApplication implements CommandLineRunner {


    @Autowired
    WebScrapper webScrapper;

    public static void main(String[] args) {
        SpringApplication.run(WebScrappingProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        webScrapper.run();
    }
}
