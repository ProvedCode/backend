package com.provedcode.aws.controller;

import com.provedcode.aws.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/talents")
public class S3Controller {
    S3Service s3Service;

    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return s3Service.saveFile(file);
    }

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/download/{filename}")
    public byte[] download(@PathVariable String filename) {
        return s3Service.downloadFile(filename);
    }

    @PreAuthorize("hasRole('TALENT')")
    @DeleteMapping("/delete/{filename}")
    public String delete(@PathVariable String filename) {
        return s3Service.deleteFile(filename);
    }

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/")
    public List<String> getAllFiles() {
        return s3Service.listAllFiles();
    }
}