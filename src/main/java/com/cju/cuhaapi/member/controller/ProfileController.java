package com.cju.cuhaapi.member.controller;

import com.cju.cuhaapi.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{filename}")
    public Resource downloadProfile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + profileService.getFullPath(filename));
    }
}
