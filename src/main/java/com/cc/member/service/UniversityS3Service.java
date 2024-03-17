package com.cc.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UniversityS3Service {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.university-verify-bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID().toString();
        File fileObj = convert(multipartFile, fileName).orElseThrow(RuntimeException::new);

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        fileObj.delete();

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    private Optional<File> convert(MultipartFile file, String fileName) throws  IOException {
        File convertFile = new File(fileName);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public void delete(String fileUrl) {
        try {
            String fileName = fileUrl.substring(61);
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucket, fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Delete File failed", e);
        }
    }
}
