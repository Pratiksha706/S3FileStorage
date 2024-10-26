package com.aws.s3.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.s3.demo.model.FileResponse;
import com.aws.s3.demo.service.FileService;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;


@RestController
@RequestMapping("/api/files/s3")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<FileResponse>> searchFiles(
            @RequestParam String userName,
            @RequestParam String searchTerm) {
        List<FileResponse> files = fileService.searchFiles(userName, searchTerm);
        return ResponseEntity.ok(files);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam String userName,
            @RequestParam MultipartFile file) {
        try {
			fileService.uploadFile(userName, file);
		} catch (AwsServiceException | SdkClientException | IOException e) {
			e.printStackTrace();
		}
        return ResponseEntity.ok("File uploaded successfully");
    }
}
    