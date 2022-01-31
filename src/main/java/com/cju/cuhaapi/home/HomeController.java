package com.cju.cuhaapi.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@ApiIgnore
@RequestMapping("/v1")
@RestController
public class HomeController {

    @RequestMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public String home() {
        return "<h1>Home</h1>";
    }

    @GetMapping("/member")
    public String member() {
        return "<h1>Member</h1>";
    }
}
