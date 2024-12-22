package com.bujeong.gov_opendata.controller;

import com.bujeong.gov_opendata.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final TestService testService;

    @RequestMapping("/index")
    public String index() {
        log.debug("-----------------------------------------------test");
        testService.test();
        return "";
    }
}
