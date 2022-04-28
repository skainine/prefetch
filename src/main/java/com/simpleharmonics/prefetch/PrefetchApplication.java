package com.simpleharmonics.prefetch;

import com.simpleharmonics.prefetch.core.Crawler;
import com.simpleharmonics.prefetch.entities.PageEntity;
import com.simpleharmonics.prefetch.repositories.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class PrefetchApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(PrefetchApplication.class);
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private PageRepository pageRepository;

    public static void main(String[] args) {
        SpringApplication.run(PrefetchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Server Online");
        greet();
        showMenus();
    }

    private void showAllArchives() {
        for (PageEntity pageEntity : pageRepository.findAll()) {
            System.out.println("-------------------------------------------");
            System.out.println("Title: " + pageEntity.getTitle());
            System.out.println("Link: " + pageEntity.getLink());
            System.out.println("Body: " + pageEntity.getBody());
        }
    }

    private void wrongInput(String message) {
        System.out.println("-------------------------------------------");
        System.out.println("Invalid Choice: " + message);
    }

    private void greet() {
        System.out.println("-------------------------------------------");
        System.out.println("------------Welcome to Prefetch------------");
    }

    private void showMenus() {
        System.out.println("-------------------------------------------");
        System.out.println("1. Archive New Data");
        System.out.println("2. Peek the Archived data");
        System.out.print("Enter your number corresponding to your choice: ");
        String input = scanner.next();
        switch (input) {
            case "1": {
                requestWebsite();
                break;
            }
            case "2": {
                showAllArchives();
                break;
            }
            default: {
                wrongInput("Please enter 1 or 2");
            }
        }
    }

    private void requestWebsite() {
        System.out.println("-------------------------------------------");
        System.out.println("Please Enter full URL of the website.");
        System.out.println("Target smaller websites because bigger websites may take very long time to archive.");
        System.out.println("Please note, Prefetch will archive the subpages of the parent url only.");
        System.out.print("Enter the website url that you want to archive: ");

        String link = scanner.next();
        new Crawler().crawl(link);
    }
}