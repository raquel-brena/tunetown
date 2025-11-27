package com.imd.backend.app.service;

import com.imd.backend.domain.entities.core.MediaFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3;
    private final String bucketName;

    public S3Service(S3Client s3Client, String bucketName) {
        this.s3 = s3Client;
        this.bucketName = bucketName;
    }

    public MediaFile uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        String url = s3.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toString();

        MediaFile fileEntity = new MediaFile();
        fileEntity.setFileName(fileName);
        fileEntity.setUrl(url);
        fileEntity.setContentType(file.getContentType());
        fileEntity.setSize(file.getSize());

        return fileEntity;
    }

    public void deleteFile(String fileKey) {
        String key = fileKey.substring(fileKey.lastIndexOf("/") + 1);
        s3.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
    }

    public String generatePresignedUrl(String key, Duration duration) {
        try (S3Presigner presigner = S3Presigner.builder()
                .region(s3.serviceClientConfiguration().region())
                .credentialsProvider(s3.serviceClientConfiguration().credentialsProvider())
                .build()) {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(duration)
                    .getObjectRequest(getObjectRequest)
                    .build();

            return presigner.presignGetObject(presignRequest).url().toString();
        }
    }
}
