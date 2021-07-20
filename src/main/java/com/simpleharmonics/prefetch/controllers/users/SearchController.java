package com.simpleharmonics.prefetch.controllers.users;

import com.simpleharmonics.prefetch.entities.PageEntity;
import com.simpleharmonics.prefetch.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private PageRepository pageRepository;

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PageEntity> search(@RequestParam(value = "query") String query) {
        List<PageEntity> pageEntityList = pageRepository.search(query);

        return pageEntityList;
    }
}