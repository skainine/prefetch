package com.simpleharmonics.prefetch;

import com.simpleharmonics.prefetch.entities.PageEntity;
import com.simpleharmonics.prefetch.repositories.PageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class PrefetchApplication implements CommandLineRunner {

    //Crawl Every 30 days
    //private static final long CRAWL_INTERVAL = 1000L * 60 * 60 * 24 * 30;
    //Target https website full URL without trailing slash(/)
    private static String TARGET_WEBSITE;

    @Autowired
    private PageRepository pageRepository;

    public static void main(String[] args) {
        SpringApplication.run(PrefetchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        greet();
        while (true) {
            boolean next = false;
            showMenus();
            String input = scanner.next();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1: {
                        showAllArchives();
                        break;
                    }
                    case 2: {
                        next = true;
                        break;
                    }
                    default: {
                        wrongInput("Expected numbers 1 or 2");
                        break;
                    }
                }
            } catch (Exception e) {
                wrongInput("Expected a number");
            }
            if (next) {
                break;
            }
        }

        while (true) {
            requestWebsite();
            TARGET_WEBSITE = cleanseRootLink(scanner.next());
            if (TARGET_WEBSITE == null) {
                continue;
            }

            Timer timer = new Timer();
            timer.schedule(new Crawler(), 0);
            //timer.schedule(new Crawler(), 0, CRAWL_INTERVAL);
            break;
        }
    }

    private void showAllArchives() {
        for (PageEntity pageEntity : pageRepository.findAll()) {
            System.out.println("-------------------------------------------");
            System.out.println("Title: " + pageEntity.getTitle());
            System.out.println("Link: " + pageEntity.getLink());
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
        System.out.println("1. Peek the Achieved data");
        System.out.println("2. Achieve New Data");
        System.out.print("Enter your number corresponding to your choice: ");
    }

    private void requestWebsite() {
        System.out.println("-------------------------------------------");
        System.out.println("Please Enter full URL of the website.");
        System.out.println("Target smaller websites because bigger websites may take very long time to archive.");
        System.out.println("Please note, Prefetch will archive the subpages of the parent url only.");
        System.out.print("Enter the website url that you want to archive: ");
    }

    private String cleanseRootLink(String rootLink) {
        if (rootLink == null || rootLink.isEmpty()) {
            return null;
        }

        StringBuilder processedLink;
        if (rootLink.startsWith("https://") || rootLink.startsWith("http://")) {
            processedLink = new StringBuilder(rootLink);
        } else {
            return null;
        }

        if (processedLink.length() == 0) {
            return null;
        }

        if (processedLink.lastIndexOf("/") == processedLink.length() - 1) {
            processedLink.deleteCharAt(processedLink.length() - 1);
        }
        return processedLink.toString();
    }

    private String cleanseRawLink(String rawLink) {
        if (rawLink == null || rawLink.isEmpty()) {
            return null;
        }

        StringBuilder processedLink = new StringBuilder();
        if (rawLink.startsWith(TARGET_WEBSITE)) {
            processedLink = new StringBuilder(rawLink);
        } else {
            if (rawLink.startsWith("#")) {
                return null;
            }
            if (rawLink.startsWith("//")) {
                String prefix = TARGET_WEBSITE.substring(0, TARGET_WEBSITE.indexOf("//"));
                String newString = prefix + rawLink;
                if (newString.startsWith(TARGET_WEBSITE)) {
                    processedLink.append(newString);
                } else {
                    return null;
                }
            }
            if (rawLink.startsWith("/")) {
                processedLink.append(TARGET_WEBSITE);
                processedLink.append(rawLink);
            }
        }

        if (processedLink.length() == 0) {
            return null;
        }

        if (processedLink.lastIndexOf("/") == processedLink.length() - 1) {
            processedLink.deleteCharAt(processedLink.length() - 1);
        }

        return processedLink.toString();
    }

    private class Crawler extends TimerTask {
        public void run() {
            Set<String> visitedSet = new HashSet<>();
            try {
                Set<PageEntity> pageEntitySet = new HashSet<>();

                Queue<String> visitingQueue = new LinkedList<>();

                visitingQueue.add(TARGET_WEBSITE);

                while (!visitingQueue.isEmpty()) {
                    String target = visitingQueue.poll();
                    Document document;
                    try {
                        document = Jsoup.connect(target).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    PageEntity pageEntity = new PageEntity();
                    pageEntity.setLink(target);
                    pageEntity.setTitle(document.title());
                    pageEntity.setTime(new Date().toString());
                    pageEntity.setBody(document.text());
                    pageRepository.save(pageEntity);
                    pageEntitySet.add(pageEntity);

                    Elements linkElements = document.select("a");
                    for (Element element : linkElements) {
                        String rawLink = element.attr("href");

                        String finalLink = cleanseRawLink(rawLink);

                        if (finalLink == null) {
                            continue;
                        }

                        if (visitedSet.contains(finalLink)) {
                            continue;
                        }

                        visitedSet.add(finalLink);
                        visitingQueue.add(finalLink);
                    }
                    System.out.println("Found " + visitedSet.size());
                    System.out.println("Saved " + pageEntitySet.size());
                    System.out.println("-------------------");
                }
                System.out.println("Archiving Complete");
                System.out.println("Total Links: " + pageEntitySet.size());
                System.out.println("-------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}