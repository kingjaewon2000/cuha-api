package com.cju.cuhaapi.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String home() {
        return "<h1>Home</h1>";
    }

    @GetMapping("/user")
    public String user() {
        return "<h1>User</h1>";
    }
}
