package com.bujeong.gov_opendata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        log.debug("-----------------------------------------------test");
        return "";
    }
}
