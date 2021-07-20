package com.simpleharmonics.prefetch.core;

import com.simpleharmonics.prefetch.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Manager {

    @Autowired
    private PageRepository pageRepository;

    public long countLinks() {
        return pageRepository.countLinks();
    }
}
