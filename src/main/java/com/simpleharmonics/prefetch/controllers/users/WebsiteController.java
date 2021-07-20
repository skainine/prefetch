package com.simpleharmonics.prefetch.controllers.users;

import com.simpleharmonics.prefetch.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {

    @Autowired
    private PageRepository pageRepository;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String index() {
        return "index.html";
    }
}