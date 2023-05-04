package com.provedcode.aws.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.provedcode.aws.service.FileService;
import com.provedcode.config.AWSProperties;
import com.provedcode.util.annotations.doc.controller.aws.GetAllAWSBucketFilesDevApiDoc;
import com.provedcode.util.annotations.doc.controller.aws.GetFileInfoDevApiDoc;
import com.provedcode.util.annotations.doc.controller.aws.PostSetNewUserImageApiDoc;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3/talents")
public class AWSS3BucketController {
    FileService fileService;
    AWSProperties awsProperties;
    AmazonS3 amazonS3;

    @PostSetNewUserImageApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("/image/upload")
    public void setNewUserImage(@RequestParam("file") MultipartFile file,
                                Authentication authentication) {
        fileService.setNewUserImage(file, authentication);
    }

    @GetAllAWSBucketFilesDevApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/files")
    List<String> getAllFiles() {
        return fileService.listAllFiles();
    }

    @GetFileInfoDevApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("/aws/test-of-filetype")
    String testTypeOfFile(@RequestParam("file") MultipartFile file,
                          Authentication authentication) {
        return Arrays.stream(file.getContentType().split("/")).toList().get(1) + " " + file.getOriginalFilename() +
                " " + file.getName() + " " + file.getResource();
    }
}
