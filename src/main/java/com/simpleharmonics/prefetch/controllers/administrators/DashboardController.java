package com.simpleharmonics.prefetch.controllers.administrators;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(value = "/administrator")
    public String root() {
        return "index.html";
    }
}