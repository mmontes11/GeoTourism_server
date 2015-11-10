package com.mmontes.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static com.mmontes.util.Constants.AMAZON_S3_BUCKET_NAME;
import static com.mmontes.util.Constants.AMAZON_S3_ROOT_URL;

public class AmazonService {

    public static String uploadS3(File file) {
        AmazonS3 s3 = new AmazonS3Client();
        String fileName = Calendar.getInstance().getTimeInMillis() + "_" + file.getName();
        PutObjectRequest por = new PutObjectRequest(AMAZON_S3_BUCKET_NAME, fileName, file);
        s3.putObject(por.withCannedAcl(CannedAccessControlList.PublicRead));
        return AMAZON_S3_ROOT_URL + fileName;
    }
}