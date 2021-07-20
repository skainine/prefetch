package com.simpleharmonics.prefetch.controllers.administrators;

import com.simpleharmonics.prefetch.core.Crawler;
import com.simpleharmonics.prefetch.core.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/administrator/api")
public class DataController {

    @Autowired
    private Manager manager;

    @Autowired
    private Crawler crawler;

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public void refreshLinks() {

    }

    @PostMapping(value = "/crawl", produces = MediaType.APPLICATION_JSON_VALUE)
    public void crawlLink(@RequestBody String link) throws Exception {
        crawler.crawl(link);
    }

    @PostMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public long countLinks() throws Exception {
        return manager.countLinks();
    }
}