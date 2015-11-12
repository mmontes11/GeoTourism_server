package com.mmontes.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mmontes.util.Constants;

import java.io.File;
import java.util.Calendar;

import static com.mmontes.util.Constants.AWS_S3_BUCKET_NAME;
import static com.mmontes.util.Constants.AWS_S3_ROOT_URL;

public class AmazonService {

    public static String uploadS3(File file) {
        AWSCredentials credentials = new BasicAWSCredentials(Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_ACCESS_KEY);
        AmazonS3 s3 = new AmazonS3Client(credentials);
        String fileName = Calendar.getInstance().getTimeInMillis() + "_" + file.getName();
        PutObjectRequest por = new PutObjectRequest(AWS_S3_BUCKET_NAME, fileName, file);
        s3.putObject(por.withCannedAcl(CannedAccessControlList.PublicRead));
        return AWS_S3_ROOT_URL + fileName;
    }
}