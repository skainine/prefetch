package com.simpleharmonics.prefetch.core;

import com.simpleharmonics.prefetch.entities.PageEntity;
import com.simpleharmonics.prefetch.repositories.PageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Component
public class Crawler {

    @Autowired
    private PageRepository pageRepository;

    public void crawl(String inputLink) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String rootLink = Tools.cleanseRootLink(inputLink);
                if (rootLink == null) {
                    return;
                }
                Set<String> visitedSet = new HashSet<>();
                try {
                    Set<PageEntity> pageEntitySet = new HashSet<>();

                    Queue<String> visitingQueue = new LinkedList<>();

                    visitingQueue.add(rootLink);

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
                        pageEntity.link = target;
                        pageEntity.title = document.title();
                        pageEntity.body = document.text();
                        pageRepository.save(pageEntity);
                        pageEntitySet.add(pageEntity);

                        Elements linkElements = document.select("a");
                        for (Element element : linkElements) {
                            String rawLink = element.attr("href");

                            String finalLink = Tools.cleanseRawLink(rootLink, rawLink);

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
        }).start();
    }
}
