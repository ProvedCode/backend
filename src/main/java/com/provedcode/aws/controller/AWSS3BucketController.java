package com.provedcode.aws.controller;

import com.provedcode.aws.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3/talents")
public class AWSS3BucketController {
    FileService fileService;

    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("/image/upload")
    public void setNewUserImage(@RequestParam("file") MultipartFile file,
                              Authentication authentication) {
        fileService.setNewUserImage(file, authentication);
    }

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/files")
    List<String> getAllFiles() {
        return fileService.listAllFiles();
    }
}
