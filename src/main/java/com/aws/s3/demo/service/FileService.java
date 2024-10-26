package com.aws.s3.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.s3.demo.config.S3Config;
import com.aws.s3.demo.model.FileResponse;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class FileService {
    private final S3Client s3Client;
    private final String bucketName;

    public FileService(S3Config config) {
        this.s3Client = config.s3Client();
        this.bucketName = config.getBucketName();
    }

    public List<FileResponse> searchFiles(String userName, String searchTerm) {
        String prefix = userName + "/" + searchTerm;
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build();
        
        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        return response.contents().stream()
                .map(obj -> new FileResponse(obj.key(), obj.size(), generateFileUrl(obj.key())))
                .collect(Collectors.toList());
    }

    public void uploadFile(String userName, MultipartFile file) throws S3Exception, AwsServiceException, SdkClientException, IOException {
        String key = userName + "/" + file.getOriginalFilename();
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
    }

    private String generateFileUrl(String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
    