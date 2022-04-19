package com.cju.cuhaapi.hello;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Hello hello() {
        return new Hello("hi");
    }

    @Data
    @AllArgsConstructor
    static class Hello {
        String content;
    }
}
