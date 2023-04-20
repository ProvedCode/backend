package com.provedcode.aws.controller;

import com.provedcode.aws.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AWSS3BucketController {
    FileService fileService;

    void setUserImage() {

    }
}
